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

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Ac00302VO extends BaseVO {
    private String userGroupUserUuid;

    private String userGroupUuid;

    private String userGroupName;

    private String useYn;

    public static Ac00302VO of(AcUserGroupUser acUserGroupUser) {
        // Custom Mapper
        /*BoundMapperFacade<AcUser, Ac00301VO> mapper =
                ModelMapperUtils.getMapper(AcUser.class.getPackage().getName(), Ac00301VO.class.getPackage().getName());
        return mapper.map(acUser);*/
        return ModelMapperUtils.map(acUserGroupUser, Ac00302VO.class);
    }

    public static List<Ac00302VO> of(List<AcUserGroupUser> acUserGroupUserList) {
        return acUserGroupUserList.stream().map(acUserGroupUser -> of(acUserGroupUser)).collect(toList());
    }

    public static List<Ac00302VO> of(Page<AcUserGroupUser> acUserGroupUserPage) {
        return acUserGroupUserPage.getContent().stream().map(acUserGroupUser -> of(acUserGroupUser)).collect(toList());
    }

}