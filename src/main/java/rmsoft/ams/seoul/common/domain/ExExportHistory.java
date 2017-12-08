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
 * The type Ex export history.
 */
@Setter
@Getter
@DynamicInsert
@DynamicUpdate
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "EX_EXPORT_HISTORY")
@IdClass(ExExportHistory.ExExportHistoryId.class)
@Alias("ExExportHistory")
public class ExExportHistory extends BaseJpaModel<ExExportHistory.ExExportHistoryId> {

    @Id
    @Column(name = "EXPORT_HISTORY_UUID", length = 36, nullable = false)
    @Comment(value = "Export History UUID")
    private String exportHistoryUuid;

    @Column(name = "ENTITY_TYPE_UUID", length = 36, nullable = false)
    @Comment(value = "Entity Type UUID")
    private String entityTypeUuid;

    @Column(name = "KEY_UUID", length = 36, nullable = false)
    @Comment(value = "Key UUID")
    private String keyUuid;

    @Column(name = "EXPORT_TYPE_UUID", length = 36, nullable = false)
    @Comment(value = "Export Type UUID")
    private String exportTypeUuid;

    @Column(name = "EXPORT_YN", nullable = false)
    @Comment(value = "Export")
    private String exportYn;

    @Column(name = "EXPORT_DATE", length = 36)
    @Comment(value = "Export Date")
    private Timestamp exportDate;

    @Override
    public ExExportHistoryId getId() {
        return ExExportHistoryId.of(exportHistoryUuid);
    }

    /**
     * The type Ex export history id.
     */
    @Embeddable
    @Data
    @NoArgsConstructor
    @RequiredArgsConstructor(staticName = "of")
    public static class ExExportHistoryId implements Serializable {

        @NonNull
        private String exportHistoryUuid;
    }
}