package rmsoft.ams.seoul.rc.rc004.vo;

import io.onsemiro.core.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Rc00401VO extends BaseVO {

    private String raAggregationUuid;

    private String raPublishedStatusUuid;

    private String raAggregationCode;

    private String raTitle;

    private String raTypeUuid;

    private String raParentAggregationUuid;

    private String raLevelNm;

    private String raAuthor;

    private String raDescriptionStartDate;

    private String raDescriptionEndDate;

    private String raLevelUuid;

    private String riItemUuid;

    private String riPublishedStatusUuid;

    private String riPublishedStatusNm;

    private String riItemCode;

    private String name; //title

    private String riTypeUuid;

    private String riAggregationUuid;

}
