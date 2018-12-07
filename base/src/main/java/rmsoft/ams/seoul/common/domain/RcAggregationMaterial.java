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
 * The type RC_AGGREGATION_MATERIAL.
 */
@Setter
@Getter
@DynamicInsert
@DynamicUpdate
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "RC_AGGREGATION_MATERIAL")
@IdClass(RcAggregationMaterial.RcAggregationMaterialId.class)
@Alias("RcAggregationMaterial")
public class RcAggregationMaterial extends BaseJpaModel<RcAggregationMaterial.RcAggregationMaterialId> {


    @Id
    @Column(name = "AGGREGATION_MATERIAL_UUID", length = 36, nullable = false)
    @Comment(value = "AGGREGATION_MATERIAL_UUID")
    private String aggregationMaterialUuid;

    @Column(name = "AGGREGATION_UUID", length = 36, nullable = false)
    @Comment(value = "AGGREGATION UUID")
    private String aggregationUuid;

    @Column(name = "MATERIAL_TYPE_UUID", length = 36, nullable = false)
    @Comment(value = "")
    private String materialTypeUuid;


    @Override
    public RcAggregationMaterial.RcAggregationMaterialId getId() { return RcAggregationMaterial.RcAggregationMaterialId.of(aggregationMaterialUuid); }

    /**
     * The type Rc aggregation id.
     */
    @Embeddable
    @Data
    @NoArgsConstructor
    @RequiredArgsConstructor(staticName = "of")
    public static class RcAggregationMaterialId implements Serializable {
        @NonNull
        private String aggregationMaterialUuid;
    }
}
