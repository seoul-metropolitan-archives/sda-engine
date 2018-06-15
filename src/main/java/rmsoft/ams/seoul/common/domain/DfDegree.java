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
 * The type Ad entity type.
 */
@Setter
@Getter
@DynamicInsert
@DynamicUpdate
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "DF_DISPOSAL_FREEZE_DEGREE")
@IdClass(DfDegree.DfDegreeId.class)
@Alias("dfDegree")
public class DfDegree extends BaseJpaModel<DfDegree.DfDegreeId> {

    @Id
    @Column(name = "DISPOSAL_FREEZE_DEGREE_UUID", length = 36, nullable = false)
    private String disposalFreezeDegreeUuid;

    @Column(name = "DISPOSAL_FREEZE_EVENT_UUID", length = 36, nullable = false)
    private String disposalFreezeEventUuid;

    @Column(name = "FREEZE_YN", length = 3, nullable = false)
    private String freezeYN;

    @Column(name = "DEGREE", length = 4, nullable = false)
    private int degree;

    @Column(name = "END_YN", length = 1, nullable = false)
    private String endYn;

    @Column(name = "TERMINATOR_UUID", length = 36)
    private String terminatorUuid;

    @Column(name = "END_DATE")
    private Timestamp endDate;

    @Override
    public DfDegreeId getId() { return DfDegreeId.of(disposalFreezeDegreeUuid); }

    /**
     * The type Ad entity type id.
     */
    @Embeddable
    @Data
    @NoArgsConstructor
    @RequiredArgsConstructor(staticName = "of")
    public static class DfDegreeId implements Serializable {

        @NonNull
        private String disposalFreezeDegreeUuid;
    }
}