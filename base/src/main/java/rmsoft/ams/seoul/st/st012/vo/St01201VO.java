package rmsoft.ams.seoul.st.st012.vo;

import io.onsemiro.core.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class St01201VO extends BaseVO {

    private String aggregationUuid;
    private String withoutNoticeIoRecordUuid;
    private String repositoryUuid;
    private String shelfUuid;

    private String code;
    private String title;
    private String level;
    private String type;
    private String publishedStatus;
    private String author;
    private String descStrDate;
    private String descEdDate;
    private String repositoryName;
    private String shelfName;
    private String locationName;

    private String inoutDateTime;
    private String inoutDateTimeFrom;
    private String inoutDateTimeTo;


}
