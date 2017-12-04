/*
 * Copyright (c) 2017. RMSoft Co.,Ltd. All rights reserved
 */

package rmsoft.ams.seoul.rms.ingest;


import io.onsemiro.core.context.AppContextManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ProcessIngestProcedure {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static String procedureName = "AD_SEOUL_ARCHIVE_CONV_T.main";

    public static void main(String[] args) {
        ProcessIngestProcedure test = new ProcessIngestProcedure();
        test.setJdbcTemplate(new JdbcTemplate(test.getDataSource()));

        test.setProcedureName("AD_SEOUL_ARCHIVE_CONV_T.main");
        test.runProcess();
    }

    public void setProcedureName(String procedureName) {
        this.procedureName = procedureName;
    }

    public boolean runProcess() {
        boolean isSuccess = false;

        try {
            if (this.jdbcTemplate == null) {
                this.jdbcTemplate = AppContextManager.getBean(JdbcTemplate.class);
            }

            if (StringUtils.isEmpty(procedureName)) {
                throw new NullPointerException("procedure name is null");
            }

            // Call Procedure
            callProcedure(procedureName);

            isSuccess = true;

        } catch (Exception e) {
            log.error("Process Injest Procedure service Error", e);
            isSuccess = false;

        } finally {
            log.error("Process Injest Procedure service Terminated");
            return isSuccess;
        }
    }

    public void callProcedure(String procedureName) throws Exception {
        log.info("Start Procedure : {}", procedureName);

        jdbcTemplate.execute("{CALL " + procedureName + "}");

        log.info("Finished Procedure : {}", procedureName);
    }

    public DriverManagerDataSource getDataSource() {

        DriverManagerDataSource dataSource = new org.springframework.jdbc.datasource.DriverManagerDataSource();
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

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
