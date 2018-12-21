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
@Table(name = "ST_GATE")
@IdClass(StGate.StGateId.class)
@Alias("StGate")
public class StGate extends SimpleJpaModel<StGate.StGateId> {

    @Id
    @Column(name = "GATE_UUID", length = 36, nullable = false)
    private String gateUuid;

    @Column(name = "NO", length = 8, nullable = false)
    private int no;


    @Column(name = "GATE_ID", length = 36, nullable = false)
    private String gateId;

    @Column(name = "GATE_NAME", length = 50, nullable = false)
    private String gateName;

    @Column(name = "MODE_UUID", length = 36, nullable = false)
    private String modeUuid;

    @Column(name = "SENSOR_USE_YN", length = 1, nullable = false)
    private String sensorUseYn;

    @Column(name = "IN_ZONE_UUID", length = 36, nullable = false)
    private String inZoneUuid;

    @Column(name = "OUT_ZONE_UUID", length = 36, nullable = false)
    private String outZoneUuid;

    @Column(name = "LIGHT_BAR_STATUS_UUID", length = 36, nullable = false)
    private String lightBarStatusUuid;

    @Override
    public StGateId getId() { return StGateId.of(gateUuid); }


    @Embeddable
    @Data
    @NoArgsConstructor
    @RequiredArgsConstructor(staticName = "of")
    public static class StGateId implements Serializable {
        @NonNull
        private String gateUuid;

    }

}
