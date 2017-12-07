/*
 * Copyright (c) 2017. RMSoft Co.,Ltd. All rights reserved
 */

package rmsoft.ams.seoul.common.workflow;

import io.onsemiro.core.context.AppContextManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import rmsoft.ams.seoul.wf.wf003.vo.Wf00303VO;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class WorkflowManager {
    private final static Map<String, Runnable> threadStopMap = new HashMap<>();

    public void invokeProcess(String batchId, Wf00303VO wf00303VO) {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = AppContextManager.getBean(ThreadPoolTaskExecutor.class);
        WorkflowProcess workflowProcess = AppContextManager.getBean(WorkflowProcess.class);
        workflowProcess.setWorkflow(wf00303VO);

        try {
            // 쓰레드 저장
            threadStopMap.put(batchId, workflowProcess);
            threadPoolTaskExecutor.execute(workflowProcess);
        } catch (Exception e) {
            errorLogging(e);
            log.error("WorkflowManager invokeProcess Error", e);
        }
    }

    public void stopProcess(String batchId) {
        WorkflowProcess runningThread = (WorkflowProcess) threadStopMap.get(batchId);

        if (runningThread != null) {
            try {
                runningThread.stopProcess();

                // 성공시에만 삭제
                threadStopMap.remove(batchId);

            } catch (Exception e) {
                errorLogging(e);
                log.error("WorkflowManager stopProcess Error", e);
            }
        }
    }

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
