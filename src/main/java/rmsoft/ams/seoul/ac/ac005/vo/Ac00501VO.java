/*
 * Copyright (c) 2017. RMSoft Co.,Ltd. All rights reserved
 */

package rmsoft.ams.seoul.ac.ac005.vo;

import io.onsemiro.core.vo.BaseVO;
import io.onsemiro.utils.ModelMapperUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import rmsoft.ams.seoul.common.domain.AcRole;

import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Ac00501VO
 *
 * @author james
 * @version 1.0.0
 * @since 2017 -10-23 오후 2:06
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Ac00501VO extends BaseVO {
    private String roleUuid;

    private String roleName;

    private String useYn;

    /**
     * Of ac 00501 vo.
     *
     * @param acRole the ac role
     * @return the ac 00501 vo
     */
    public static Ac00501VO of(AcRole acRole) {
        return ModelMapperUtils.map(acRole, Ac00501VO.class);
    }

    /**
     * Of list.
     *
     * @param acRoleList the ac role list
     * @return the list
     */
    public static List<Ac00501VO> of(List<AcRole> acRoleList) {
        return acRoleList.stream().map(acRole -> of(acRole)).collect(toList());
    }

    /**
     * Of list.
     *
     * @param acRolePage the ac role page
     * @return the list
     */
    public static List<Ac00501VO> of(Page<AcRole> acRolePage) {
        return acRolePage.getContent().stream().map(acRole -> of(acRole)).collect(toList());
    }

}