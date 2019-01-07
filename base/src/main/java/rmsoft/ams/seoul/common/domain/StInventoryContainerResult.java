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
@Table(name = "ST_INVENTORY_CONTAINER_RESULT")
@IdClass(StInventoryContainerResult.StInventoryContainerResultId.class)
@Alias("stInventoryContainerResult")
public class StInventoryContainerResult extends SimpleJpaModel<StInventoryContainerResult.StInventoryContainerResultId> {

    @Id
    @Column(name = "INVENTORY_CONT_RESULT_UUID", length = 36, nullable = false)
    private String inventoryContResultUuid;

    @Column(name = "INVENTORY_PLAN_UUID", length = 36, nullable = false)
    private String inventoryPlanUuid;

    @Column(name = "CONTAINER_UUID", length = 36, nullable = false)
    private String containerUuid;

    @Column(name = "INVENTORY_RESULT_UUID", length = 36, nullable = false)
    private String inventoryResultUuid;

    @Column(name = "CONTAINER_STATUS_UUID", length = 36, nullable = false)
    private String containerStatusUuid;

    @Override
    public StInventoryContainerResultId getId() { return StInventoryContainerResultId.of(inventoryContResultUuid);}

    @Embeddable
    @Data
    @NoArgsConstructor
    @RequiredArgsConstructor(staticName = "of")
    public static class StInventoryContainerResultId implements Serializable {
        @NonNull
        private String inventoryContResultUuid;

    }

}
