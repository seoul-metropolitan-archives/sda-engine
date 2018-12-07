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
 * The type AGGREGATION_CREATOR_UUID.
 */
@Setter
@Getter
@DynamicInsert
@DynamicUpdate
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "RC_AGGREGATION_CREATOR")
@IdClass(RcAggregationCreator.RcAggregationCreatorId.class)
@Alias("RcAggregationCreator")
public class RcAggregationCreator extends BaseJpaModel<RcAggregationCreator.RcAggregationCreatorId> {


    @Id
    @Column(name = "AGGREGATION_CREATOR_UUID", length = 36, nullable = false)
    @Comment(value = "AGGREGATION_CREATOR_UUID")
    private String aggregationCreatorUuid;

    @Column(name = "AGGREGATION_UUID", length = 36, nullable = false)
    @Comment(value = "AGGREGATION UUID")
    private String aggregationUuid;

    @Column(name = "CREATOR_UUID", length = 36, nullable = false)
    @Comment(value = "")
    private String creatorUuid;


    @Override
    public RcAggregationCreator.RcAggregationCreatorId getId() { return RcAggregationCreator.RcAggregationCreatorId.of(aggregationCreatorUuid); }

    /**
     * The type Rc aggregation id.
     */
    @Embeddable
    @Data
    @NoArgsConstructor
    @RequiredArgsConstructor(staticName = "of")
    public static class RcAggregationCreatorId implements Serializable {
        @NonNull
        private String aggregationCreatorUuid;
    }
}
