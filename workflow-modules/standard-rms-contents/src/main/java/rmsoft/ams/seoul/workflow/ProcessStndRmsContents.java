/*
 * Copyright (c) 2017. RMSoft Co.,Ltd. All rights reserved
 */

package rmsoft.ams.seoul.workflow;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import rmsoft.ams.seoul.common.workflow.WorkflowResponse;
import rmsoft.ams.seoul.utils.ArchiveUtils;

@Slf4j
@Service
public class ProcessStndRmsContents {
    private String uploadFilePath = "";
    private String contentsUploadServerPath = "";
    private String contentsRootPath = "";

    public void setUploadFilePath(String uploadFilePath) {
        this.uploadFilePath = uploadFilePath;
    }

    public void setContentsUploadServerPath(String contentsUploadServerPath) {
        this.contentsUploadServerPath = contentsUploadServerPath;
    }

    public void setContentsRootPath(String contentsRootPath) {
        this.contentsRootPath = contentsRootPath;
    }


    /******************************************************
     * Main Threads
     * @return the workflow response
     */
    public WorkflowResponse runProcess() {
        WorkflowResponse workflowResult = new WorkflowResponse();

        if (StringUtils.isEmpty(uploadFilePath)) {
            workflowResult.setSuccess(false);
            workflowResult.setMessage("uploadFilePath parameter value is empty");
            return workflowResult;
        }

        if (StringUtils.isEmpty(contentsUploadServerPath)) {
            workflowResult.setSuccess(false);
            workflowResult.setMessage("contentsUploadServerPath parameter value is empty");
            return workflowResult;
        }

        if (StringUtils.isEmpty(contentsRootPath)) {
            workflowResult.setSuccess(false);
            workflowResult.setMessage("contentsRootPath parameter value is empty");
            return workflowResult;
        }

        try {
            ArchiveUtils.extract(contentsUploadServerPath + uploadFilePath, contentsRootPath, "");

            workflowResult.setSuccess(true);
            workflowResult.setMessage("Success");
        } catch (Exception e) {
            log.error("Process Standard RMS Contents Upload service Error", e);
            workflowResult.setSuccess(false);
            workflowResult.setMessage(e.getMessage());
        } finally {
            log.info("Process Standard RMS Contents Upload service terminated");
            return workflowResult;
        }
    }
}
