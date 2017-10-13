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
@Table(name = "AC_ROLE_MENU")
@IdClass(AcRoleMenu.AcRoleMenuId.class)
@Alias("AcRoleMenu")
public class AcRoleMenu extends BaseJpaModel<AcRoleMenu.AcRoleMenuId> {

    @Id
    @Column(name = "ROLE_MENU_UUID", length = 36, nullable = false)
    @Comment(value = "RoleMenu UUID")
    private String roleMenuUuid;

    @Column(name = "ROLE_UUID", length = 36, nullable = false)
    @Comment(value = "Role UUID")
    private String roleUuid;

    @Column(name = "MENU_UUID", length = 36, nullable = false)
    @Comment(value = "Menu UUID")
    private String menuUuid;

    @Column(name = "USE_YN", length = 1, nullable = false)
    @Comment(value = "사용여부")
    @Type(type = "labelEnum")
    private AXBootTypes.Used useYn = AXBootTypes.Used.YES;

    @Override
    public AcRoleMenuId getId() {
        return AcRoleMenuId.of(roleMenuUuid);
    }

    @Embeddable
    @Data
    @NoArgsConstructor
    @RequiredArgsConstructor(staticName = "of")
    public static class AcRoleMenuId implements Serializable {

        @NonNull
        private String roleMenuUuid;
    }
}