package rmsoft.ams.seoul.rc.rc001.vo;

import io.onsemiro.core.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * The type Rc 00101 vo.
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Rc00101VO extends BaseVO
{
    private String isDisplayItem;
    private String uuid;
    private String parentUuid;
    private String nodeType;
    private String level;
    private String name;
    private String publishedStatus;
    private String orderKey1;
    private String classifyRecordsUuid;
    private String classUuid;
    private int childCnt;
}
