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
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
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

/**
 * DIPBatchExecutor
 *
 * @author james
 * @version 1.0.0
 * @since 2017 -12-06 오후 6:13
 */
@Slf4j
@Component
@ConditionalOnClass(DIPBatchExecutionCondition.class)
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
        log.info("DIPBatchExecutor-execute DIP Process :: {}");

        try {
            List<Map<String, Object>> entityList = dipMapper.findAllEntityType();
            List<Map<String, Object>> resultList = new ArrayList<>();
            List<Map<String, Object>> rcComponentFile = new ArrayList<>();
            ;

            String tableName = "";

            for (int i = 0; i < entityList.size(); i++) {
                Map<String, Object> entityInfo = entityList.get(i);

                tableName = (String) entityInfo.get("tableName");
                log.info("Data Processing : Table Name : {}", tableName);

                List<Map<String, Object>> exportList = dipMapper.findAllEntityTypeData((String) entityInfo.get("entityTypeUuid"));

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
                resultList.clear();
            }

            // Json 파일 전송
            sendFileToFtp();

            // 디지털 파일이 있으면 디지털 파일도 전송
            if (rcComponentFile != null && rcComponentFile.size() > 0) {
                sendDigitalFileToFtp(rcComponentFile);
            }

            // 전송이 완료되면 DB 테이블의 전송 Flag를 Y로 설정
            dipMapper.updateSendHistory();
        } catch (Exception e) {
            errorLogging(e);
            log.error("DIP Process Error : " + e.getMessage());
        }
    }

    private void connect() throws JSchException {
        log.info("SFTP Connecting to {}", ftpHost);

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
    }

    private void disconnect() {
        if (session.isConnected()) {
            log.info("SFTP Disconnecting from {}", ftpHost);

            sftpChannel.disconnect();
            channel.disconnect();
            session.disconnect();
        }
    }

    private void sendFileToFtp() throws Exception {
        if (session == null || !session.isConnected()) {
            connect();
        }

        File[] jsonFileList = getJsonFileList(jsonTmpDir);

        FileInputStream fis = null;
        for (int i = 0; i < jsonFileList.length; i++) {
            // Change to output directory
            sftpChannel.cd(ftpJsonPath);

            // 입력 파일을 가져온다.
            fis = new FileInputStream(jsonFileList[i]);
            // 파일을 업로드한다.
            sftpChannel.put(fis, jsonFileList[i].getName());

            fis.close();
            log.debug("JSON file is uploaded  : {}", jsonFileList[i].getName());
        }

        log.info("{} JSON file is uploaded.", jsonFileList.length);

        disconnect();
    }

    private void sendDigitalFileToFtp(List<Map<String, Object>> rcComponentFile) throws Exception {
        if (session == null || !session.isConnected()) {
            connect();
        }

        FileInputStream fis = null;
        Map<String, Object> rcComponenMap = null;
        File digitalFile = null;

        for (int i = 0; i < rcComponentFile.size(); i++) {
            rcComponenMap = rcComponentFile.get(i);

            // Change to output directory
            sftpChannel.cd(ftpDigitalFilePath);

            digitalFile = new File(digitalFileDir + (String) rcComponenMap.get("FILE_PATH") + File.separator + (String) rcComponenMap.get("FILE_NAME"));

            if (digitalFile.exists()) {
                // 입력 파일을 가져온다.
                fis = new FileInputStream(digitalFile);
                // 파일을 업로드한다.
                sftpChannel.put(fis, digitalFile.getName());

                fis.close();
                log.debug("Digital file is uploaded  : {}", digitalFile.getName());
            }
        }

        log.info("{} Digital file is uploaded.", rcComponentFile.size());

        disconnect();
    }

    /**
     * json 파일 목록을 가져온다.
     *
     * @param filePath
     * @return
     */
    private File[] getJsonFileList(String filePath) throws IOException {
        File[] listFiles = new File(filePath).listFiles(
                (dir, name) -> {
                    return name.toLowerCase().endsWith(".json");
                }
        );

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
}
