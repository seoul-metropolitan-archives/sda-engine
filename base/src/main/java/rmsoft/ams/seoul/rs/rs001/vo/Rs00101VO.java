package rmsoft.ams.seoul.rs.rs001.vo;

import io.onsemiro.core.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * The type Cl 00101 vo.
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Rs00101VO extends BaseVO {

    private String generalRecordScheduleUuid;
    private String statusUuid;
    private String grsCode ;
    private String grsName;
    private String retentionPeriodUuid;
    private String disposalTypeUuid;
    private String triggerYn;
    private String useYn;
    private String basedOn;
    private String changeStatus;
}
