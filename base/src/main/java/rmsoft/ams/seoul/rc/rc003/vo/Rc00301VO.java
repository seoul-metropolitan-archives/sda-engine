package rmsoft.ams.seoul.rc.rc003.vo;

import io.onsemiro.core.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import rmsoft.ams.seoul.ad.ad007.vo.Ad00702VO;
import rmsoft.ams.seoul.rc.rc002.vo.Rc00207VO;
import rmsoft.ams.seoul.rc.rc002.vo.Rc00208VO;
import rmsoft.ams.seoul.rc.rc002.vo.Rc00209VO;
import rmsoft.ams.seoul.rc.rc002.vo.Rc00210VO;

import java.util.List;

/**
 * The type Rc 00301 vo.
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Rc00301VO extends BaseVO {
    private String aggregationUuid;
    private String publishedStatusUuid;
    private String aggregationCode;
    private String parentAggregationName;
    private String name;
    private String publishedStatusName;
    private String levelCode;
    private String parentAggregationUuid;
    private String levelUuid;
    private String author;
    private String descriptionStartDate;
    private String descriptionEndDate;
    private String headTitle;
    private String typeUuid;
    private String typeNm;
    private String provenance;
    private String referenceCode;
    private String creator;
    private String languageCode;
    private String statusDescription;
    private String levelOfDetailUuid;
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
    private String addMetaTemplateSetUuid;
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

    /**
     * The Creator list.
     */
    private List<Rc00207VO> creatorList;
    /**
     * The Material list.
     */
    private List<Rc00208VO> materialList;
    /**
     * The Related Authority list.
     */
    private List<Rc00209VO> relatedAuthorityList;
    /**
     * The Related Record list.
     */
    private List<Rc00210VO> relatedRecordList;
}
