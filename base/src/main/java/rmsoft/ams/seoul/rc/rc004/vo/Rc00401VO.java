package rmsoft.ams.seoul.rc.rc004.vo;

import io.onsemiro.core.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * The type Rc 00401 vo.
 */
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

    private String notes;

    private String description;

    private String languageCode;

    private String statusDescription;

    private String levelOfDetailUuid;

    /** 설계변경 신규추가 **/
    private String sourceSystemUuid;

    private String creationSystemUuid;

    private String addMetaTemplateSetUuid;

    private String legalStatusUuid;

    private String repositoriesUuid;

    private String repositoriesName;

    private String electronicRecordStatusUuid;

    private String accumulationStartDate;

    private String accumulationEndDate;

    private String scopeContent;

    private String custodialHistory;

    private String sourceAcquisitionUuid;

    private String sourceAcquisitionName;

    private String physicalCondition;

    private String useCondition;

    private String findingAids;

    private String rulesConversionUuid;

    private String recordScheduleUuid;

    private String accessCondition;

}
