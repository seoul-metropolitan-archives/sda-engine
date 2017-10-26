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
@Table(name = "AD_MENU")
@IdClass(AdMenu.AdMenuId.class)
@Alias("AdMenu")
public class AdMenu extends BaseJpaModel<AdMenu.AdMenuId> {

    @Id
    @Column(name = "MENU_UUID", length = 36, nullable = false)
    @Comment(value = "메뉴UUID")
    private String menuUuid;

    @Column(name = "PARENT_MENU_CODE", length = 20)
    @Comment(value = "상위메뉴코드")
    private String parentMenuCode;

    @Column(name = "MENU_CODE", length = 20, nullable = false)
    @Comment(value = "메뉴코드")
    private String menuCode;

    @Column(name = "MENU_NAME", length = 50, nullable = false)
    @Comment(value = "메뉴명")
    private String menuName;

    @Column(name = "PROGRAM_UUID", length = 36)
    @Comment(value = "프로그램UUID")
    private String programUuid;

    @Column(name = "PARAMETER", length = 50)
    @Comment(value = "메뉴파라미터")
    private String parameter;

    @Column(name = "USE_YN", length = 1, nullable = false)
    @Comment(value = "사용여부")
    private String useYn;

    @Override
    public AdMenuId getId() {
        return AdMenuId.of(menuUuid);
    }

    @Embeddable
    @Data
    @NoArgsConstructor
    @RequiredArgsConstructor(staticName = "of")
    public static class AdMenuId implements Serializable {

        @NonNull
        private String menuUuid;
    }
}