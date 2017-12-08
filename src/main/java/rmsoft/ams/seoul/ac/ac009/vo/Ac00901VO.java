/*
 * Copyright (c) 2017. RMSoft Co.,Ltd. All rights reserved
 */

package rmsoft.ams.seoul.ac.ac009.vo;

import io.onsemiro.core.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Ac00901VO
 *
 * @author james
 * @version 1.0.0
 * @since 2017 -10-23 오후 2:06
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Ac00901VO extends BaseVO {

    private String menuUuid;

    private String parentMenuCode;

    private String menuCode;

    private String menuName;

    private String programUuid;

    private String programId;

    private String programName;

    private String parameter;

    private String useYn;
}