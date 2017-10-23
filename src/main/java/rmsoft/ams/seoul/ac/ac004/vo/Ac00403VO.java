/*
 * Copyright (c) 2017. RMSoft Co.,Ltd. All rights reserved
 */

package rmsoft.ams.seoul.ac.ac004.vo;

import io.onsemiro.core.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Ac00403VO
 *
 * @author james
 * @version 1.0.0
 * @since 2017-10-23 오후 2:06
 **/
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Ac00403VO extends BaseVO {
    private String accessControlUuid;

    private String userUuid;

    private String userGroupUuid;

    private String roleUuid;

    private String roleName;

    private String useYn;
}