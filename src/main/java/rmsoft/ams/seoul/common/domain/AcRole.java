/*
 * Copyright (c) 2017. RMSoft Co.,Ltd. All rights reserved
 */

package rmsoft.ams.seoul.common.domain;

import io.onsemiro.core.annotations.Comment;
import io.onsemiro.core.code.AXBootTypes;
import io.onsemiro.core.domain.BaseJpaModel;
import lombok.*;
import org.apache.ibatis.type.Alias;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;

@Setter
@Getter
@DynamicInsert
@DynamicUpdate
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "AC_ROLE")
@IdClass(AcRole.AcRoleId.class)
@Alias("AcRole")
public class AcRole extends BaseJpaModel<AcRole.AcRoleId> {

    @Id
    @Column(name = "ROLE_UUID", length = 36, nullable = false)
    @Comment(value = "Role UUID")
    private String roleUuid;

    @Column(name = "ROLE_NAME", length = 50, nullable = false)
    @Comment(value = "Role Name")
    private String permissionName;

    @Column(name = "USE_YN", length = 1, nullable = false)
    @Comment(value = "사용여부")
    @Type(type = "labelEnum")
    private AXBootTypes.Used useYn = AXBootTypes.Used.YES;

    @Override
    public AcRoleId getId() {
        return AcRoleId.of(roleUuid);
    }

    @Embeddable
    @Data
    @NoArgsConstructor
    @RequiredArgsConstructor(staticName = "of")
    public static class AcRoleId implements Serializable {

        @NonNull
        private String roleUuid;
    }
}