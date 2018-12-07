package rmsoft.ams.seoul.rc.rc005.vo;

import io.onsemiro.core.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The type Rc 00206 vo.
 * DB Table RC_AGGREGATION_MATERIAL
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class Rc00504VO extends BaseVO
{
    private String itemMaterialUuid;
    private String itemUuid;
    private String materialTypeUuid;
}

