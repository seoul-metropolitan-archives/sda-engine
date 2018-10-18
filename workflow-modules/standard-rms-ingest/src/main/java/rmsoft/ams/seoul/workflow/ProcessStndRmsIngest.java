/*
 * Copyright (c) 2017. RMSoft Co.,Ltd. All rights reserved
 */

package rmsoft.ams.seoul.workflow;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.lang3.StringUtils;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Service;
import rmsoft.ams.seoul.common.workflow.WorkflowResponse;
import rmsoft.ams.seoul.utils.SocketMsgUtils;
import rmsoft.ams.seoul.workflow.xls.AMSXlsDataSet;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Slf4j
@Service
public class ProcessStndRmsIngest {
    private JdbcTemplate jdbcTemplate;

    private String xlsFilePath = "";
    private String xlsFileName = "";
    private String schemeName = "";

    public void setXlsFilePath(String xlsFilePath) {
        this.xlsFilePath = xlsFilePath;
    }

    public void setXlsFileName(String xlsFileName) {
        this.xlsFileName = xlsFileName;
    }

    public void setSchemeName(String schemeName) {
        this.schemeName = schemeName;
    }

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */

    public static void main(String[] args) {
        ProcessStndRmsIngest test = new ProcessStndRmsIngest();
        test.setJdbcTemplate(new JdbcTemplate(test.getDataSource()));

        test.setXlsFilePath(SocketMsgUtils.getDbDatesetDir());
        test.setXlsFileName("20181008_업무관리시스템_철건파일목록.xlsx");
        test.setSchemeName("ams");

        test.runProcess();
    }

    /******************************************************
     * Main Threads
     * @return the workflow response
     */
    public WorkflowResponse runProcess() {
        WorkflowResponse workflowResult = new WorkflowResponse();
        this.jdbcTemplate = new JdbcTemplate(getDataSource());

        IDatabaseConnection dbUnitCon = null;

        try {
            dbUnitCon = new DatabaseConnection(jdbcTemplate.getDataSource().getConnection(), schemeName);
            IDataSet dataset = new AMSXlsDataSet(new File(xlsFilePath + xlsFileName));
            DatabaseOperation.CLEAN_INSERT.execute(dbUnitCon, dataset);

            workflowResult.setSuccess(true);
            workflowResult.setMessage("Success");
        } catch (Exception e) {
            log.error("Process Standard RMS Ingest service Error", e);
            workflowResult.setSuccess(false);
            workflowResult.setMessage(e.getMessage());
        } finally {
            try {
                dbUnitCon.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            log.info("Process Standard RMS Ingest service terminated");
            return workflowResult;
        }
    }

    /******************************************************
     * Process Methods
     *******************************************************/

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
