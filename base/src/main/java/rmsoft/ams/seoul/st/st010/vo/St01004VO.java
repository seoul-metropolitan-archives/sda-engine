package rmsoft.ams.seoul.st.st010.vo;

import io.onsemiro.core.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class St01004VO extends BaseVO{

    private String arrangeContainersResultUuid;
    private String locationUuid;
    private String containerUuid;
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
