package rmsoft.ams.seoul.common.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The type Check column vo.
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class CheckColumnVO extends BaseColumnVO {
    private boolean editable = true;
}
