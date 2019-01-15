package rmsoft.ams.seoul.st.st008.vo;

import io.onsemiro.core.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * The type Cl 00301 vo.
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class St00802VO extends BaseVO {

    private String takeoutRecordResultUuid;
    private String takeoutRequestUuid;
    private String aggregationUuid;
    private String code;
    private String repositoryUuid;
    private String shelfUuid;
    private String containerTypeUuid;
    private String locationUuid;

    private String title;
    private String level;
    private String type;
    private String publishedStatus;
    private String author;
    private String descStrDate;
    private String descEdDate;

    private String publishedStatusUuid;
    private String locationName; // 행렬단


}
