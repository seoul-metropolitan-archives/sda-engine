package rmsoft.ams.seoul.st.st007.vo;

import io.onsemiro.core.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class St00701VO extends BaseVO {

    private String aggregationUuid;
    private String repositoryUuid;
    private String shelfUuid;
    private String containerTypeUuid;
    private String locationUuid;

    private String retentionPeriodName;
    private String code;
    private String title;
    private String level;
    private String type;
    private String publishedStatus;
    private String author;

    private Timestamp descStrDate;
    private Timestamp descEdDate;
    private String repositoryName;
    private String shelfName;
    private String locationName;
    private String containerUuid;
    private String containerName;

    private String disposalDueDateStart; //filter 인애들은 string 을 주자
    private String disposalDueDateEnd;//filter 인애들은 string 을 주자

}
