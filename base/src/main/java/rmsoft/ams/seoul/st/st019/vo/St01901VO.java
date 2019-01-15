package rmsoft.ams.seoul.st.st019.vo;

import io.onsemiro.core.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class St01901VO extends BaseVO {

    private String aggregationUuid;
    private String rfidTagRepublishUuid;
    private String code;
    private String title;
    private String level;
    private String type;
    private String publishedStatus;

    private String repositoryUuid;
    private String shelfUuid;
    private String containerTypeUuid;
    private String locationUuid;

    private String author;
    private String descStrDate;
    private String descEdDate;
    private String repositoryName;
    private String shelfName;
    private String locationName;
    private String publishSourceTypeUuid;
    private String requestDate;
    private String requestDateFrom;
    private String requestDateTo;
    private String republishYn;
    private String republishDate;


}
