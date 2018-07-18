/*
 * Copyright (c) 2017. RMSoft Co.,Ltd. All rights reserved
 */

package rmsoft.ams.seoul.wf.wf001.vo;

import io.onsemiro.core.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Wf00101VO
 *
 * @author james
 * @version 1.0.0
 * @since 2017 -10-23 오후 2:06
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Wf00101VO extends BaseVO {
    private String jobUuid;

    private String jobName;

    private String api;

    private String useYn;
}