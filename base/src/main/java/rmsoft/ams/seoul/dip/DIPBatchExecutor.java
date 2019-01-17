/*
 * Copyright (c) 2017. RMSoft Co.,Ltd. All rights reserved
 */

package rmsoft.ams.seoul.dip;

import com.jcraft.jsch.*;
import io.onsemiro.utils.DateUtils;
import io.onsemiro.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Conditional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

/**
 * DIPBatchExecutor
 *
 * @author james
 * @version 1.0.0
 * @since 2017 -12-06 오후 6:13
 */
@Slf4j
@Component
@Conditional(DIPBatchExecutionCondition.class)
public class DIPBatchExecutor {
    /**
     * The Ftp host.
     */
    @Value("${dip.process.sftp.host}")
    protected String ftpHost;

    /**
     * The Ftp port.
     */
    @Value("${dip.process.sftp.port}")
    protected int ftpPort;

    /**
     * The Ftp user.
     */
    @Value("${dip.process.sftp.user}")
    protected String ftpUser;

    /**
     * The Ftp password.
     */
    @Value("${dip.process.sftp.password}")
    protected String ftpPassword;

    /**
     * The Ftp json path.
     */
    @Value("${dip.process.sftp.json-file-path}")
    protected String ftpJsonPath;

    /**
     * The Ftp digital file path.
     */
    @Value("${dip.process.sftp.digital-file-path}")
    protected String ftpDigitalFilePath;

    /**
     * The Digital file Upload Prefix
     */
    @Value("${dip.process.sftp.upload-prefix}")
    protected String uploadPrefix;

    /**
     * The Json tmp dir.
     */
    @Value("${dip.process.tmp.dir}")
    protected String jsonTmpDir;

    /**
     * The Digital file dir.
     */
    @Value("${dip.process.digital.file.dir}")
    protected String digitalFileDir;

    @Inject
    private DIPMapper dipMapper;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private ChannelSftp sftpChannel;
    private Session session;
    private Channel channel;

    private boolean sendFileSuccess = false;
    private boolean sendDigitalFileSuccess = false;

    private String localFtpJsonPath = "";
    private String localFtpDigitalFilePath = "";

    /**
     * Execute.
     */
    @Scheduled(cron = "${dip.process.batch.cron}")
    public void execute() {
        runDipProcess();
    }

    /**
     * Run dip process.
     */
    public void runDipProcess() {
        sendFileSuccess = false;
        sendDigitalFileSuccess = false;
        localFtpJsonPath = "";
        localFtpDigitalFilePath = "";

        log.info("DIPBatchExecutor-execute DIP Process");

        try {
            List<Map<String, Object>> entityList = dipMapper.findAllEntityType();
            List<Map<String, Object>> resultList = new ArrayList<>();
            List<Map<String, Object>> rcComponentFile = new ArrayList<>();

            String tableName = "";

            for (int i = 0; i < entityList.size(); i++) {
                Map<String, Object> entityInfo = entityList.get(i);

                tableName = (String) entityInfo.get("tableName");
                log.info("Data Processing : Table Name : {}", tableName);

                List<Map<String, Object>> exportList = dipMapper.findAllEntityTypeData((String) entityInfo.get("entityTypeUuid"));
                resultList = new ArrayList<>();

                for (int j = 0; j < exportList.size(); j++) {
                    Map<String, Object> tableInfo = exportList.get(j);

                    String tmpSql = "SELECT * FROM " + tableName + " WHERE " + (String) tableInfo.get("pkColumnCode") + " = '" + (String) tableInfo.get("keyUuid") + "'";
                    Map<String, Object> resultMap = jdbcTemplate.queryForMap(tmpSql);
                    resultList.add(resultMap);

                    // 전자파일 테이블인 경우에는 전자파일 전송을 위한 목록을 만든다.
                    if (tableName.equals("RC_COMPONENT")) {
                        rcComponentFile.add(resultMap);
                    }
                }

                log.info("Table : {} ,Data Size : {}", tableName, resultList.size());
                File jsonFile = FileUtils.getFile(jsonTmpDir + tableName + "_" + DateUtils.convertToString(LocalDateTime.now(), "yyyyMMdd") + ".json");
                FileUtils.write(jsonFile, JsonUtils.toJson(resultList).replace("null", "\"\""), StandardCharsets.UTF_8);
            }

            localFtpJsonPath = uploadPrefix + "/" + DateUtils.getNow("yyyyMMdd") + ftpJsonPath + "/";
            localFtpDigitalFilePath = uploadPrefix + "/" + DateUtils.getNow("yyyyMMdd") + ftpDigitalFilePath + "/";

            // Json 파일 전송
            if (entityList.size() > 0) {
                sendFileToFtp();
            } else {
                log.info("There is no entry lists.");
            }

            // 디지털 파일이 있으면 디지털 파일도 전송
            if (rcComponentFile != null && rcComponentFile.size() > 0) {
                sendDigitalFileToFtp(rcComponentFile);
            } else {
                sendDigitalFileSuccess = true;
            }

            // 전송이 완료되면 DB 테이블의 전송 Flag를 Y로 설정
            if (sendFileSuccess && sendDigitalFileSuccess) {
                log.info("History Table Update Export_Yn = 'Y'");
                dipMapper.updateSendHistory();
            }

            log.info("DIPBatchExecutor-end DIP Process");

        } catch (Exception e) {
            errorLogging(e);
            log.error("DIP Process Error : " + e.getMessage());
        }
    }

