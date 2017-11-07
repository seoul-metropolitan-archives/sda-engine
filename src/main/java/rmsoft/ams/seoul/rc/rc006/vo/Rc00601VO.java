package rmsoft.ams.seoul.rc.rc006.vo;

import io.onsemiro.core.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Rc00601VO extends BaseVO
{
    private String levelUuid;
    private String levelNo;
    private String levelName;
    private String shortName;
    private String useYN;
}
