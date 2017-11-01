/*
 * Copyright (c) 2017. RMSoft Co.,Ltd. All rights reserved
 */

package rmsoft.ams.seoul.common.domain;

import io.onsemiro.core.annotations.Comment;
import io.onsemiro.core.domain.BaseJpaModel;
import lombok.*;
import org.apache.ibatis.type.Alias;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

@Setter
@Getter
@DynamicInsert
@DynamicUpdate
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "WF_WORKFLOW_JOB")
@IdClass(WfWorkflowJob.WfWorkflowJobId.class)
@Alias("WfWorkflowJob")
public class WfWorkflowJob extends BaseJpaModel<WfWorkflowJob.WfWorkflowJobId> {

    @Id
    @Column(name = "WORKFLOW_JOB_UUID", length = 36, nullable = false)
    @Comment(value = "Workflow Job UUID")
    private String workflowJobUuid;

    @Column(name = "WORKFLOW_UUID", length = 36, nullable = false)
    @Comment(value = "Workflow UUID")
    private String workflowUuid;

    @Column(name = "SEQUENCE", nullable = false)
    @Comment(value = "Sequence")
    private long sequence;

    @Column(name = "JOB_UUID", length = 36, nullable = false)
    @Comment(value = "Job UUID")
    private String jobUuid;

    @Column(name = "SKIP_YN", length = 1, nullable = false)
    @Comment(value = "Skip")
    private String skipYn;

    @Column(name = "TERMINATE_YN", length = 1, nullable = false)
    @Comment(value = "Terminate")
    private String terminateYn;

    @Column(name = "USE_YN", length = 1, nullable = false)
    @Comment(value = "사용여부")
    private String useYn;

    @Override
    public WfWorkflowJobId getId() {
        return WfWorkflowJobId.of(workflowJobUuid);
    }

    @Embeddable
    @Data
    @NoArgsConstructor
    @RequiredArgsConstructor(staticName = "of")
    public static class WfWorkflowJobId implements Serializable {

        @NonNull
        private String workflowJobUuid;
    }
}