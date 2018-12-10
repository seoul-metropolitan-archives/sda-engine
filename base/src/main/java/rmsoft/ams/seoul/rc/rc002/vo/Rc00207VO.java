package rmsoft.ams.seoul.rc.rc002.vo;

import io.onsemiro.core.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The type Rc 00206 vo.
 * DB Table RC_AGGREGATION_CREATOR
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class Rc00207VO extends BaseVO
{
    private String aggregationCreatorUuid;
    private String aggregationUuid;
    private String creatorUuid;
    private String creatorName;
}
