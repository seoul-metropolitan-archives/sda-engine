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
import rmsoft.ams.seoul.common.domain.AcUserGroupUser;

import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Ac00402VO
 *
 * @author james
 * @version 1.0.0
 * @since 2017 -10-23 오후 2:06
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Ac00402VO extends BaseVO {
    private String userGroupUserUuid;

    private String userGroupUuid;

    private String userUuid;

    private String userNm;

    private String useYn;

    /**
     * Of ac 00402 vo.
     *
     * @param acUserGroupUser the ac user group user
     * @return the ac 00402 vo
     */
    public static Ac00402VO of(AcUserGroupUser acUserGroupUser) {
        // Custom Mapper
        /*BoundMapperFacade<AcUser, Ac00401VO> mapper =
                ModelMapperUtils.getMapper(AcUser.class.getPackage().getName(), Ac00401VO.class.getPackage().getName());
        return mapper.map(acUser);*/
        return ModelMapperUtils.map(acUserGroupUser, Ac00402VO.class);
    }

    /**
     * Of list.
     *
     * @param acUserGroupUserList the ac user group user list
     * @return the list
     */
    public static List<Ac00402VO> of(List<AcUserGroupUser> acUserGroupUserList) {
        return acUserGroupUserList.stream().map(acUserGroupUser -> of(acUserGroupUser)).collect(toList());
    }

    /**
     * Of list.
     *
     * @param acUserGroupUserPage the ac user group user page
     * @return the list
     */
    public static List<Ac00402VO> of(Page<AcUserGroupUser> acUserGroupUserPage) {
        return acUserGroupUserPage.getContent().stream().map(acUserGroupUser -> of(acUserGroupUser)).collect(toList());
    }

}