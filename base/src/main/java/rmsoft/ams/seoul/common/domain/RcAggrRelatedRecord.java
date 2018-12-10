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
 * The type RC_AGGR_RELATED_RECORD.
 */
@Setter
@Getter
@DynamicInsert
@DynamicUpdate
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "RC_AGGR_RELATED_RECORD")
@IdClass(RcAggrRelatedRecord.RcAggrRelatedRecordId.class)
@Alias("RcAggrRelatedRecord")
public class RcAggrRelatedRecord extends BaseJpaModel<RcAggrRelatedRecord.RcAggrRelatedRecordId> {


    @Id
    @Column(name = "AGGR_RELATED_RECORD_UUID", length = 36, nullable = false)
    @Comment(value = "AGGR_RELATED_RECORD_UUID")
    private String aggrRelatedRecordUuid;

    @Column(name = "AGGREGATION_UUID", length = 36, nullable = false)
    @Comment(value = "AGGREGATION UUID")
    private String aggregationUuid;

    @Column(name = "RELATED_AGGREGATION_UUID", length = 36, nullable = false)
    @Comment(value = "")
    private String relatedAggregationUuid;

    @Column(name = "RELATED_ITEM_UUID", length = 36, nullable = false)
    @Comment(value = "")
    private String relatedItemUuid;


    @Override
    public RcAggrRelatedRecord.RcAggrRelatedRecordId getId() { return RcAggrRelatedRecord.RcAggrRelatedRecordId.of(aggrRelatedRecordUuid); }

    /**
     * The type Rc aggregation id.
     */
    @Embeddable
    @Data
    @NoArgsConstructor
    @RequiredArgsConstructor(staticName = "of")
    public static class RcAggrRelatedRecordId implements Serializable {
        @NonNull
        private String aggrRelatedRecordUuid;
    }
}
