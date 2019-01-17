package rmsoft.ams.seoul.st.st013.vo;

import io.onsemiro.core.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class St01302VO extends BaseVO{

    private String exceptRecordResultUuid;
    private String inoutExceptUuid;

    private String uuid;
    private String aggregationUuid;
    private String publishedStatus;

    private String repositoryUuid;
    private String shelfUuid;
    private String containerTypeUuid;
    private String locationUuid;
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
