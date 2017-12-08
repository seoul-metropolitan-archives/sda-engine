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
 * The type Wf workflow result.
 */
@Setter
@Getter
@DynamicInsert
@DynamicUpdate
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "WF_WORKFLOW_RESULT")
@IdClass(WfWorkflowResult.WfWorkflowResultId.class)
@Alias("WfWorkflowResult")
public class WfWorkflowResult extends WorkflowJpaModel<WfWorkflowResult.WfWorkflowResultId> {

    @Id
    @Column(name = "WORKFLOW_RESULT_UUID", length = 36, nullable = false)
    @Comment(value = "Workflow Result UUID")
    private String workflowResultUuid;

    @Column(name = "WORKFLOW_UUID", length = 36, nullable = false)
    @Comment(value = "Workflow UUID")
    private String workflowUuid;

    @Column(name = "WORKFLOW_NAME", length = 50, nullable = false)
    @Comment(value = "Workflow Name")
    private String workflowName;

    @Column(name = "BATCH_ID", nullable = false)
    @Comment(value = "Batch ID")
    private long batchId;

    @Column(name = "STATUS_UUID", length = 36, nullable = false)
    @Comment(value = "Status UUID")
    private String statusUuid;

    @Column(name = "EXECUTER_UUID", length = 36)
    @Comment(value = "Executer UUID")
    private String executerUuid;

    @Column(name = "MENU_UUID", length = 36, nullable = false)
    @Comment(value = "Program UUID")
    private String menuUuid;

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

    @Override
    public WfWorkflowResultId getId() {
        return WfWorkflowResultId.of(workflowResultUuid);
    }

    /**
     * The type Wf workflow result id.
     */
    @Embeddable
    @Data
    @NoArgsConstructor
    @RequiredArgsConstructor(staticName = "of")
    public static class WfWorkflowResultId implements Serializable {

        @NonNull
        private String workflowResultUuid;
    }
}