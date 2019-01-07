package rmsoft.ams.seoul.rc.rc005.vo;

import io.onsemiro.core.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * The type Rc 00501 vo.
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Rc00501VO extends BaseVO {

    private String raTitle;

    private String raAggregationUuid;

    private String raPublishedStatusUuid;

    private String raAggregationCode;

    private String raTypeUuid;

    private String raParentAggregationUuid;

    private String raLevelNm;

    private String raAuthor;

    private String raDescriptionStartDate;

    private String raDescriptionEndDate;

    private String raLevelUuid;

    private String riItemUuid;

    private String riPublishedStatusUuid;

    private String riPublishedStatusName;

    private String riItemCode;

    private String name; //title

    private String riTypeUuid;

    private String riTypeName;

    private String riAggregationUuid;

    private String riAuthor;

    private String riDescriptionStartDate;

    private String riDescriptionEndDate;

    private String provenance;

    private String openStatusUuid;

    private String openStatusName;

    private String keyword;

    private String referenceCode;

    private String creator;

    private String creationStartDate;

    private String creationEndDate;

    private String addMetadata01;

    private String addMetadata02;

    private String addMetadata03;

    private String addMetadata04;

    private String addMetadata05;

    private String addMetadata06;

    private String addMetadata07;

    private String addMetadata08;

    private String addMetadata09;

    private String addMetadata10;

    private String description1;

    private String notes1;

    private String extraMetadata;

    /** 설계변경 신규추가 **/
    private String languageCode;

    private String languageName;

    private String statusDescription;

    private String statusDescriptionName;

    private String levelOfDetailUuid;

    private String levelOfDetailName;

    private String sourceSystemUuid;

    private String creationSystemUuid;

    private String addMetaTemplateSetUuid;

    private String legalStatusUuid;

    private String legalStatusName;

    private String repositoriesUuid;

    private String repositoriesName;

    private String electronicRecordStatusUuid;

    private String electronicRecordStatusName;

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

    private String rulesConversionName;

    private String recordScheduleUuid;

    private String recordScheduleName;

    private String accessCondition;

    private List<Rc00502VO> rc00502VoList;

    /**
     * The Creator list.
     */
    private List<Rc00503VO> creatorList;
    /**
     * The Material list.
     */
    private List<Rc00504VO> materialList;
    /**
     * The Related Authority list.
     */
    private List<Rc00505VO> relatedAuthorityList;
    /**
     * The Related Record list.
     */
    private List<Rc00506VO> relatedRecordList;

    /**
     * Sets ra description start date.
     *
     * @param raDescriptionStartDate the ra description start date
     */
    public void setRaDescriptionStartDate(String raDescriptionStartDate) {
        if(raDescriptionStartDate != null) {
            this.raDescriptionStartDate = raDescriptionStartDate.replace("-", "");
        }
    }

    /**
     * Sets ra description end date.
     *
     * @param raDescriptionEndDate the ra description end date
     */
    public void setRaDescriptionEndDate(String raDescriptionEndDate) {
        if(raDescriptionEndDate != null) {
            this.raDescriptionEndDate = raDescriptionEndDate.replace("-", "");
        }
    }

    /**
     * Sets ri description start date.
     *
     * @param riDescriptionStartDate the ri description start date
     */
    public void setRiDescriptionStartDate(String riDescriptionStartDate) {
        if(riDescriptionStartDate != null) {
            this.riDescriptionStartDate = riDescriptionStartDate.replace("-", "");
        }
    }

    /**
     * Sets ri description end date.
     *
     * @param riDescriptionEndDate the ri description end date
     */
    public void setRiDescriptionEndDate(String riDescriptionEndDate) {
        if(riDescriptionEndDate != null) {
            this.riDescriptionEndDate = riDescriptionEndDate.replace("-", "");
        }
    }
}
