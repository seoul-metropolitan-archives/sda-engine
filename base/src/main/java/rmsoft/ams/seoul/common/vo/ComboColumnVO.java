package rmsoft.ams.seoul.common.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The type Combo column vo.
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class ComboColumnVO extends BaseColumnVO {
    private boolean lookupDisplay = true;
    private String values = "";
    private String labels = "";
    private boolean editable = true;
}
