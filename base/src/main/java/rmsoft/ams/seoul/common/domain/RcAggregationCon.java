package rmsoft.ams.seoul.common.domain;

import io.onsemiro.core.annotations.Comment;
import io.onsemiro.core.domain.SimpleJpaModel;
import lombok.*;
import org.apache.ibatis.type.Alias;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

/**
 * The type Rc aggregation con.
 */
@Setter
@Getter
@DynamicInsert
@DynamicUpdate
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "RC_AGGREGATION_CON")
@IdClass(RcAggregationCon.RcAggregationConId.class)
@Alias("RcAggregationCon")

public class RcAggregationCon extends SimpleJpaModel<RcAggregationCon.RcAggregationConId> {

    @Id
    @Column(name = "AGGREGATION_UUID", length = 36, nullable = false)
    @Comment(value = "AGGREGATION UUID")
    private String aggregationUuid;

    @Column(name = "REFERENCE_CODE", length = 500, nullable = true)
    @Comment(value = "")
    private String referenceCode;

    @Column(name = "PROVENANCE", length = 500, nullable = true)
    @Comment(value = "")
    private String provenance;

    @Column(name = "CREATOR", length = 500, nullable = true)
    @Comment(value = "")
    private String creator;

    @Column(name = "CREATION_START_DATE", length = 8, nullable = true)
    @Comment(value = "")
    private String creationStartDate;

    @Column(name = "CREATION_END_DATE", length = 8, nullable = true)
    @Comment(value = "")
    private String creationEndDate;

    @Column(name = "ADD_METADATA01", length = 100, nullable = true)
    @Comment(value = "")
    private String addMetadata01;

    @Column(name = "ADD_METADATA02", length = 100, nullable = true)
    @Comment(value = "")
    private String addMetadata02;

    @Column(name = "ADD_METADATA03", length = 100, nullable = true)
    @Comment(value = "")
    private String addMetadata03;

    @Column(name = "ADD_METADATA04", length = 100, nullable = true)
    @Comment(value = "")
    private String addMetadata04;

    @Column(name = "ADD_METADATA05", length = 100, nullable = true)
    @Comment(value = "")
    private String addMetadata05;

    @Column(name = "ADD_METADATA06", length = 100, nullable = true)
    @Comment(value = "")
    private String addMetadata06;

    @Column(name = "ADD_METADATA07", length = 100, nullable = true)
    @Comment(value = "")
    private String addMetadata07;

    @Column(name = "ADD_METADATA08", length = 100, nullable = true)
    @Comment(value = "")
    private String addMetadata08;

    @Column(name = "ADD_METADATA09", length = 100, nullable = true)
    @Comment(value = "")
    private String addMetadata09;

    @Column(name = "ADD_METADATA10", length = 100, nullable = true)
    @Comment(value = "")
    private String addMetadata10;

    @Column(name = "EXTRA_METADATA", length = 4000, nullable = true)
    @Comment(value = "extraMetadata")
    private String extraMetadata;

    @Column(name = "SOURCE_SYSTEM_UUID", length = 36, nullable = true)
    @Comment(value = "SOURCE_SYSTEM_UUID")
    private String sourceSystemUuid;

    @Column(name = "CREATION_SYSTEM_UUID", length = 36, nullable = true)
    @Comment(value = "CREATION_SYSTEM_UUID")
    private String creationSystemUuid;

    @Column(name = "ADD_META_TEMPLATE_SET_UUID", length = 36, nullable = true)
    @Comment(value = "ADD_META_TEMPLATE_SET_UUID")
    private String addMetaTemplateSetUuid;

    @Column(name = "LEGAL_STATUS_UUID", length = 36, nullable = true)
    @Comment(value = "LEGAL_STATUS_UUID")
    private String legalStatusUuid;

    @Column(name = "REPOSITORIES_UUID", length = 36, nullable = true)
    @Comment(value = "REPOSITORIES_UUID")
    private String repositoriesUuid;

    @Column(name = "ELECTRONIC_RECORD_STATUS_UUID", length = 36, nullable = true)
    @Comment(value = "ELECTRONIC_RECORD_STATUS_UUID")
    private String electronicRecordStatusUuid;

    @Column(name = "ACCUMULATION_START_DATE", length = 10, nullable = true)
    @Comment(value = "ACCUMULATION_START_DATE")
    private String accumulationStartDate;

    @Column(name = "ACCUMULATION_END_DATE", length = 10, nullable = true)
    @Comment(value = "ACCUMULATION_END_DATE")
    private String accumulationEndDate;

    @Column(name = "SCOPE_CONTENT", length = 4000, nullable = true)
    @Comment(value = "SCOPE_CONTENT")
    private String scopeContent;

    @Column(name = "ARRANGEMENT", length = 4000, nullable = true)
    @Comment(value = "ARRANGEMENT")
    private String arrangement;

    @Column(name = "ACCRUALS", length = 4000, nullable = true)
    @Comment(value = "ACCRUALS")
    private String accruals;

    @Column(name = "CUSTODIAL_HISTORY", length = 4000, nullable = true)
    @Comment(value = "CUSTODIAL_HISTORY")
    private String custodialHistory;

    @Column(name = "SOURCE_ACQUISITION_UUID", length = 36, nullable = true)
    @Comment(value = "SOURCE_ACQUISITION_UUID")
    private String sourceAcquisitionUuid;

    @Column(name = "PHYSICAL_CONDITION", length = 500, nullable = true)
    @Comment(value = "PHYSICAL_CONDITION")
    private String physicalCondition;

    @Column(name = "USE_CONDITION", length = 500, nullable = true)
    @Comment(value = "USE_CONDITION")
    private String useCondition;

    @Column(name = "FINDING_AIDS", length = 500, nullable = true)
    @Comment(value = "FINDING_AIDS")
    private String findingAids;

    @Column(name = "RULES_CONVERSION_UUID", length = 36, nullable = true)
    @Comment(value = "RULES_CONVERSION_UUID")
    private String rulesConversionUuid;

    @Column(name = "RECORD_SCHEDULE_UUID", length = 36, nullable = true)
    @Comment(value = "RECORD_SCHEDULE_UUID")
    private String recordScheduleUuid;

    @Column(name = "ACCESS_CONDITION", length = 500, nullable = true)
    @Comment(value = "ACCESS_CONDITION")
    private String accessCondition;

    @Override
    public RcAggregationCon.RcAggregationConId getId() { return RcAggregationCon.RcAggregationConId.of(aggregationUuid); }

    /**
     * The type Rc aggregation con id.
     */
    @Embeddable
    @Data
    @NoArgsConstructor
    @RequiredArgsConstructor(staticName = "of")
    public static class RcAggregationConId implements Serializable {
        @NonNull
        private String aggregationUuid;
    }
}
