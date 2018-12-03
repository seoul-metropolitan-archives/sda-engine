package rmsoft.ams.seoul.st.st011.vo;

import io.onsemiro.core.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class St01102VO extends BaseVO{

    private String exceptRecordResultUuid;
    private String inoutExceptUuid;
    private String aggregationUuid;
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
