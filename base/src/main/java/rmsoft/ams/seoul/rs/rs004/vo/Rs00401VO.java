package rmsoft.ams.seoul.rs.rs004.vo;

import io.onsemiro.core.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * The type Cl 00101 vo.
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Rs00401VO extends BaseVO {

    private String recordScheduleResultUuid;
    private String recordScheduleUuid;
    private String statusUuid;
    private String itemUuid;
    private String disposalTypeUuid;
    private String initialDate;
    private String disposalDueDate;
    private String disposalFromDueDate;
    private String disposalToDueDate;;
    private String disposalFromConfirmDate;
    private String disposalToConfirmDate;
    private String disposalConfirmDate;
    private String disposalConfirmReason;
    private Timestamp disposalCompleteDate;
    private String disposalFromCompleteDate;
    private String disposalToCompleteDate;
    private String aggregationTree;
    private String itemTitle;
    private String changeStatus;
    private String disposalStatus;
    private String itemTypeUuid;
    private String rsCode;
    private String rsName;
    private String retentionPeriodUuid;
    private String disposalFreeze;
    private String itemCode;
}
