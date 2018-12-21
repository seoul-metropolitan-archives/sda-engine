package rmsoft.ams.seoul.st.st009.vo;

import io.onsemiro.core.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;


@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class St00902VO extends BaseVO {

    private String takeoutRecordResultUuid;
    private String takeoutRequestUuid;
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
    private String repositoryName;
    private String shelfName;
    private String locationName;

}
