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
@Table(name = "WF_WORKFLOW")
@IdClass(WfWorkflow.WfWorkflowId.class)
@Alias("WfWorkflow")
public class WfWorkflow extends BaseJpaModel<WfWorkflow.WfWorkflowId> {

    @Id
    @Column(name = "WORKFLOW_UUID", length = 36, nullable = false)
    @Comment(value = "Workflow UUID")
    private String workflowUuid;

    @Column(name = "WORKFLOW_NAME", length = 50, nullable = false)
    @Comment(value = "Workflow Name")
    private String workflowName;

    @Column(name = "SERVICE_UUID", length = 36, nullable = false)
    @Comment(value = "Service UUID")
    private String serviceUuid;

    @Column(name = "USE_YN", length = 1, nullable = false)
    @Comment(value = "사용여부")
    private String useYn;

    @Override
    public WfWorkflowId getId() {
        return WfWorkflowId.of(workflowUuid);
    }

    @Embeddable
    @Data
    @NoArgsConstructor
    @RequiredArgsConstructor(staticName = "of")
    public static class WfWorkflowId implements Serializable {

        @NonNull
        private String workflowUuid;
    }
}