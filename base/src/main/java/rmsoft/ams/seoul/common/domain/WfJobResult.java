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
import java.sql.Timestamp;

/**
 * The type Wf job result.
 */
@Setter
@Getter
@DynamicInsert
@DynamicUpdate
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "WF_JOB_Result")
@IdClass(WfJobResult.WfJobResultId.class)
@Alias("WfJobResult")
public class WfJobResult extends WorkflowJpaModel<WfJobResult.WfJobResultId> {

    @Id
    @Column(name = "JOB_RESULT_UUID", length = 36, nullable = false)
    @Comment(value = "Job Result UUID")
    private String jobResultUuid;

    @Column(name = "WORKFLOW_RESULT_UUID", length = 36, nullable = false)
    @Comment(value = "Workflow Result UUID")
    private String workflowResultUuid;

    @Column(name = "SEQUENCE", nullable = false)
    @Comment(value = "Sequence")
    private long sequence;

    @Column(name = "JOB_UUID", length = 36, nullable = false)
    @Comment(value = "Job UUID")
    private String jobUuid;

    @Column(name = "JOB_NAME", length = 50, nullable = false)
    @Comment(value = "Job Name")
    private String jobName;

    @Column(name = "API", length = 500, nullable = false)
    @Comment(value = "API")
    private String api;

    @Column(name = "SKIP_YN", length = 1, nullable = false)
    @Comment(value = "Skip")
    private String skipYn;

    @Column(name = "TERMINATE_YN", length = 1, nullable = false)
    @Comment(value = "Terminate")
    private String terminateYn;

    @Column(name = "BATCH_ID", nullable = false)
    @Comment(value = "Batch ID")
    private long batchId;

    @Column(name = "STATUS_UUID", length = 36, nullable = false)
    @Comment(value = "Status UUID")
    private String statusUuid;

    /**
     * The Start date.
     */
    @Column(name = "START_DATE", nullable = false)
    @Comment(value = "Start Date")
    protected Timestamp startDate;

    /**
     * The End date.
     */
    @Column(name = "END_DATE")
    @Comment(value = "End Date")
    protected Timestamp endDate;

    @Column(name = "MESSAGE", length = 4000)
    @Comment(value = "Message")
    private String message;

    @Override
    public WfJobResultId getId() {
        return WfJobResultId.of(jobResultUuid);
    }

    /**
     * The type Wf job result id.
     */
    @Embeddable
    @Data
    @NoArgsConstructor
    @RequiredArgsConstructor(staticName = "of")
    public static class WfJobResultId implements Serializable {

        @NonNull
        private String jobResultUuid;
    }
}