package rmsoft.ams.seoul.rc.rc002.vo;

import io.onsemiro.utils.UUIDUtils;
import lombok.Data;

@Data
public class Rc00201VO
{
    private String saveType;
    private String aggregationUuid;
    private String publishedStatusUuid;
    private String aggregationCode;
    private String title;
    private String typeUuid;
    private String parentsAggregationUuid;
    private String levelUuid;
    private String author;
    private String descriptionStartDate;
    private String descriptionEndDate;
}
