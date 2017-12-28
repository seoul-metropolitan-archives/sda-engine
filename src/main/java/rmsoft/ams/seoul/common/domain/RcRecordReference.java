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
 * The type Rc record reference.
 */
@Setter
@Getter
@DynamicInsert
@DynamicUpdate
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "RC_RECORD_REFERENCE")
@IdClass(RcRecordReference.RcRecordReferenceId.class)
@Alias("RcRecordReference")
public class RcRecordReference extends BaseJpaModel<RcRecordReference.RcRecordReferenceId> {


    @Id
    @Column(name = "RECORD_REFERENCE_UUID", length = 36, nullable = false)
    @Comment(value = "RECORD_REFERENCE_UUID")
    private String recordReferenceUuid;
    @Column(name = "VIRTUAL_AGGREGATION_UUID", length = 36, nullable = false)
    @Comment(value = "VIRTUAL_AGGREGATION_UUID")
    private String virtualAggregationUuid;
    @Column(name = "AGGREGATION_UUID", length = 36, nullable = true)
    @Comment(value = "AGGREGATION UUID")
    private String aggregationUuid;
    @Column(name = "ITEM_UUID", length = 36, nullable = true)
    @Comment(value = "ITEM_UUID")
    private String itemUuid;




    @Override
    public RcRecordReference.RcRecordReferenceId getId() { return RcRecordReference.RcRecordReferenceId.of(recordReferenceUuid); }

    /**
     * The type Rc record reference id.
     */
    @Embeddable
    @Data
    @NoArgsConstructor
    @RequiredArgsConstructor(staticName = "of")
    public static class RcRecordReferenceId implements Serializable {
        @NonNull
        private String recordReferenceUuid;
    }
}
