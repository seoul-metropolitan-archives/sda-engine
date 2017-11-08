package rmsoft.ams.seoul.rc.rc001.vo;

import io.onsemiro.core.vo.BaseVO;
import lombok.Data;

@Data
public class Rc00103VO extends BaseVO
{
    private String uuid;
    private String publishedStatus;
    private String code;
    private String title;
    private String type;
    private String level;
    private String author;
    private String descStrDate;
    private String descEdDate;
    private String description;
    private String notes;
    private int childCnt;
}
