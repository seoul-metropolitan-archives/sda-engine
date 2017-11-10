package rmsoft.ams.seoul.common.vo;

import lombok.Data;

@Data
public class ComboColumnVO extends BaseColumnVO {
    private boolean lookupDisplay = true;
    private String values = "";
    private String labels = "";
}
