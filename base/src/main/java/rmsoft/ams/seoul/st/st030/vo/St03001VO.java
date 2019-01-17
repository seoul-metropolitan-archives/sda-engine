package rmsoft.ams.seoul.st.st030.vo;

import io.onsemiro.core.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class St03001VO extends BaseVO {

    private String missArrangeRecordUuid;
    private String aggregationUuid;
    private String code;
    private String title;
    private String level;
    private String type;

    private String repositoryUuid;
    private String shelfUuid;
    private String containerTypeUuid;
    private String locationUuid;

    private String currentContainerUuid;
    private String currentContainerName;
    private String sourceTypeUuid;
    private String sourceTypeName;
    private Timestamp requestDate;
    private String republishYn;
    private Timestamp republishDate;
    private String containerUuid;
    private String containerName;
    private String requestDateFrom;
    private String requestDateTo;

}
