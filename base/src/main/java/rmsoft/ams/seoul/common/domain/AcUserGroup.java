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
 * The type Ac user group.
 */
@Setter
@Getter
@DynamicInsert
@DynamicUpdate
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "AC_USER_GROUP")
@IdClass(AcUserGroup.AcUserGroupId.class)
@Alias("AcUserGroup")
public class AcUserGroup extends BaseJpaModel<AcUserGroup.AcUserGroupId> {

    @Id
    @Column(name = "USER_GROUP_UUID", length = 36, nullable = false)
    @Comment(value = "사용자그룹UUID")
    private String userGroupUuid;

    @Column(name = "USER_GROUP_NAME", length = 50, nullable = false)
    @Comment(value = "유저그룹명")
    private String userGroupName;

    @Column(name = "USE_YN", length = 1, nullable = false)
    @Comment(value = "사용여부")
    private String useYn;

    @Override
    public AcUserGroupId getId() {
        return AcUserGroupId.of(userGroupUuid);
    }

    /**
     * The type Ac user group id.
     */
    @Embeddable
    @Data
    @NoArgsConstructor
    @RequiredArgsConstructor(staticName = "of")
    public static class AcUserGroupId implements Serializable {

        @NonNull
        private String userGroupUuid;
    }
}