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
 * The type Ad entity type.
 */
@Setter
@Getter
@DynamicInsert
@DynamicUpdate
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "DF_DISPOSAL_FREEZE_EVENT")
@IdClass(DfEvent.DfEventId.class)
@Alias("dfEvent")
public class DfEvent extends BaseJpaModel<DfEvent.DfEventId> {

    @Id
    @Column(name = "DISPOSAL_FREEZE_EVENT_UUID", length = 36, nullable = false)
    private String disposalFreezeEventUuid;

    @Column(name = "STATUS_UUID", length = 36, nullable = false)
    private String statusUuid;

    @Column(name = "EVENT_CODE", length = 30, nullable = false)
    private String eventCode;

    @Column(name = "EVENT_NAME", length = 50, nullable = false)
    private String eventName;

    @Column(name = "EVENT_TYPE_UUID", length = 36)
    private String eventTypeUuid;

    @Column(name = "REVIEW_DATE")
    private Timestamp reviewDate;

    @Column(name = "END_YN", length = 1, nullable = false)
    private String endYn;

    @Column(name = "TERMINATOR_UUID", length = 36)
    private String terminatorUuid;

    @Column(name = "END_DATE")
    private Timestamp endDate;

    @Column(name = "REASON", length = 4000)
    private String reason;

    @Override
    public DfEventId getId() { return DfEventId.of(disposalFreezeEventUuid); }

    /**
     * The type Ad entity type id.
     */
    @Embeddable
    @Data
    @NoArgsConstructor
    @RequiredArgsConstructor(staticName = "of")
    public static class DfEventId implements Serializable {

        @NonNull
        private String disposalFreezeEventUuid;
    }
}