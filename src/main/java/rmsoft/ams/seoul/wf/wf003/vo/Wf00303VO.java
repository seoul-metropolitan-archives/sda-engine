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
 * Wf00301VO
 *
 * @author james
 * @version 1.0.0
 * @since 2017 -10-23 오후 2:06
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Wf00303VO extends BaseVO {
    private String workflowUuid;

    private String workflowName;

    private String serviceUuid;

    private String menuUuid;

    private String useYn;

    private String workflowResultUuid;

    private List<Wf00302VO> workflowJobList;
}