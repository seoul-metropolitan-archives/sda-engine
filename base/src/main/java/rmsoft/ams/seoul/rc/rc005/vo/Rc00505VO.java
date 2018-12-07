package rmsoft.ams.seoul.rc.rc005.vo;

import io.onsemiro.core.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The type Rc 00206 vo.
 * DB Table RC_AGGR_RELATED_AUTHORITY
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class Rc00505VO extends BaseVO
{
    private String itemRelatedAuthorityUuid;
    private String itemUuid;
    private String authorityUuid;
    private String authorityName;
}
