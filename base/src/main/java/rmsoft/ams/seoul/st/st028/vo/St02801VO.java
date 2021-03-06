package rmsoft.ams.seoul.st.st028.vo;


import io.onsemiro.core.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class St02801VO extends BaseVO {

    private String gateUuid;

    private String gateId;

    private String gateName;

    private String modeUuid;

    private String sensorUseYn;

    private String inZoneUuid;

    private String inZoneName;

    private String outZoneUuid;

    private String outZoneName;

    private String lightBarStatusUuid;
}
