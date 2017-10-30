package rmsoft.ams.seoul.common.vo;

import java.util.ArrayList;
import java.util.List;

public class ComboColumnVO extends BaseColumnVO{
    private boolean lookupDisplay = true;
    private List<String> values = new ArrayList<String>();
    private List<String> labels = new ArrayList<String>();
}
