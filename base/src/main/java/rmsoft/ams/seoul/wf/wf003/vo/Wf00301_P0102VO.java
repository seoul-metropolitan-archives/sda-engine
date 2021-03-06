/*
 * Copyright (c) 2017. RMSoft Co.,Ltd. All rights reserved
 */

package rmsoft.ams.seoul.wf.wf003.vo;

import io.onsemiro.core.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Wf00301_P0102VO
 *
 * @author james
 * @version 1.0.0
 * @since 2017 -10-23 오후 2:06
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Wf00301_P0102VO extends BaseVO {
    private String parameterUuid;

    private String jobUuid;

    private String parameterName;

    private String inputCodeUuid;

    private String inputCode;

    private String inputCodeName;

    private String inputMethodUuid;

    private String inOutUuid;

    private String defaultValue;

    private String displayYn;

    private String requiredYn;

    private String useYn;

    private String parameterResultUuid;
}