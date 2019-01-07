package rmsoft.ams.seoul.common.domain;

import io.onsemiro.core.domain.SimpleJpaModel;
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
@Table(name = "ST_INVENTORY_RECORD_RESULT")
@IdClass(StInventoryRecordResult.StInventoryRecordResultId.class)
@Alias("stInventoryRecordResult")
public class StInventoryRecordResult extends SimpleJpaModel<StInventoryRecordResult.StInventoryRecordResultId> {

    @Id
    @Column(name = "INVENTORY_RECORD_RESULT_UUID", length = 36, nullable = false)
    private String inventoryRecordResultUuid;

    @Column(name = "INVENTORY_PLAN_UUID", length = 36, nullable = false)
    private String inventoryPlanUuid;

    @Column(name = "CONTAINER_UUID", length = 36, nullable = false)
    private String containerUuid;

    @Column(name = "AGGREGATION_UUID", length = 36, nullable = false)
    private String aggregationUuid;

    @Column(name = "WORKER_UUID", length = 36, nullable = false)
    private String workerUuid;

    @Column(name = "INVENTORY_DATE", length = 36, nullable = false)
    private String inventoryDate;

    @Column(name = "INVENTORY_RESULT_UUID", length = 36, nullable = false)
    private String inventoryResultUuid;

    @Column(name = "TAG_STATUS_UUID", length = 36, nullable = false)
    private String tagStatusUuid;

    @Column(name = "RECORD_STATUS_UUID", length = 36, nullable = false)
    private String recordStatusUuid;


    @Override
    public StInventoryRecordResultId getId(){ return StInventoryRecordResultId.of(inventoryRecordResultUuid);}

    @Embeddable
    @Data
    @NoArgsConstructor
    @RequiredArgsConstructor(staticName = "of")
    public static class StInventoryRecordResultId implements Serializable {
        @NonNull
        private String inventoryRecordResultUuid;
    }
}
