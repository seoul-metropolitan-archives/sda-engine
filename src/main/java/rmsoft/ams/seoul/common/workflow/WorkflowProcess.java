/*
 * Copyright (c) 2017. RMSoft Co.,Ltd. All rights reserved
 */

package rmsoft.ams.seoul.common.workflow;

import com.querydsl.core.types.Predicate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rmsoft.ams.seoul.common.domain.QWfJobResult;
import rmsoft.ams.seoul.common.domain.QWfWorkflowResult;
import rmsoft.ams.seoul.common.domain.WfJobResult;
import rmsoft.ams.seoul.common.domain.WfWorkflowResult;
import rmsoft.ams.seoul.common.repository.WfJobResultRepository;
import rmsoft.ams.seoul.common.repository.WfWorkflowResultRepository;
import rmsoft.ams.seoul.utils.CommonCodeUtils;
import rmsoft.ams.seoul.wf.wf003.vo.Wf00302VO;
import rmsoft.ams.seoul.wf.wf003.vo.Wf00303VO;

import java.util.List;

@Slf4j
@Component
public class WorkflowProcess implements Runnable {
    @Autowired
    WorkflowExecutor workflowExecutor;

    @Autowired
    private WfWorkflowResultRepository wfWorkflowResultRepository;

    @Autowired
    private WfJobResultRepository wfJobResultRepository;

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

            // Workflow Running Status Update
            updateWorkflowResult(wf00303VO, getStatusUuid("CD131", WorkflowResultStatus.실행.getCode()));

            workflowJobList = wf00303VO.getWorkflowJobList();

            log.info("Thread with [{}] : {}", runThread.getId(), runThread.getName());

            int successCnt = 0;
            int failCnt = 0;

            for (int i = 0; i < workflowJobList.size(); i++) {
                currentJob = workflowJobList.get(i);

                if (!currentJob.getSkipYn().equals("Y")) {
                    // Workflow Running Status Update
                    updateJobResult(currentJob, getStatusUuid("CD130", JobResultStatus.실행.getCode()), "");

                    log.info("JobUuid : [{}] , JobName : [{}] is started", currentJob.getJobUuid(), currentJob.getJobName());
                    WorkflowResult callResult = workflowExecutor.invokeJobProcess(workflowJobList.get(i));

                    if (!callResult.isSuccess()) {
                        failCnt++;

                        // 결과 등록
                        updateJobResult(currentJob, getStatusUuid("CD130", JobResultStatus.에러.getCode()), callResult.getMessage());
                        break;
                    } else {
                        successCnt++;

                        // 결과 등록
                        updateJobResult(currentJob, getStatusUuid("CD130", JobResultStatus.성공.getCode()), callResult.getMessage());
                    }

                } else {
                    successCnt++;
                    log.info("JobUuid : [{}] , JobName : [{}] is skipped.", currentJob.getJobUuid(), currentJob.getJobName());
                }
            }

            if (failCnt > 0) {
                // 에러가 1개라도 있되, Job 1 1개이면 error, 1개 이상이면 partial
                if (workflowJobList.size() > 1) {
                    updateWorkflowResult(wf00303VO, getStatusUuid("CD131", WorkflowResultStatus.부분에러.getCode()));
                } else {
                    updateWorkflowResult(wf00303VO, getStatusUuid("CD131", WorkflowResultStatus.에러.getCode()));
                }
            } else {
                updateWorkflowResult(wf00303VO, getStatusUuid("CD131", WorkflowResultStatus.성공.getCode()));
            }

        } catch (Exception e) {
            log.error("Workflow Process Service Error", e);
        } finally {
            log.info("Thread with [{}] : {} is Terminated.", runThread.getId(), runThread.getName());
        }
    }

    private void updateWorkflowResult(Wf00303VO wf00303VO, String workflowResultStatus) {
        // Workflow Result 찾기
        QWfWorkflowResult qWfWorkflowResult = QWfWorkflowResult.wfWorkflowResult;
        Predicate predicate = qWfWorkflowResult.workflowResultUuid.eq(wf00303VO.getWorkflowResultUuid());
        WfWorkflowResult wfWorkflowResult = wfWorkflowResultRepository.findOne(predicate);

        if (wfWorkflowResult != null) {
            wfWorkflowResult.setStatusUuid(workflowResultStatus);
            wfWorkflowResultRepository.save(wfWorkflowResult);
        }
    }

    private void updateJobResult(Wf00302VO wf00302VO, String jobResultStatus, String message) {
        QWfJobResult qWfJobResult = QWfJobResult.wfJobResult;
        Predicate predicate = qWfJobResult.jobResultUuid.eq(wf00302VO.getJobResultUuid());
        WfJobResult wfJobResult = wfJobResultRepository.findOne(predicate);

        if (wfJobResult != null) {
            wfJobResult.setStatusUuid(jobResultStatus);
            wfJobResult.setMessage(message);
            wfJobResultRepository.save(wfJobResult);
        }
    }

    private String getStatusUuid(String codeGroup, String code) {
        return CommonCodeUtils.getCodeDetailUuidByCode(codeGroup, code);
    }

    public void stopProcess() {
        runThread.interrupt();
    }
}
