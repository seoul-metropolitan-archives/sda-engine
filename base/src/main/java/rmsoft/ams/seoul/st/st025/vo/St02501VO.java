package rmsoft.ams.seoul.st.st025.vo;

import io.onsemiro.core.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class St02501VO extends BaseVO {
    private String readerMachineUuid;

    private int no;

    private String machineId;

    private String machineName;

    private String gateUuid;

    private String ip;

    private String subnetmask;

    private String gateway;

    private int printDecrease;

    private String macAddr;

    private int antennaCnt;

    private String statusUuid;

    private String parentGateName;
}
