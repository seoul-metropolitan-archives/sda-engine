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
 * The type Ac access control.
 */
@Setter
@Getter
@DynamicInsert
@DynamicUpdate
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "AC_ACCESS_CONTROL")
@IdClass(AcAccessControl.AcAccessControlId.class)
@Alias("AcAccessControl")
public class AcAccessControl extends BaseJpaModel<AcAccessControl.AcAccessControlId> {

    @Id
    @Column(name = "ACCESS_CONTROL_UUID", length = 36, nullable = false)
    @Comment(value = "Access Control UUID")
    private String accessControlUuid;

    @Column(name = "USER_UUID", length = 36)
    @Comment(value = "User UUID")
    private String userUuid;

    @Column(name = "USER_GROUP_UUID", length = 36)
    @Comment(value = "User Group UUID")
    private String userGroupUuid;

    @Column(name = "ROLE_UUID", length = 36, nullable = false)
    @Comment(value = "Role UUID")
    private String roleUuid;

    @Column(name = "USE_YN", length = 1, nullable = false)
    @Comment(value = "사용여부")
    private String useYn;

    @Override
    public AcAccessControlId getId() {
        return AcAccessControlId.of(accessControlUuid);
    }

    /**
     * The type Ac access control id.
     */
    @Embeddable
    @Data
    @NoArgsConstructor
    @RequiredArgsConstructor(staticName = "of")
    public static class AcAccessControlId implements Serializable {

        @NonNull
        private String accessControlUuid;
    }
}