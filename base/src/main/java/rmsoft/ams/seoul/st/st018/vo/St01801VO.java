package rmsoft.ams.seoul.st.st018.vo;

import com.fasterxml.jackson.databind.deser.Deserializers;
import io.onsemiro.core.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class St01801VO extends BaseVO implements Serializable {

    private String aggregationUuid;
    private String code;
    private String title;
    private String level;
    private String type;
    private String publishedStatus;
    private String publishedStatusUuid;
    private String containerName;

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

    private String publishStatus;
    private String publishStatusUuid; // 태그발행 상태 uuid
    private String publishDate;
    private String publishDateFrom;
    private String publishDateTo;
    private String PublishSourceTypeUuid;

    private String rfidMachineUuid;

}
