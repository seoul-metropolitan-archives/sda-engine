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

/**
 * The type Cl classified records.
 */
@Setter
@Getter
@DynamicInsert
@DynamicUpdate
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "CL_CLASSIFIED_RECORDS")
@IdClass(ClClassifiedRecords.ClClassifiedRecordsId.class)
@Alias("ClClassifiedRecords")
public class ClClassifiedRecords extends BaseJpaModel<ClClassifiedRecords.ClClassifiedRecordsId> {

    @Id
    @Column(name = "CLASSIFIED_RECORDS_UUID", length = 36, nullable = false)
    @Comment(value = "분류기록UUID")
    private String classifiedRecordsUuid;

    @Column(name = "STATUS_UUID", length = 36, nullable = false)
    @Comment(value = "상태UUID")
    private String statusUuid;

    @Column(name = "CLASS_UUID", length = 36, nullable = false)
    @Comment(value = "분류UUID")
    private String classUuid;

    @Column(name = "AGGREGATION_UUID", length = 36)
    @Comment(value = "집합ID")
    private String aggregationId;

    @Column(name = "ITEM_UUID")
    @Comment(value = "아이템UUID")
    private String itemUuid;

    @Column(name = "CLASSIFIED_DATE")
    @Comment(value = "분류날짜")
    private String classifiedDate;

    @Override
    public ClClassifiedRecordsId getId() { return ClClassifiedRecordsId.of(classifiedRecordsUuid); }

    /**
     * The type Cl classified records id.
     */
    @Embeddable
    @Data
    @NoArgsConstructor
    @RequiredArgsConstructor(staticName = "of")
    public static class ClClassifiedRecordsId implements Serializable {
        @NonNull
        private String classifiedRecordsUuid;
    }
}
