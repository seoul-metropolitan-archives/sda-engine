/*
 * Copyright (c) 2017. RMSoft Co.,Ltd. All rights reserved
 */

package rmsoft.ams.seoul.common.domain;

import io.onsemiro.core.annotations.Comment;
import lombok.*;
import org.apache.ibatis.type.Alias;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import rmsoft.ams.seoul.common.workflow.WorkflowJpaModel;

import javax.persistence.*;
import java.io.Serializable;

/**
 * The type Wf parameter result.
 */
@Setter
@Getter
@DynamicInsert
@DynamicUpdate
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "WF_PARAMETER_RESULT")
@IdClass(WfParameterResult.WfParameterResultId.class)
@Alias("WfParameterResult")
public class WfParameterResult extends WorkflowJpaModel<WfParameterResult.WfParameterResultId> {

    @Id
    @Column(name = "PARAMETER_RESULT_UUID", length = 36, nullable = false)
    @Comment(value = "Parameter Result UUID")
    private String parameterResultUuid;

    @Column(name = "JOB_RESULT_UUID", length = 36, nullable = false)
    @Comment(value = "Job Result UUID")
    private String jobResultUuid;

    @Column(name = "PARAMETER_UUID", length = 36, nullable = false)
    @Comment(value = "Parameter UUID")
    private String parameterUuid;

    @Column(name = "PARAMETER_NAME", length = 50, nullable = false)
    @Comment(value = "Parameter Name")
    private String parameterName;

    @Column(name = "BATCH_ID", nullable = false)
    @Comment(value = "Batch ID")
    private long batchId;

    @Column(name = "INPUT_METHOD_UUID", length = 36, nullable = false)
    @Comment(value = "Input Method UUID")
    private String inputMethodUuid;

    @Column(name = "INPUT_CODE_UUID", length = 36)
    @Comment(value = "Input Code UUID")
    private String inputCodeUuid;

    @Column(name = "IN_OUT_UUID", length = 36, nullable = false)
    @Comment(value = "In Out UUID")
    private String inOutUuid;

    @Column(name = "VALUE", length = 100)
    @Comment(value = "Value")
    private String value;

    @Column(name = "DISPLAY_YN", length = 1, nullable = false)
    @Comment(value = "Display")
    private String displayYn;

    @Column(name = "REQUIRED_YN", length = 1, nullable = false)
    @Comment(value = "Required")
    private String requiredYn;

    @Override
    public WfParameterResultId getId() {
        return WfParameterResultId.of(parameterResultUuid);
    }

    /**
     * The type Wf parameter result id.
     */
    @Embeddable
    @Data
    @NoArgsConstructor
    @RequiredArgsConstructor(staticName = "of")
    public static class WfParameterResultId implements Serializable {

        @NonNull
        private String parameterResultUuid;
    }
}