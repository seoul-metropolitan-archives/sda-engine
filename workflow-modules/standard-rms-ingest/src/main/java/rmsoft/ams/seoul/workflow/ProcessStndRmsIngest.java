/*
 * Copyright (c) 2017. RMSoft Co.,Ltd. All rights reserved
 */

package rmsoft.ams.seoul.workflow;


import io.onsemiro.core.context.AppContextManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import rmsoft.ams.seoul.common.workflow.WorkflowResponse;
import rmsoft.ams.seoul.wf.wf999.service.Wf999Service;


@Slf4j
@Service
public class ProcessStndRmsIngest {
    private String contentsRootPath = "";

    public void setContentsRootPath(String contentsRootPath) {
        this.contentsRootPath = contentsRootPath;
    }

    /******************************************************
     * Main Threads
     * @return the workflow response
     */
    public WorkflowResponse runProcess() {
        WorkflowResponse workflowResult = new WorkflowResponse();

        if (StringUtils.isEmpty(contentsRootPath)) {
            workflowResult.setSuccess(false);
            workflowResult.setMessage("contentsRootPath parameter value is empty");
            return workflowResult;
        }

        try {
            Wf999Service wf999Service = AppContextManager.getBean(Wf999Service.class);

            if(wf999Service != null){
                wf999Service.workflowIngestExcel(contentsRootPath);

                workflowResult.setSuccess(true);
                workflowResult.setMessage("Success");
            }else{
                workflowResult.setSuccess(false);
                workflowResult.setMessage("Could not find Wf999Service");
            }
        } catch (Exception e) {
            log.error("Process Standard RMS Ingest service Error", e);
            workflowResult.setSuccess(false);
            workflowResult.setMessage(e.getMessage());
        } finally {
            log.info("Process Standard RMS Ingest service terminated");
            return workflowResult;
        }
    }

    /******************************************************
     * Process Methods
     *******************************************************/

}
