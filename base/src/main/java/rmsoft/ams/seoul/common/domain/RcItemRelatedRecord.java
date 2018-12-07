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
 * The type RC_ITEM_RELATED_RECORD.
 */
@Setter
@Getter
@DynamicInsert
@DynamicUpdate
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "RC_ITEM_RELATED_RECORD")
@IdClass(RcItemRelatedRecord.RcItemRelatedRecordId.class)
@Alias("RcItemRelatedRecord")
public class RcItemRelatedRecord extends BaseJpaModel<RcItemRelatedRecord.RcItemRelatedRecordId> {

    @Id
    @Column(name = "ITEM_RELATED_RECORD_UUID", length = 36, nullable = false)
    @Comment(value = "ITEM_RELATED_RECORD_UUID")
    private String itemRelatedRecordUuid;

    @Column(name = "ITEM_UUID", length = 36, nullable = false)
    @Comment(value = "ITEM_UUID")
    private String itemUuid;

    @Column(name = "RELATED_AGGREGATION_UUID", length = 36, nullable = false)
    @Comment(value = "")
    private String relatedAggregationUuid;

    @Column(name = "RELATED_ITEM_UUID", length = 36, nullable = false)
    @Comment(value = "")
    private String relatedItemUuid;


    @Override
    public RcItemRelatedRecord.RcItemRelatedRecordId getId() { return RcItemRelatedRecord.RcItemRelatedRecordId.of(itemRelatedRecordUuid); }

    /**
     * The type Rc aggregation id.
     */
    @Embeddable
    @Data
    @NoArgsConstructor
    @RequiredArgsConstructor(staticName = "of")
    public static class RcItemRelatedRecordId implements Serializable {
        @NonNull
        private String itemRelatedRecordUuid;
    }
}
