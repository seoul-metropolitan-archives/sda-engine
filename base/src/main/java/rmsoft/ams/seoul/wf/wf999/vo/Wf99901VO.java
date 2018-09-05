/*
 * Copyright (c) 2017. RMSoft Co.,Ltd. All rights reserved
 */

package rmsoft.ams.seoul.wf.wf999.vo;

import io.onsemiro.core.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * Wf00401VO
 *
 * @author james
 * @version 1.0.0
 * @since 2017 -10-23 오후 2:06
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Wf99901VO extends BaseVO {
    private String workflowResultUuid;

    private String workflowUuid;

    private String statusUuid;

    private String batchId;

    private String workflowName;

    private String serviceUuid;

    private String executerUuid;

    private String munuName;

    private Timestamp startDate;

    private Timestamp endDate;

    private String executer;

    private String menu;

    private String startFromDate;

    private String startToDate;

    private String endFromDate;

    private String endToDate;


}