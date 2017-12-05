/*
 * Copyright (c) 2017. RMSoft Co.,Ltd. All rights reserved
 */

package rmsoft.ams.seoul.wf.wf004.vo;

import io.onsemiro.core.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * Wf00402VO
 *
 * @author james
 * @version 1.0.0
 * @since 2017-10-23 오후 2:06
 **/
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Wf00402VO extends BaseVO {
    private String jobResultUuid;

    private String workflowResultUuid;

    private String statusUuid;

    private long sequence;

    private String jobUuid;

    private String jobName;

    private String api;

    private String skipYn;

    private String terminateYn;

    private String message;

    private Timestamp startDate;

    private Timestamp endDate;

}