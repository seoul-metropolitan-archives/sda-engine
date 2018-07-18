package rmsoft.ams.seoul.rs.rs002.vo;

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
public class Rs00201VO extends BaseVO {

    private String triggerUuid;
    private String statusUuid;
    private String triggerCode ;
    private String triggerName;
    private String triggerDate;
    private String changeStatus;
    private String useYn;
}
