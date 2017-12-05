/*
 * Copyright (c) 2017. RMSoft Co.,Ltd. All rights reserved
 */

package rmsoft.ams.seoul.common.workflow;

import lombok.Getter;

/**
 * WorkflowStatus
 *
 * @author james
 * @version 1.0.0
 * @since 2017-12-04 오후 1:17
 **/
@Getter
public enum JobResultStatus {

    초기상태("PENDING"), 실행("RUNNING"), 성공("SUCCESS"), 에러("ERROR"), 종료("TERMINATED");

    private String code;

    JobResultStatus(String code) {
        this.code = code;
    }
}


