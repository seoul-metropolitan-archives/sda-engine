package rmsoft.ams.seoul.st.st025.vo;

import io.onsemiro.core.annotations.Comment;
import io.onsemiro.core.domain.SimpleJpaModel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Setter
@Getter
@DynamicInsert
@DynamicUpdate
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "ST_READER_MACHINE")
@Comment("ST_READER_MACHINE")
@Alias("st025")
public class St025 extends SimpleJpaModel<String> {

    @Id
    @Column(name = "READER_MACHINE_UUID", length = 36, nullable = false)
    private String readerMachineUuid;

    @Column(name = "NO", length = 4, nullable = false)
    private int no;

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

    @Column(name = "INSERT_UUID", length = 36, nullable = false)
    private String insertUuid;

    @Column(name = "INSERT_DATE", nullable = false)
    private Timestamp insertDate;

    @Column(name = "UPDATE_UUID", length = 36, nullable = false)
    private String updateUuid;

    @Column(name = "UPDATE_DATE", nullable = false)
    private Timestamp updateDate;

    @Override
    public String getId() {
        return readerMachineUuid;
    }
}
