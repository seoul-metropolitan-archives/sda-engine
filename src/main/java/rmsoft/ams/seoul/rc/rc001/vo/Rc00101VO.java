package rmsoft.ams.seoul.rc.rc001.vo;

import io.onsemiro.core.vo.BaseVO;
import lombok.Data;

@Data
public class Rc00101VO extends BaseVO
{
    private String uuid;
    private String parentUuid;
    private String nodeType;
    private String name;
}
