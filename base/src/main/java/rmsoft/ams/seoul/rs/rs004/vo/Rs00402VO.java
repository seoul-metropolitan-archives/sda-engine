package rmsoft.ams.seoul.rs.rs004.vo;

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
public class Rs00402VO extends BaseVO {
    private String recordScheduleUuid;
    private String aggregationUuid;
    private String title;
    private String rsCode;
    private String rsName;
    private String retentionPeriodUuid;
    private String disposalTypeUuid;
    private String catPath;
}
