package rmsoft.ams.seoul.rc.rc001.vo;

import io.onsemiro.core.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * The type Rc 00103 vo.
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Rc00103VO extends BaseVO
{
    private String uuid;
    private String code;
    private String nodeType;
    private String type;
    private String title;
    private String publishedStatusUuid;
    private String typeUuid;
    private String levelUuid;
    private String author;
    private String descriptionStartDate;
    private String descriptionEndDate;
    private String description;
    private String notes;
    private String languageCode;
    private String statusDescription;
    private String levelOfDetailUuid;
    private int childCnt;

    private String parentAggregationUuid;
    private String aggregationUuid;

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
    private String extraMetadata;
    private String sourceSystemUuid;
    private String creationSystemUuid;
    private String AddMetaTemplateSetUuid;
    private String legalStatusUuid;
    private String repositoriesUuid;
    private String repositoriesName;
    private String electronicRecordStatusUuid;
    private String accumulationStartDate;
    private String accumulationEndDate;
    private String scopeContent;
    private String arrangement;
    private String accruals;
    private String custodialHistory;
    private String sourceAcquisitionUuid;
    private String sourceAcquisitionName;
    private String physicalCondition;
    private String useCondition;
    private String findingAids;
    private String rulesConversionUuid;
    private String recordScheduleUuid;
    private String accessCondition;
    private String keyword;

    private String recordRelatedAuthorityUuid;
    private String authorityUuid;
    private String authorityName;

    private String recordCreatorUuid;
    private String creatorUuid;
    private String creatorName;
}
