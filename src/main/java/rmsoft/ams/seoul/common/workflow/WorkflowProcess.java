/*
 * Copyright (c) 2017. RMSoft Co.,Ltd. All rights reserved
 */

package rmsoft.ams.seoul.common.workflow;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rmsoft.ams.seoul.wf.wf003.vo.Wf00302VO;
import rmsoft.ams.seoul.wf.wf003.vo.Wf00303VO;

import java.util.List;

@Slf4j
@Component
public class WorkflowProcess implements Runnable {
    @Autowired
    WorkflowExecutor workflowExecutor;

    public String batchId = "";
    public String stopBatchId = "";

    private Wf00303VO wf00303VO = null;
    private Thread runThread = null;

    public void setWorkflow(Wf00303VO wf00303VO) {
        this.wf00303VO = wf00303VO;
    }

    @Override
    public void run() {
        runThread = Thread.currentThread();

        Wf00302VO currentJob = null;
        List<Wf00302VO> workflowJobList = null;

        try {
            if (wf00303VO == null) {
                throw new NullPointerException("target Workflow is null");
            }

            workflowJobList = wf00303VO.getWorkflowJobList();

            log.info("Thread with [{}] : {}", runThread.getId(), runThread.getName());

            for (int i = 0; i < workflowJobList.size(); i++) {
                currentJob = workflowJobList.get(i);

                if (!currentJob.getSkipYn().equals("Y")) {
                    log.info("JobUuid : [{}] , JobName : [{}] is started", currentJob.getJobUuid(), currentJob.getJobName());
                    workflowExecutor.invokeJobProcess(workflowJobList.get(i));
                } else {
                    log.info("JobUuid : [{}] , JobName : [{}] is skipped.", currentJob.getJobUuid(), currentJob.getJobName());
                }
            }

        } catch (Exception e) {
            log.error("Workflow Process Service Error", e);
        } finally {
            log.info("Thread with [{}] : {} is Terminated.", runThread.getId(), runThread.getName());
        }
    }

    public void stopProcess() {
        runThread.interrupt();
    }
}
