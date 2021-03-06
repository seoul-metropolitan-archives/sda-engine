/*
 * Copyright (c) 2017. RMSoft Co.,Ltd. All rights reserved
 */

package rmsoft.ams.seoul.workflow;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Service;
import rmsoft.ams.seoul.common.workflow.WorkflowResponse;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The type Process ingest.
 */
@Slf4j
@Service
public class ProcessIngest {
    private JdbcTemplate jdbcTemplate;

    private static String sqlFilePath = "D:\\02.DreamAntDev\\git-workspaces\\java\\seoul-ams\\rms-ingest\\tmp";
    private List<String> truncateList = null;
    private static boolean truncateTableYn = true;
    private static int sqlBatchSize = 1000;

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        ProcessIngest test = new ProcessIngest();
        test.setJdbcTemplate(new JdbcTemplate(test.getDataSource()));
        test.setSqlFilePath("D:\\02.DreamAntDev\\git-workspaces\\java\\seoul-ams\\rms-ingest\\tmp");
        test.setTruncateTableYn(true);

        List<String> truncateList = new ArrayList<>();
        truncateList.add("ETC_BST_MMS_CATEGORY_T");
        truncateList.add("ETC_BST_MMS_OPENCLLCTLIST_T");
        //truncateList.add("E_BST_MMS_OPENCLLCTPHOTOLIST_T");
        //truncateList.add("ETC_BST_MMS_OPENPHOTOLIST_T");
        //truncateList.add("E_BST_OPENPHOTOCATEGORYLIST_T");

