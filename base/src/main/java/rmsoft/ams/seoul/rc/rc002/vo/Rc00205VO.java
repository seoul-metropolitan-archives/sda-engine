package rmsoft.ams.seoul.rc.rc002.vo;

import io.onsemiro.core.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The type Rc 00205 vo.
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class Rc00205VO extends BaseVO{

    private String levelUuid;
    private int levelNo;
    private String levelName;
    private String shortName;

}