    private void connect() {
        log.info("SFTP Connecting to {}", ftpHost);

        try {
            // 1. JSch 객체를 생성한다.
            JSch jsch = new JSch();
            // 2. 세션 객체를 생성한다(사용자 이름, 접속할 호스트, 포트를 인자로 전달한다.)
            session = jsch.getSession(ftpUser, ftpHost, ftpPort);
            // 4. 세션과 관련된 정보를 설정한다.
            session.setConfig("StrictHostKeyChecking", "no");
            // 4. 패스워드를 설정한다.
            session.setPassword(ftpPassword);
            // 5. 접속한다.
            session.connect();

            // 6. sftp 채널을 연다.
            channel = session.openChannel("sftp");
            // 7. 채널에 연결한다.
            channel.connect();
            // 8. 채널을 FTP용 채널 객체로 캐스팅한다.
            sftpChannel = (ChannelSftp) channel;
        } catch (JSchException e) {
            errorLogging(e);
            log.error("JSchException Error : " + e.getMessage());
        }

    }

    private void disconnect() {
        if (session.isConnected()) {
            log.info("SFTP Disconnecting from {}", ftpHost);

            sftpChannel.disconnect();
            channel.disconnect();
            session.disconnect();
        }
    }

    private boolean sendFileToFtp() {
        if (session == null || !session.isConnected()) {
            connect();
        }

        File[] jsonFileList = getJsonFileList(jsonTmpDir);
        FileInputStream fis = null;

        try {
            for (int i = 0; i < jsonFileList.length; i++) {
                // Change to output directory
                if (!isDirectoryExists(sftpChannel, localFtpJsonPath)) {
                    /*log.info("Creating Directory : {} ", ftpJsonPath);
                    sftpChannel.mkdir(ftpJsonPath);*/

                    makeDirectory(sftpChannel, localFtpJsonPath);
                }

                sftpChannel.cd(localFtpJsonPath);

                // 입력 파일을 가져온다.
                fis = new FileInputStream(jsonFileList[i]);
                // 파일을 업로드한다.
                sftpChannel.put(fis, jsonFileList[i].getName());

                fis.close();
                log.info("JSON file is uploaded  : {}", jsonFileList[i].getName());
            }

            log.info("{} JSON file is uploaded.", jsonFileList.length);

            disconnect();
            sendFileSuccess = true;
        } catch (Exception e) {
            sendFileSuccess = false;

            errorLogging(e);
            log.error("FTP File Upload Error : {}", e.getMessage());
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    sendFileSuccess = false;
                    errorLogging(e);
                }
            }
        }

        return sendFileSuccess;
    }

    private boolean sendDigitalFileToFtp(List<Map<String, Object>> rcComponentFile) {
        if (session == null || !session.isConnected()) {
            connect();
        }

        FileInputStream fis = null;
        Map<String, Object> rcComponenMap = null;
        File digitalFile = null;

        try {
            for (int i = 0; i < rcComponentFile.size(); i++) {
                rcComponenMap = rcComponentFile.get(i);

                if (!isDirectoryExists(sftpChannel, localFtpDigitalFilePath)) {
                    /*log.info("Creating Directory : {} ", ftpDigitalFilePath);
                    sftpChannel.mkdir(ftpDigitalFilePath);*/
                    makeDirectory(sftpChannel, localFtpDigitalFilePath);

                }

                // Change to output directory
                sftpChannel.cd(localFtpDigitalFilePath);

                digitalFile = new File(digitalFileDir + (String) rcComponenMap.get("FILE_PATH") + File.separator + (String) rcComponenMap.get("FILE_NAME"));

                if (digitalFile.exists()) {
                    // 입력 파일을 가져온다.
                    fis = new FileInputStream(digitalFile);
                    // 파일을 업로드한다.
                    sftpChannel.put(fis, digitalFile.getName());

                    fis.close();
                    log.info("Digital file is uploaded  : {}", digitalFile.getName());
                }
            }

            log.info("{} Digital file is uploaded.", rcComponentFile.size());

            disconnect();
            sendDigitalFileSuccess = true;

        } catch (Exception e) {
            sendDigitalFileSuccess = false;

            errorLogging(e);
            log.error("FTP Digital File Upload Error : {}", e.getMessage());
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    sendDigitalFileSuccess = false;
                    errorLogging(e);
                }
            }
        }

        return sendDigitalFileSuccess;
    }

    /**
     * json 파일 목록을 가져온다.
     *
     * @param filePath
     * @return
     */
    private File[] getJsonFileList(String filePath) {

        File[] listFiles = null;

        try {
            listFiles = new File(filePath).listFiles(
                    (dir, name) -> {
                        return name.toLowerCase().endsWith(".json");
                    }
            );
        } catch (Exception e) {
            errorLogging(e);
        }

        return listFiles;
    }

    /**
     * Error logging.
     *
     * @param throwable the throwable
     */
    protected void errorLogging(Throwable throwable) {

        if (log.isErrorEnabled()) {

            Throwable rootCause = ExceptionUtils.getRootCause(throwable);

            if (rootCause != null) {
                throwable = rootCause;
            }

            if (throwable.getMessage() != null) {
                log.error(throwable.getMessage(), throwable);
            } else {
                log.error("ERROR", throwable);
            }
        }
    }

    /**
     * Check Remote Directory
     *
     * @param channelSftp
     * @param path
     * @return
     */
    private boolean isDirectoryExists(ChannelSftp channelSftp, String path) {
        Vector res = null;
        try {
            res = channelSftp.ls(path);
        } catch (SftpException e) {
            log.info("There is no directory or file : {}", path);

            if (e.id == ChannelSftp.SSH_FX_NO_SUCH_FILE) {
                return false;
            }
        }
        return res != null && !res.isEmpty();
    }

    private void makeDirectory(ChannelSftp channelSftp, String path) {

        log.info("Creating Directory : {} ", path);

        try {
            String[] folders = path.split("/");
            if (folders[0].isEmpty()) folders[0] = "/";
            String fullPath = folders[0];
            for (int i = 1; i < folders.length; i++) {
                Vector ls = channelSftp.ls(fullPath);
                boolean isExist = false;
                for (Object o : ls) {
                    if (o instanceof ChannelSftp.LsEntry) {
                        ChannelSftp.LsEntry e = (ChannelSftp.LsEntry) o;
                        if (e.getAttrs().isDir() && e.getFilename().equals(folders[i])) {
                            isExist = true;
                        }
                    }
                }
                if (!isExist && !folders[i].isEmpty()) {
                    channelSftp.mkdir(fullPath + folders[i]);
                }
                fullPath = fullPath + folders[i] + "/";
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
