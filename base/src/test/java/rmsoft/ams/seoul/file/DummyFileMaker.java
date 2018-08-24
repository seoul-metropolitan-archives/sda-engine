package rmsoft.ams.seoul.file;


import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.io.*;
import java.math.BigDecimal;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Map;

@Slf4j
public class DummyFileMaker {
    private JdbcTemplate jdbcTemplate;
    private static String dummyFilePath = "D:\\02.DreamAntDev\\git-workspaces\\java\\seoul-ams\\dip\\tmp\\dummy-img.png"; // 더미로 사용할 파일
    private static String targetDir = "D:\\02.DreamAntDev\\git-workspaces\\java\\seoul-ams\\dip\\tmp\\"; //임의의 더미 파일을 저장할 경로


    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        DummyFileMaker test = new DummyFileMaker();
        test.runProcess();
    }


    /******************************************************
     * Main Threads
     * @return the workflow response
     */
    public void runProcess() {
        try {
            /*if (this.jdbcTemplate == null) {
                this.jdbcTemplate = AppContextManager.getBean(JdbcTemplate.class);
            }*/

            log.info("Database Connecting...");
            this.jdbcTemplate = new JdbcTemplate(getDataSource());

            // get new instance for dummy file
            log.info("Get Dummy File...");
            File dummyFile = new File(dummyFilePath);

            log.info("Get Data Lists");

            String testSql = "SELECT COUNT(*) AS TOTAL_COUNT FROM EX_EXPORT_COMPONENT_FILE";
            Map<String, Object> returnMap = jdbcTemplate.queryForMap(testSql);
            BigDecimal dbAliveStr = (BigDecimal)returnMap.get("TOTAL_COUNT");

            // SQL Injection
            String tmpSql = "SELECT FILE_PATH, FILE_NAME FROM EX_EXPORT_COMPONENT_FILE";

            List<Map<String, Object>> resultList = jdbcTemplate.queryForList(tmpSql);
            log.info("Data Total Counts : {}", resultList.size());

            if (resultList.size() > 0) {
                for (int i = 0; i < resultList.size(); i++) {
                    String filePath = targetDir + File.separator + (String) resultList.get(i).get("FILE_PATH");
                    String fileName = (String) resultList.get(i).get("FILE_NAME");

                    //  디렉토리생성
                    File tmpPath = new File(filePath);
                    if (Files.notExists(tmpPath.toPath())) {
                        Files.createDirectories(tmpPath.toPath());
                    }

                    File tmpFile = new File(filePath + File.separator + fileName);
//                    if (Files.notExists(tmpFile.toPath())) {
//                        Files.createFile(tmpFile.toPath());
//                    }

                    Files.copy(dummyFile.toPath(), tmpFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    log.info("[{}] : File : {} is created", i, resultList.get(i).get("FILE_PATH") + "/" + resultList.get(i).get("FILE_NAME"));
                }
            }

        } catch (Exception e) {
            log.error("Dummy File Maker Error", e);
        }
    }

    private static void copyFileUsingStream(File source, File dest) throws IOException {
        InputStream is = null;
        OutputStream os = null;
        try {
            is = new FileInputStream(source);
            os = new FileOutputStream(dest);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        } finally {
            is.close();
            os.close();
        }
    }


    /**
     * Gets data source.
     *
     * @return the data source
     */
    public DriverManagerDataSource getDataSource() {

        DriverManagerDataSource dataSource = new org.springframework.jdbc.datasource.DriverManagerDataSource();
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
