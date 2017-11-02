/*
 * Copyright (c) 2017. RMSoft Co.,Ltd. All rights reserved
 */

package rmsoft.ams.seoul.sp.sp001.vo;

import io.onsemiro.core.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Ac00502VO
 *
 * @author james
 * @version 1.0.0
 * @since 2017-10-23 오후 2:06
 **/
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Sp00102VO extends BaseVO {
    private String rolePermissionUuid;

    private String roleUuid;

    private String permissionUuid;

    private String permissionName;

    private String pmsProgramUuid;

    private String programName;

    private String pmsEntityTypeUuid;

    private String entityTypeName;

    private String pmsFunctionUuid;

    private String useYn;
}