package rmsoft.ams.seoul.rc.rc002.vo;

import io.onsemiro.core.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The type Rc 00206 vo.
 * DB Table RC_AGGR_RELATED_RECORD
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class Rc00210VO extends BaseVO
{
    private String aggrRelatedRecordUuid;
    private String aggregationUuid;
    private String relatedAggregationUuid;
    private String relatedItemUuid;
}
