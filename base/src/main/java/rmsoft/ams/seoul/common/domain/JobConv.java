/*
 * Copyright (c) 2017. RMSoft Co.,Ltd. All rights reserved
 */

package rmsoft.ams.seoul.common.domain;

import io.onsemiro.core.domain.BaseJpaModel;
import lombok.*;
import org.apache.ibatis.type.Alias;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * The type Ac access control.
 */
@Setter
@Getter
@DynamicInsert
@DynamicUpdate
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "JOB_CONV")
@IdClass(JobConv.JobConvId.class)
@Alias("JobConv")
public class JobConv extends BaseJpaModel<JobConv.JobConvId> {

    @Id
    @Column(name = "JOBID", length = 64, nullable = false)
    private String jobid;

    @Column(name = "SRCFILE", length = 512, nullable = false)
    private String srcfile;

    @Column(name = "DESTFILE", length = 512, nullable = false)
    private String destfile;

    @Column(name = "JOBSTATUS", length = 1)
    private String jobstatus;

    @Column(name = "REQDATE", length = 6)
    private Timestamp reqdate;

    @Override
    public JobConvId getId() {
        return JobConvId.of(jobid);
    }

    /**
     * The type Ac access control id.
     */
    @Embeddable
    @Data
    @NoArgsConstructor
    @RequiredArgsConstructor(staticName = "of")
    public static class JobConvId implements Serializable {

        @NonNull
        private String jobid;
    }
}