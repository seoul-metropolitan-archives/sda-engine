/*
 * Copyright (c) 2017. RMSoft Co.,Ltd. All rights reserved
 */

package rmsoft.ams.seoul.wf.wf999.vo;

import io.onsemiro.core.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Wf00403VO
 *
 * @author james
 * @version 1.0.0
 * @since 2017 -10-23 오후 2:06
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Wf99903VO extends BaseVO {
    private String parameterResultUuid;

    private String jobResultUuid;

    private String parameterName;

    private String inputCodeUuid;

    private String inputCode;

    private String inputCodeName;

    private String inputMethodUuid;

    private String inOutUuid;

    private String value;

    private String displayYn;

    private String requiredYn;

}