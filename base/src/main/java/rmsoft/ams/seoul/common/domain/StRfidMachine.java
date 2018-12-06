package rmsoft.ams.seoul.common.domain;

import io.onsemiro.core.domain.SimpleJpaModel;
import lombok.*;
import org.apache.ibatis.type.Alias;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Setter
@Getter
@DynamicInsert
@DynamicUpdate
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "ST_RFID_MACHINE")
@IdClass(StRfidMachine.StRfidMachineId.class)
@Alias("StRfidMachine")
public class StRfidMachine extends SimpleJpaModel<StRfidMachine.StRfidMachineId> {


    @Id
    @Column(name = "RFID_MACHINE_UUID", length = 36, nullable = false)
    private String rfidMachineUuid;

    @Column(name = "NO", length = 30, nullable = false)
    private int no;

    @Column(name = "MACHINE_TYPE_UUID", length = 36, nullable = false)
    private String machineTypeUuid;

    @Column(name = "MACHINE_ID", length = 36, nullable = false)
    private String machineId;


    @Column(name = "MACHINE_NAME", length = 50, nullable = false)
    private String machineName;

    @Column(name = "MAC_ADDR", length = 50, nullable = false)
    private String macAddr;

    @Column(name = "IP", length = 50, nullable = false)
    private String ip;

    @Column(name = "SUBNETMASK", length = 50, nullable = false)
    private String subnetmask;

    @Column(name = "GATEWAY", length = 50, nullable = false)
    private String gateway;

    @Override
    public StRfidMachineId getId() {
        return StRfidMachineId.of(rfidMachineUuid);
    }

    @Embeddable
    @Data
    @NoArgsConstructor
    @RequiredArgsConstructor(staticName = "of")
    public static class StRfidMachineId implements Serializable {
        @NonNull
        private String rfidMachineUuid;
    }
}
