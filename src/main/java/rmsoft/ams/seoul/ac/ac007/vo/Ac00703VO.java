/*
 * Copyright (c) 2017. RMSoft Co.,Ltd. All rights reserved
 */

package rmsoft.ams.seoul.ac.ac007.vo;

import io.onsemiro.core.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Ac00703VO extends BaseVO {
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