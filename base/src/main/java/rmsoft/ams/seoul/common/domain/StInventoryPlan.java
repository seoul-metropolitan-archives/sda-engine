package rmsoft.ams.seoul.common.domain;

import io.onsemiro.core.domain.SimpleJpaModel;
import lombok.*;
import org.apache.ibatis.type.Alias;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

@Setter
@Getter
@DynamicInsert
@DynamicUpdate
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "ST_INVENTORY_PLAN")
@IdClass(StInventoryPlan.StInventoryPlanId.class)
@Alias("stInventoryPlan")
public class StInventoryPlan extends SimpleJpaModel<StInventoryPlan.StInventoryPlanId> {

    @Id
    @Column(name = "INVENTORY_PLAN_UUID", length = 36, nullable = false)
    private String inventoryPlanUuid;

    @Column(name = "PLAN_NAME", length = 500, nullable = false)
    private String planName;

    @Column(name = "PLANNER_UUID", length = 36, nullable = false)
    private String plannerUuid;

    @Column(name = "EXCEPT_START_DATE", nullable = false)
    private Timestamp exceptStartDate;

    @Column(name = "EXCEPT_END_DATE", nullable = false)
    private Timestamp exceptEndDate;

    @Column(name = "REPOSITORY_UUID", length = 36)
    private String repositoryUuid;

    @Column(name = "SHELF_UUID", length = 36)
    private String shelfUuid;

    @Column(name = "LOCATION_UUID", length = 36)
    private String locationUuid;

    @Column(name = "STATUS_UUID", length = 36, nullable = false)
    private String statusUuid;

    @Column(name = "PLAN_RESULT_UUID", length = 36, nullable = false)
    private String planResultUuid;

    @Column(name = "NOTES", length = 4000)
    private String notes;

    @Column(name = "EXCEPT_REASON")
    private String exceptReason;


    @Override
    public StInventoryPlanId getId() { return StInventoryPlanId.of(inventoryPlanUuid);}

    @Embeddable
    @Data
    @NoArgsConstructor
    @RequiredArgsConstructor(staticName = "of")
    public static class StInventoryPlanId implements Serializable{
        @NonNull
        private String inventoryPlanUuid;
    }
}
