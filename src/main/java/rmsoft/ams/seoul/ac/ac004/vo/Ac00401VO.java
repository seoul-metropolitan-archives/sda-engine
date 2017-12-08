/*
 * Copyright (c) 2017. RMSoft Co.,Ltd. All rights reserved
 */

package rmsoft.ams.seoul.ac.ac004.vo;

import io.onsemiro.core.vo.BaseVO;
import io.onsemiro.utils.ModelMapperUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import rmsoft.ams.seoul.common.domain.AcUserGroup;

import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Ac00401VO
 *
 * @author james
 * @version 1.0.0
 * @since 2017 -10-23 오후 2:06
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Ac00401VO extends BaseVO {
    private String userGroupUuid;

    private String userGroupName;

    private String useYn;

    /**
     * Of ac 00401 vo.
     *
     * @param acUserGroup the ac user group
     * @return the ac 00401 vo
     */
    public static Ac00401VO of(AcUserGroup acUserGroup) {
        return ModelMapperUtils.map(acUserGroup, Ac00401VO.class);
    }

    /**
     * Of list.
     *
     * @param acUserGroupList the ac user group list
     * @return the list
     */
    public static List<Ac00401VO> of(List<AcUserGroup> acUserGroupList) {
        return acUserGroupList.stream().map(acUserGroup -> of(acUserGroup)).collect(toList());
    }

    /**
     * Of list.
     *
     * @param acUserGroupPage the ac user group page
     * @return the list
     */
    public static List<Ac00401VO> of(Page<AcUserGroup> acUserGroupPage) {
        return acUserGroupPage.getContent().stream().map(acUserGroup -> of(acUserGroup)).collect(toList());
    }

}