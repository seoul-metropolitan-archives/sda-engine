package rmsoft.ams.seoul.rc.rc002.vo;

import io.onsemiro.core.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The type Rc 00206 vo.
 * DB Table RC_AGGR_RELATED_AUTHORITY
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class Rc00209VO extends BaseVO
{
    private String aggrRelatedAuthorityUuid;
    private String aggregationUuid;
    private String authorityUuid;
    private String authorityName;
}
