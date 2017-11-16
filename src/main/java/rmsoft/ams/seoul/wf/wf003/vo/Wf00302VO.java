/*
 * Copyright (c) 2017. RMSoft Co.,Ltd. All rights reserved
 */

package rmsoft.ams.seoul.wf.wf003.vo;

import io.onsemiro.core.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Wf00302VO
 *
 * @author james
 * @version 1.0.0
 * @since 2017-10-23 오후 2:06
 **/
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Wf00302VO extends BaseVO {
    private String workflowJobUuid;

    private String workflowUuid;

    private long sequence;

    private String jobUuid;

    private String jobName;

    private String api;

    private String skipYn;

    private String terminateYn;

    private String useYn;

    private List<Wf00301_P0102VO> parameterList;
}