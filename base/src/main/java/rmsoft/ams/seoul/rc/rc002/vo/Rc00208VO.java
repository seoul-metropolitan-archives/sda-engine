package rmsoft.ams.seoul.rc.rc002.vo;

import io.onsemiro.core.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The type Rc 00206 vo.
 * DB Table RC_AGGREGATION_MATERIAL
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class Rc00208VO extends BaseVO
{
    private String aggregationMaterialUuid;
    private String aggregationUuid;
    private String materialTypeUuid;
}

