package rmsoft.ams.seoul.rc.rc001.vo;

import lombok.Data;
import rmsoft.ams.seoul.common.vo.PageInfoVO;

@Data
public class Rc00104VO extends PageInfoVO {
    private String isDisplayItem;
    private String uuid;
    private String parentUuid;
    private String nodeType;
    private String level;
    private String name;
    private int childCnt;
}
