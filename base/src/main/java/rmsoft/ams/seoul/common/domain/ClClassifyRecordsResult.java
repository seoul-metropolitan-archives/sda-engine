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
import java.sql.Timestamp;

/**
 * The type Cl classified records.
 */
@Setter
@Getter
@DynamicInsert
@DynamicUpdate
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "CL_CLASSIFY_RECORDS_RESULT")
@IdClass(ClClassifyRecordsResult.ClClassifyRecordsId.class)
@Alias("ClClassifyRecordsResult")
public class ClClassifyRecordsResult extends BaseJpaModel<ClClassifyRecordsResult.ClClassifyRecordsId> {

    @Id
    @Column(name = "CLASSIFY_RECORDS_UUID", length = 36, nullable = false)
    @Comment(value = "분류기록UUID")
    private String classifyRecordsUuid;

    @Column(name = "STATUS_UUID", length = 36, nullable = false)
    @Comment(value = "상태UUID")
    private String statusUuid;

    @Column(name = "CLASS_UUID", length = 36, nullable = false)
    @Comment(value = "분류UUID")
    private String classUuid;

    @Column(name = "AGGREGATION_UUID", length = 36)
    @Comment(value = "집합ID")
    private String aggregationUuid;

    @Column(name = "ITEM_UUID")
    @Comment(value = "아이템UUID")
    private String itemUuid;

    @Column(name = "CLASSIFIED_DATE")
    @Comment(value = "분류날짜")
    private Timestamp classifiedDate;

    @Column(name = "CHOICE_YN")
    @Comment(value = "선택구분")
    private String choiceYn;

    @Override
    public ClClassifyRecordsId getId() { return ClClassifyRecordsId.of(classifyRecordsUuid); }

    /**
     * The type Cl classified records id.
     */
    @Embeddable
    @Data
    @NoArgsConstructor
    @RequiredArgsConstructor(staticName = "of")
    public static class ClClassifyRecordsId implements Serializable {
        @NonNull
        private String classifyRecordsUuid;
    }
}
