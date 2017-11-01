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
@Table(name = "WF_JOB")
@IdClass(WfJob.WfJobId.class)
@Alias("WfJob")
public class WfJob extends BaseJpaModel<WfJob.WfJobId> {

    @Id
    @Column(name = "JOB_UUID", length = 36, nullable = false)
    @Comment(value = "Job UUID")
    private String jobUuid;

    @Column(name = "JOB_NAME", length = 50, nullable = false)
    @Comment(value = "Job Name")
    private String jobName;

    @Column(name = "API", length = 500, nullable = false)
    @Comment(value = "API")
    private String api;

    @Column(name = "USE_YN", length = 1, nullable = false)
    @Comment(value = "사용여부")
    private String useYn;

    @Override
    public WfJobId getId() {
        return WfJobId.of(jobUuid);
    }

    @Embeddable
    @Data
    @NoArgsConstructor
    @RequiredArgsConstructor(staticName = "of")
    public static class WfJobId implements Serializable {

        @NonNull
        private String jobUuid;
    }
}