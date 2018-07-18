package rmsoft.ams.seoul.common.vo;

import lombok.Data;

/**
 * The type Combo column vo.
 */
@Data
public class ComboColumnVO extends BaseColumnVO {
    private boolean lookupDisplay = true;
    private String values = "";
    private String labels = "";
    private boolean editable = true;
}
