/*
 * Copyright (c) 2017. RMSoft Co.,Ltd. All rights reserved
 */

package rmsoft.ams.seoul.ac.ac003.vo;

import io.onsemiro.core.vo.BaseVO;
import io.onsemiro.utils.ModelMapperUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import rmsoft.ams.seoul.common.domain.AcUserGroupUser;

import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * The type Ac 00302 vo.
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Ac00302VO extends BaseVO {
    private String userGroupUserUuid;

    private String userGroupUuid;

    private String userGroupName;

    private String userUuid;

    private String useYn;

    /**
     * Of ac 00302 vo.
     *
     * @param acUserGroupUser the ac user group user
     * @return the ac 00302 vo
     */
    public static Ac00302VO of(AcUserGroupUser acUserGroupUser) {
        // Custom Mapper
        /*BoundMapperFacade<AcUser, Ac00301VO> mapper =
                ModelMapperUtils.getMapper(AcUser.class.getPackage().getName(), Ac00301VO.class.getPackage().getName());
        return mapper.map(acUser);*/
        return ModelMapperUtils.map(acUserGroupUser, Ac00302VO.class);
    }

    /**
     * Of list.
     *
     * @param acUserGroupUserList the ac user group user list
     * @return the list
     */
    public static List<Ac00302VO> of(List<AcUserGroupUser> acUserGroupUserList) {
        return acUserGroupUserList.stream().map(acUserGroupUser -> of(acUserGroupUser)).collect(toList());
    }

    /**
     * Of list.
     *
     * @param acUserGroupUserPage the ac user group user page
     * @return the list
     */
    public static List<Ac00302VO> of(Page<AcUserGroupUser> acUserGroupUserPage) {
        return acUserGroupUserPage.getContent().stream().map(acUserGroupUser -> of(acUserGroupUser)).collect(toList());
    }

}