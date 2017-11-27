/*
 * Copyright (c) 2017. RMSoft Co.,Ltd. All rights reserved
 */

package rmsoft.ams.seoul.ac.ac003.vo;

import io.onsemiro.core.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Ac00304VO extends BaseVO {
    private String accessControlUuid;

    private String userHUuid;

    private String userGroupUuid;

    private String roleUuid;

    private String roleName;

    private String useYn;
}