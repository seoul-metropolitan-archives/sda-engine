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
@Table(name = "AC_PERMISSION")
@IdClass(AcPermission.AcPermissionId.class)
@Alias("AcPermission")
public class AcPermission extends BaseJpaModel<AcPermission.AcPermissionId> {

    @Id
    @Column(name = "PERMISSION_UUID", length = 36, nullable = false)
    @Comment(value = "Permission UUID")
    private String permissionUuid;

    @Column(name = "PERMISSION_NAME", length = 50, nullable = false)
    @Comment(value = "Permission Name")
    private String permissionName;

    @Column(name = "PMS_PROGRAM_UUID", length = 36, nullable = false)
    @Comment(value = "User Group UUID")
    private String pmsProgramUuid;

    @Column(name = "PMS_ENTITY_TYPE_UUID", length = 36)
    @Comment(value = "Role UUID")
    private String pmsEntityTypeUuid;

    @Column(name = "PMS_FUNCTION_UUID", length = 36, nullable = false)
    @Comment(value = "Role UUID")
    private String pmsFunctionUuid;

    @Column(name = "USE_YN", length = 1, nullable = false)
    @Comment(value = "사용여부")
    private String useYn;

    @Override
    public AcPermissionId getId() {
        return AcPermissionId.of(permissionUuid);
    }

    @Embeddable
    @Data
    @NoArgsConstructor
    @RequiredArgsConstructor(staticName = "of")
    public static class AcPermissionId implements Serializable {

        @NonNull
        private String permissionUuid;
    }
}