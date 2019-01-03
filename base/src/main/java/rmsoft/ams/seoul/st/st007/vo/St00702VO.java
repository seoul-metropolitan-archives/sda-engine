package rmsoft.ams.seoul.st.st007.vo;

import io.onsemiro.core.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class St00702VO extends BaseVO {

    private String aggregationUuid;
    private String code;
    private String title;
    private String retentionPeriodName; // 보존기간
    private Timestamp descriptionStartDate;
    private Timestamp descriptionEndDate;
    private Timestamp initialDate;
    private Timestamp disposalDueDate;
    private Timestamp disposalCompleteDate;

}
