package io.onsemiro.core.domain.program.menu;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import io.onsemiro.core.annotations.Comment;
import io.onsemiro.core.code.AXBootTypes;
import io.onsemiro.core.domain.BaseJpaModel;
import io.onsemiro.core.domain.program.Program;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@DynamicInsert
@DynamicUpdate
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "AD_MENU")
@Comment(value = "메뉴")
@Alias("AdMenu")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "menuUuid")
/**
 * 메뉴 엔티티
 * @author james
 * @version 1.0.0
 * @since 2017-09-12 오전 10:53
 **/
public class Menu extends BaseJpaModel<String> {

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
    @Type(type = "labelEnum")
    private AXBootTypes.Used useYn = AXBootTypes.Used.YES;

    // 사용자그룹정보
    @Transient
    private Program program;

    @Transient
    @JsonProperty
    private String menuId;

    @Transient
    private String menuNm;

    @Transient
    private String progCd;

    @Transient
    public String name;

    @Transient
    public String saveYn;

    @Transient
    public String inquiryYn;

    @Transient
    private List<Menu> children = new ArrayList<>();

    public void addChildren(Menu menu) {
        children.add(menu);
    }

    public Menu clone() {
        try {
            Menu menu = (Menu) super.clone();
            menu.setChildren(new ArrayList<>());
            return menu;
        } catch (Exception e) {
            // ignore
        }
        return null;
    }

    @Override
    public String getId() {
        return menuUuid;
    }
}
