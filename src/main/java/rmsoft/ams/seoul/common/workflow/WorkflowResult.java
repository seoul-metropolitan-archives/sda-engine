/*
 * Copyright (c) 2017. RMSoft Co.,Ltd. All rights reserved
 */

package rmsoft.ams.seoul.common.workflow;

import lombok.Data;

@Data
public class WorkflowResult {
    private boolean isSuccess = false;
    private String message = "";
}
