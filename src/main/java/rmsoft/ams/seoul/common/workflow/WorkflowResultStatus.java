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
 * @since 2017 -12-04 오후 1:17
 */
@Getter
public enum WorkflowResultStatus {

    /**
     * 초기상태 workflow result status.
     */
    초기상태("PENDING"), /**
     * 실행 workflow result status.
     */
    실행("RUNNING"), /**
     * 성공 workflow result status.
     */
    성공("SUCCESS"), /**
     * 에러 workflow result status.
     */
    에러("ERROR"), /**
     * 부분에러 workflow result status.
     */
    부분에러("PARTIAL"), /**
     * 종료 workflow result status.
     */
    종료("TERMINATED");

    private String code;

    WorkflowResultStatus(String code) {
        this.code = code;
    }
}


