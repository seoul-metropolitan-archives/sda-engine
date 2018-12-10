package rmsoft.ams.seoul.rc.rc005.vo;

import io.onsemiro.core.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The type Rc 00206 vo.
 * DB Table RC_AGGREGATION_CREATOR
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class Rc00503VO extends BaseVO
{
    private String itemCreatorUuid;
    private String itemUuid;
    private String creatorUuid;
    private String creatorName;
}
