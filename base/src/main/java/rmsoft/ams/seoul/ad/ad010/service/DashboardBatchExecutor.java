/*
 * Copyright (c) 2017. RMSoft Co.,Ltd. All rights reserved
 */

package rmsoft.ams.seoul.ad.ad010.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


/**
 * The type Dashboard batch executor.
 */
@Slf4j
@Component
@Conditional(DashboardBatchExecutionCondition.class)
public class DashboardBatchExecutor {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    /**
     * Execute.
     */
    @Scheduled(cron = "${dashboard.batch.cron}")
    public void execute() {
        runDashboardProcess();
    }

    /**
     * Run dip process.
     */
    public void runDashboardProcess() {
        log.info("DashboardBatchExecutor-execute Dashboard Process");

        try {

            jdbcTemplate.batchUpdate("call ad_main_statistics()");


            log.info("DashboardBatchExecutor-end Dashboard Process");

        } catch (Exception e) {
            errorLogging(e);
            log.error("Dashboard Process Error : " + e.getMessage());
        }
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
