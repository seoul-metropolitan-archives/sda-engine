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
 * The type Ad entity column.
 */
@Setter
@Getter
@DynamicInsert
@DynamicUpdate
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "AD_ENTITY_COLUMN")
@IdClass(AdEntityColumn.AdEntityColumnId.class)
@Alias("AdEntityColumn")
public class AdEntityColumn extends BaseJpaModel<AdEntityColumn.AdEntityColumnId> {

    @Id
    @Column(name = "ENTITY_COLUMN_UUID", length = 36, nullable = false)
    @Comment(value = "엔티티컬럼UUID")
    private String entityColumnUuid;

    @Column(name = "ENTITY_TYPE_UUID", length = 36, nullable = false)
    @Comment(value = "엔티티타입CODE")
    private String entityTypeUuid;

    @Column(name = "GLOSSARY_UUID", length = 36, nullable = false)
    @Comment(value = "Glossary UUID")
    private String glossaryUuid;

    @Column(name = "AUDIT_YN", length = 1, nullable = false)
    @Comment(value = "AUDIT_YN")
    private String auditYN;

    @Column(name = "USE_YN", length = 1, nullable = false)
    @Comment(value = "사용여부")
    private String useYN;


    @Override
    public AdEntityColumnId getId() {
        return AdEntityColumnId.of(entityColumnUuid);
    }

    /**
     * The type Ad entity column id.
     */
    @Embeddable
    @Data
    @NoArgsConstructor
    @RequiredArgsConstructor(staticName = "of")
    public static class AdEntityColumnId implements Serializable {

        @NonNull
        private String entityColumnUuid;
    }
}