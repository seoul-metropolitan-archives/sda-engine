package rmsoft.ams.seoul.common.domain;


import io.onsemiro.core.domain.BaseJpaModel;
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
@Table(name = "ST_READER_MACHINE")
@IdClass(StReaderMachine.StReaderMachineId.class)
@Alias("StReaderMachine")
public class StReaderMachine extends SimpleJpaModel<StReaderMachine.StReaderMachineId> {

    @Id
    @Column(name = "READER_MACHINE_UUID", length = 36, nullable = false)
    private String readerMachineUuid;
/*
    @Column(name = "NO", length = 4, nullable = false)
    private int no;*/

    @Column(name = "MACHINE_ID", length = 36, nullable = false)
    private String machineId;

    @Column(name = "MACHINE_NAME", length = 50, nullable = false)
    private String machineName;

    @Column(name = "GATE_UUID", length = 36, nullable = false)
    private String gateUuid;

    @Column(name = "IP", length = 50, nullable = false)
    private String ip;

    @Column(name = "SUBNETMASK", length = 50, nullable = false)
    private String subnetmask;

    @Column(name = "GATEWAY", length = 50, nullable = false)
    private String gateway;

    @Column(name = "PRINT_DECREASE", length = 4, nullable = false)
    private int printDecrease;

    @Column(name = "MAC_ADDR", length = 50, nullable = false)
    private String macAddr;

    @Column(name = "ANTENNA_CNT", length = 4, nullable = false)
    private int antennaCnt;

    @Column(name = "STATUS_UUID", length = 36, nullable = false)
    private String statusUuid;

    @Override
    public StReaderMachineId getId() { return StReaderMachineId.of(readerMachineUuid); }

    @Embeddable
    @Data
    @NoArgsConstructor
    @RequiredArgsConstructor(staticName = "of")
    public static class StReaderMachineId implements Serializable{
        @NonNull
        private String readerMachineUuid;
    }


}
