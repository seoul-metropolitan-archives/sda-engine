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
    private Timestamp initialDate;
    private Timestamp disposalDueDate;
    private Timestamp disposalConfirmDate;
    private String disposalConfirmReason;
    private Timestamp disposalCompleteDate;
}
