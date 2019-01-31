package rmsoft.ams.seoul.rc.rc002.vo;

import lombok.Data;
import oracle.sql.CLOB;

import java.sql.Clob;

/**
 * The type Rc 00202 vo.
 * DB Table RC_AGGREGATION_CON
 */
@Data
public class Rc00202VO {
    private String saveType;
    private String aggregationUuid;
    private String referenceCode;
    private String provenance;
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
    private String electronicRecordStatusUuid;
    private String accumulationStartDate;
    private String accumulationEndDate;
    private String scopeContent;
    private String arrangement;
    private String accruals;
    private String custodialHistory;
    private String sourceAcquisitionUuid;
    private String physicalCondition;
    private String useCondition;
    private String findingAids;
    private String rulesConversionUuid;
    private String recordScheduleUuid;
    private String accessCondition;
}
