/*
 * Copyright (c) 2017. RMSoft Co.,Ltd. All rights reserved
 */

package rmsoft.ams.seoul.workflow;


import io.onsemiro.core.context.AppContextManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Service;
import rmsoft.ams.seoul.common.workflow.WorkflowResponse;
import rmsoft.ams.seoul.utils.CommonCodeUtils;

import javax.inject.Inject;
import java.io.File;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Slf4j
@Service
public class ExtractFileHash {
    @Inject
    private JdbcTemplate jdbcTemplate;

    private String aggregationUuid = "";
    private String contentsRootPath = "";

    public void setAggregationUuid(String aggregationUuid) {
        this.aggregationUuid = aggregationUuid;
    }

    public void setContentsRootPath(String contentsRootPath) {
        this.contentsRootPath = contentsRootPath;
    }

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */

    public static void main(String[] args) {
        ExtractFileHash test = new ExtractFileHash();
        test.setJdbcTemplate(new JdbcTemplate(test.getDataSource()));

        test.setAggregationUuid("68BE6E59-B6C7-4379-8B02-1A0F387EDFCA");
        test.setContentsRootPath("/Users/jspark226/devspace/contents");

        test.runProcess();
    }

    /******************************************************
     * Main Threads
     * @return the workflow response
     */
    public WorkflowResponse runProcess() {
        WorkflowResponse workflowResult = new WorkflowResponse();

        if (StringUtils.isEmpty(aggregationUuid)) {
            workflowResult.setSuccess(false);
            workflowResult.setMessage("aggregationUuid parameter value is empty");
            return workflowResult;
        }

        if (StringUtils.isEmpty(contentsRootPath)) {
            workflowResult.setSuccess(false);
            workflowResult.setMessage("contentsRootPath parameter value is empty");
            return workflowResult;
        }

        this.jdbcTemplate = AppContextManager.getBean(JdbcTemplate.class);  // 서버 반영시 풀고..
        try {
            // SQL Injection
            StringBuilder queryStr = new StringBuilder();

            queryStr.append(" SELECT T1.COMPONENT_UUID AS COMPONENT_UUID, \n");
            queryStr.append("        T1.FILE_PATH AS FILE_PATH, \n");
            queryStr.append("        T1.FILE_NAME AS FILE_NAME, \n");
            queryStr.append("        T1.ORIGINAL_FILE_NAME AS ORG_FILE_NAME \n");
            queryStr.append(" FROM  RC_COMPONENT T1, ( \n");
            queryStr.append("         SELECT A.COMPONENT_UUID \n");
            queryStr.append("           FROM RC_ITEM_COMPONENT A, \n");
            queryStr.append("                ( SELECT ITEM_UUID \n");
            queryStr.append("                    FROM RC_ITEM WHERE AGGREGATION_UUID='" + aggregationUuid + "' \n");
            queryStr.append("                  UNION ALL \n");
            queryStr.append("                  SELECT ITEM_UUID \n");
            queryStr.append("                    FROM RC_ITEM WHERE AGGREGATION_UUID IN \n");
            queryStr.append("                    ( SELECT AGGREGATION_UUID \n");
            queryStr.append("                        FROM RC_AGGREGATION \n");
            queryStr.append("                             START WITH PARENT_AGGREGATION_UUID = '" + aggregationUuid + "' \n");
            queryStr.append("                             CONNECT BY PRIOR AGGREGATION_UUID = PARENT_AGGREGATION_UUID \n");
            queryStr.append("                    ) \n");
            queryStr.append("                 ) B \n");
            queryStr.append("           WHERE A.ITEM_UUID = B.ITEM_UUID \n");
            queryStr.append("        ) T2 \n");
            queryStr.append(" WHERE T1.COMPONENT_UUID = T2.COMPONENT_UUID \n");

            List<Map<String, Object>> resultList = jdbcTemplate.queryForList(queryStr.toString());
            log.info("Data Total Counts : {}", resultList.size());

            queryStr.delete(0, queryStr.length());


            List<String> sqlList = null;
            if (resultList.size() > 0) {
                sqlList = new ArrayList<>();

                for (int i = 0; i < resultList.size(); i++) {

                    String filePath = contentsRootPath + (String) resultList.get(i).get("FILE_PATH");
                    String fileName = (String) resultList.get(i).get("FILE_NAME");

                    //  디렉토리생성
                    File tmpPath = new File(filePath + fileName);
                    if (Files.exists(tmpPath.toPath()) && Files.isReadable(tmpPath.toPath())) {
                        log.info("FilePath : " + filePath + fileName);

                        String hashValue = extractFileHashSHA256(tmpPath);
                        log.info("Hash Value : " + hashValue);

                        if (!StringUtils.isEmpty(hashValue)) {
                            queryStr.append("UPDATE RC_COMPONENT \n");
                            queryStr.append("SET CHECKSUM = '" + hashValue + "' \n");
                            queryStr.append("   ,CHECKSUM_TYPE_UUID = '3BF24131-3972-4BBD-825F-5AA867E52AA2' \n");
                            queryStr.append("WHERE COMPONENT_UUID = '" + (String) resultList.get(i).get("COMPONENT_UUID") + "' \n");

                            sqlList.add(queryStr.toString());
                            queryStr.delete(0, queryStr.length());
                        }
                    } else {
                        // Missing File
                        queryStr.append("UPDATE RC_COMPONENT \n");
                        queryStr.append("SET CHECKSUM_TYPE_UUID = '0CE2B767-F3F3-46C6-9AE3-9E17067ABBAA' \n");
                        queryStr.append("WHERE COMPONENT_UUID = '" + (String) resultList.get(i).get("COMPONENT_UUID") + "' \n");

                        sqlList.add(queryStr.toString());
                        queryStr.delete(0, queryStr.length());
                    }
                }

                jdbcTemplate.batchUpdate(sqlList.toArray(new String[0]));
            }

            workflowResult.setSuccess(true);
            workflowResult.setMessage("Success");
        } catch (Exception e) {
            log.error("Process ExtractFileHash service Error", e);
            workflowResult.setSuccess(false);
            workflowResult.setMessage(e.getClass().getSimpleName() + ":" + e.getMessage());
        } finally {
            log.info("Process ExtractFileHash service terminated");
            return workflowResult;
        }
    }

    /******************************************************
     * Process Methods
     *******************************************************/

    public String extractFileHashSHA256(File file) {

        String SHA = "";
        int buff = 16384;

        try {

            RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");

            MessageDigest hashSum = MessageDigest.getInstance("SHA-256");

            byte[] buffer = new byte[buff];
            byte[] partialHash = null;

            long read = 0;

            // calculate the hash of the hole file for the test
            long offset = randomAccessFile.length();
            int unitsize;
            while (read < offset) {
                unitsize = (int) (((offset - read) >= buff) ? buff : (offset - read));
                randomAccessFile.read(buffer, 0, unitsize);

                hashSum.update(buffer, 0, unitsize);

                read += unitsize;
            }

            randomAccessFile.close();
            partialHash = new byte[hashSum.getDigestLength()];
            partialHash = hashSum.digest();

            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < partialHash.length; i++) {
                sb.append(Integer.toString((partialHash[i] & 0xff) + 0x100, 16).substring(1));
            }
            SHA = sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return SHA;
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
            dataSource.setUrl("jdbc:oracle:thin:@//rmhost.iptime.org:11521/amsdb");
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
