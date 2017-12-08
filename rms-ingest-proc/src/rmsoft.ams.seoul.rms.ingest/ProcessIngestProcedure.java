/*
 * Copyright (c) 2017. RMSoft Co.,Ltd. All rights reserved
 */

package rmsoft.ams.seoul.rms.ingest;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Service;
import rmsoft.ams.seoul.common.workflow.WorkflowResponse;

/**
 * The type Process ingest procedure.
 */
@Slf4j
@Service
public class ProcessIngestProcedure {
    private JdbcTemplate jdbcTemplate;

    private static String procedureName = "AD_SEOUL_ARCHIVE_CONV_T.main";

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        ProcessIngestProcedure test = new ProcessIngestProcedure();
        test.setJdbcTemplate(new JdbcTemplate(test.getDataSource()));

        test.setProcedureName("AD_SEOUL_ARCHIVE_CONV_T.main");
        test.runProcess();
    }

    /**
     * Sets procedure name.
     *
     * @param procedureName the procedure name
     */
    public void setProcedureName(String procedureName) {
        this.procedureName = procedureName;
    }

    /**
     * Run process workflow response.
     *
     * @return the workflow response
     */
    public WorkflowResponse runProcess() {
        WorkflowResponse workflowResult = new WorkflowResponse();

        try {
           /*if (this.jdbcTemplate == null) {
                this.jdbcTemplate = AppContextManager.getBean(JdbcTemplate.class);
            }*/

            this.jdbcTemplate = new JdbcTemplate(getDataSource());

            if (StringUtils.isEmpty(procedureName)) {
                throw new NullPointerException("procedure name is null");
            }

            // Call Procedure
            callProcedure(procedureName);

            workflowResult.setSuccess(true);
            workflowResult.setMessage("Success");

        } catch (Exception e) {
            log.error("Process Injest Procedure service Error", e);
            workflowResult.setSuccess(false);
            workflowResult.setMessage(e.getMessage());
        } finally {
            log.info("Process Injest Procedure service Terminated");
            return workflowResult;
        }
    }

    /**
     * Call procedure.
     *
     * @param procedureName the procedure name
     * @throws Exception the exception
     */
    public void callProcedure(String procedureName) throws Exception {
        log.info("Start Procedure : {}", procedureName);

        jdbcTemplate.execute("{CALL " + procedureName + "}");

        log.info("Finished Procedure : {}", procedureName);
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
