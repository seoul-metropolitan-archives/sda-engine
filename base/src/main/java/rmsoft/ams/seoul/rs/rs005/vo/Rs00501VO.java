package rmsoft.ams.seoul.rs.rs005.vo;

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
public class Rs00501VO extends BaseVO {

    private String recordScheduleUuid;
    private String generalRecordScheduleUuid;
    private String triggerUuid;
    private String statusUuid;
    private String rsCode ;
    private String rsName;
    private String grsCode;
    private String grsName;
    private String retentionPeriodUuid;
    private String disposalTypeUuid;
    private String basedOn;
    private String triggerName;
    private String triggerDate;
    private String useYn;
    private String changeStatus;
    private String triggerYn; //just input
    private String recalculationYn;
}