        test.setTruncateList(truncateList);
        test.runProcess();
    }

    /******************************************************
     * Setter Methods
     * @param sqlFilePath the sql file path
     */
    public void setSqlFilePath(String sqlFilePath) {
        this.sqlFilePath = sqlFilePath;
    }

    /**
     * Sets truncate list.
     *
     * @param truncateList the truncate list
     */
    public void setTruncateList(List<String> truncateList) {
        Collections.copy(this.truncateList, truncateList);
    }

    /**
     * Sets truncate table yn.
     *
     * @param truncateTableYn the truncate table yn
     */
    public void setTruncateTableYn(Boolean truncateTableYn) {
        this.truncateTableYn = truncateTableYn.booleanValue();
    }

    /**
     * Sets sql batch size.
     *
     * @param sqlBatchSize the sql batch size
     */
    public void setSqlBatchSize(Integer sqlBatchSize) {
        this.sqlBatchSize = sqlBatchSize.intValue();
    }

    /******************************************************
     * Main Threads
     * @return the workflow response
     */
    public WorkflowResponse runProcess() {
        WorkflowResponse workflowResult = new WorkflowResponse();

        //TODO 삭제 및 수정 예정
        truncateList = new ArrayList<>();
        if (truncateTableYn) {
            truncateList.add("ETC_BST_MMS_CATEGORY_T");
            truncateList.add("ETC_BST_MMS_OPENCLLCTLIST_T");
            truncateList.add("E_BST_MMS_OPENCLLCTPHOTOLIST_T");
            truncateList.add("ETC_BST_MMS_OPENPHOTOLIST_T");
            truncateList.add("E_BST_OPENPHOTOCATEGORYLIST_T");
        }

        try {
            /*if (this.jdbcTemplate == null) {
                this.jdbcTemplate = AppContextManager.getBean(JdbcTemplate.class);
            }*/

            this.jdbcTemplate = new JdbcTemplate(getDataSource());

            if (truncateTableYn) {
                if (truncateList == null || truncateList.size() == 0) {
                    throw new Exception("truncate list is empty");
                }

                truncateTable();
            }

            if (StringUtils.isEmpty(sqlFilePath)) {
                throw new NullPointerException("sql file path is null");
            }

            // SQL Injection
            executeSQL();

            // Move file to backup directory
            copyFileList(getSqlFileList(sqlFilePath), sqlFilePath + File.separator + "backup", true);

            workflowResult.setSuccess(true);
            workflowResult.setMessage("Success");
        } catch (Exception e) {
            log.error("Process Injest service Error", e);
            workflowResult.setSuccess(false);
            workflowResult.setMessage(e.getMessage());
        } finally {
            log.error("Process Injest service Terminated");
            return workflowResult;
        }
    }

    /******************************************************
     * Process Methods
     *******************************************************/
    private void truncateTable() throws Exception {
        log.info("Truncated table processing started");

        for (int i = 0; i < truncateList.size(); i++) {
            truncateList.set(i, "Truncate table " + truncateList.get(i));
        }

        dataProcessing(truncateList);
    }

    private void executeSQL() throws Exception {
        log.info("SQL Injection started");

        // sql 파일 목록 추출
        File[] sqlFileList = getSqlFileList(sqlFilePath);

        if (sqlFileList.length == 0) {
            throw new Exception("there is no SQL files in Path : " + sqlFilePath);
        }

        List<String> sqlList = new ArrayList<>();

        LineIterator lineIterator = null;
        int sqlCount = 0;

        for (int i = 0; i < sqlFileList.length; i++) {
            lineIterator = FileUtils.lineIterator(sqlFileList[i], "EUC-KR");

            sqlCount = 0;
            while (lineIterator.hasNext()) {
                sqlCount++;
                sqlList.add(lineIterator.nextLine().replace(";", ""));

                if (sqlList.size() > sqlBatchSize - 1) {
                    log.info("SQL Batch Size is [{}]", sqlList.size());

                    dataProcessing(sqlList);
                    sqlList.clear();
                }
            }

            log.info("FILE : [{}] , SQL Counts: [{}]", sqlFileList[i].getName(), sqlCount);
        }

        dataProcessing(sqlList);
        LineIterator.closeQuietly(lineIterator);
    }

    /**
     * 데이터 처리 함수
     *
     * @param sqlList
     */
    private void dataProcessing(List<String> sqlList) throws Exception {
        log.info("Data processing started.");
        jdbcTemplate.batchUpdate(sqlList.toArray(new String[0]));
    }

    /**
     * Zip 파일 목록을 가져온다.
     *
     * @param filePath
     * @return
     */
    private File[] getSqlFileList(String filePath) throws IOException {
        File[] listFiles = new File(filePath).listFiles(
                (dir, name) -> {
                    return name.toLowerCase().endsWith(".sql");
                }
        );

        return listFiles;
    }

    /**
     * 파일들을 복사한다.
     *
     * @param fileList
     * @param destDir
     * @param isMove
     * @throws IOException
     */
    private void copyFileList(File[] fileList, String destDir, boolean isMove) throws IOException {
        File dir = new File(destDir);
        if (!dir.exists()) {
            dir.mkdir();
        }

        for (int i = 0; i < fileList.length; i++) {
            if (isMove) {
                Files.move(Paths.get(fileList[i].toURI()), Paths.get(destDir, fileList[i].getName()), StandardCopyOption.REPLACE_EXISTING);
            } else {
                Files.copy(Paths.get(fileList[i].toURI()), Paths.get(destDir, fileList[i].getName()), StandardCopyOption.REPLACE_EXISTING);
            }

            log.info("File is {} to {}", isMove == true ? "moved." : "copied.", destDir);
        }
    }

    /**
     * Gets data source.
     *
     * @return the data source
     */
    public DriverManagerDataSource getDataSource() {

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        try {
            dataSource.setDriverClassName("oracle.jdbc.driver.OracleDriver");
            dataSource.setUsername("ams");
            dataSource.setPassword("ams");
            dataSource.setUrl("jdbc:oracle:thin:@//rmhost.iptime.org:1521/amsdb");
        } catch (Exception e) {
            log.error("Database Connection Error");
        }

        return dataSource;
    }

    /**
     * Sets jdbc template.
     *
     * @param jdbcTemplate the jdbc template
     */
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
