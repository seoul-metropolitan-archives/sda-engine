package rmsoft.ams.seoul.rc.rc001.vo;

import io.onsemiro.core.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * The type Rc 00102 vo.
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Rc00102VO extends BaseVO
{
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
}
