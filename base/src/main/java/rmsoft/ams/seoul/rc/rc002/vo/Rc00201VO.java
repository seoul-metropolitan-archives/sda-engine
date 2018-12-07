package rmsoft.ams.seoul.rc.rc002.vo;

import lombok.Data;

/**
 * The type Rc 00201 vo.
 * DB Table RC_AGGREGATION
 */
@Data
public class Rc00201VO
{
    private String saveType;
    private String aggregationUuid;
    private String publishedStatusUuid;
    private String aggregationCode;
    private String title;
    private String typeUuid;
    private String parentAggregationUuid;
    private String levelUuid;
    private String author;
    private String descriptionStartDate;
    private String descriptionEndDate;
    private String description;
    private String notes;
    private String languageCode;
    private String statusDescription;
    private String levelOfDetailUuid;
}
