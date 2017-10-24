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

@Setter
@Getter
@DynamicInsert
@DynamicUpdate
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "AC_ROLE_PERMISSION")
@IdClass(AcRolePermission.AcRolePermissionId.class)
@Alias("AcRolePermission")
public class AcRolePermission extends BaseJpaModel<AcRolePermission.AcRolePermissionId> {

    @Id
    @Column(name = "ROLE_PERMISSION_UUID", length = 36, nullable = false)
    @Comment(value = "Role Permission UUID")
    private String rolePermissionUuid;

    @Column(name = "ROLE_UUID", length = 36, nullable = false)
    @Comment(value = "Role Uuid")
    private String roleUuid;

    @Column(name = "PERMISSION_UUID", length = 36, nullable = false)
    @Comment(value = "Permission UUID")
    private String permissionUuid;

    @Column(name = "USE_YN", length = 1, nullable = false)
    @Comment(value = "사용여부")
    private String useYn;

    @Override
    public AcRolePermissionId getId() {
        return AcRolePermissionId.of(rolePermissionUuid);
    }

    @Embeddable
    @Data
    @NoArgsConstructor
    @RequiredArgsConstructor(staticName = "of")
    public static class AcRolePermissionId implements Serializable {

        @NonNull
        private String rolePermissionUuid;
    }
}