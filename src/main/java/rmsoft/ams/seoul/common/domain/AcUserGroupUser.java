/*
 * Copyright (c) 2017. RMSoft Co.,Ltd. All rights reserved
 */

package rmsoft.ams.seoul.common.domain;

import io.onsemiro.core.annotations.Comment;
import io.onsemiro.core.domain.BaseJpaModel;
import lombok.*;
import org.apache.ibatis.type.Alias;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

/**
 * The type Ac user group user.
 */
@Setter
@Getter
@DynamicInsert
@DynamicUpdate
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "AC_USER_GROUP_USER")
@IdClass(AcUserGroupUser.AcUserGroupUserId.class)
@Alias("AcUserGroupUser")
public class AcUserGroupUser extends BaseJpaModel<AcUserGroupUser.AcUserGroupUserId> {

    @Id
    @Column(name = "USER_GROUP_USER_UUID", length = 36, nullable = false)
    @Comment(value = "유저그룹유저UUID")
    private String userGroupUserUuid;

    @Column(name = "USER_GROUP_UUID", length = 36, nullable = false)
    @Comment(value = "유저그룹UUID")
    private String userGroupUuid;

    @Column(name = "USER_UUID", length = 36, nullable = false)
    @Comment(value = "그룹UUID")
    private String userUuid;

    @Column(name = "USE_YN", length = 1, nullable = false)
    @Comment(value = "사용여부")
    private String useYn;

    @Override
    public AcUserGroupUserId getId() {
        return AcUserGroupUserId.of(userGroupUserUuid);
    }

    /**
     * The type Ac user group user id.
     */
    @Embeddable
    @Data
    @NoArgsConstructor
    @RequiredArgsConstructor(staticName = "of")
    public static class AcUserGroupUserId implements Serializable {

        @NonNull
        private String userGroupUserUuid;
    }
}