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
 * The type RC_ITEM_MATERIAL.
 */
@Setter
@Getter
@DynamicInsert
@DynamicUpdate
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "RC_ITEM_MATERIAL")
@IdClass(RcItemMaterial.RcItemMaterialId.class)
@Alias("RcItemMaterial")
public class RcItemMaterial extends BaseJpaModel<RcItemMaterial.RcItemMaterialId> {


    @Id
    @Column(name = "ITEM_MATERIAL_UUID", length = 36, nullable = false)
    @Comment(value = "ITEM_MATERIAL_UUID")
    private String itemMaterialUuid;

    @Column(name = "ITEM_UUID", length = 36, nullable = false)
    @Comment(value = "ITEM_UUID UUID")
    private String itemUuid;

    @Column(name = "MATERIAL_TYPE_UUID", length = 36, nullable = false)
    @Comment(value = "")
    private String materialTypeUuid;


    @Override
    public RcItemMaterial.RcItemMaterialId getId() { return RcItemMaterial.RcItemMaterialId.of(itemMaterialUuid); }

    /**
     * The type Rc aggregation id.
     */
    @Embeddable
    @Data
    @NoArgsConstructor
    @RequiredArgsConstructor(staticName = "of")
    public static class RcItemMaterialId implements Serializable {
        @NonNull
        private String itemMaterialUuid;
    }
}
