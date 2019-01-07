package rmsoft.ams.seoul.rc.rc001.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import rmsoft.ams.seoul.common.vo.PageInfoVO;

/**
 * The type Rc 00104 vo.
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class Rc00107VO extends PageInfoVO {
    private String uuid;
    private String parentUuid;
    private String nodeType;
    private String title;
    private String level;
    private String publishedStatus;
    private String author;
    private String descriptionStartDate;
    private String descriptionEndDate;
    private String description;
    private String notes;
    private String path;
    private int childCnt;
}
