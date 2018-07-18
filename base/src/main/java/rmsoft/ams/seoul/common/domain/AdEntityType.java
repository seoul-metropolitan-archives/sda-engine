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
 * The type Ad entity type.
 */
@Setter
@Getter
@DynamicInsert
@DynamicUpdate
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "AD_ENTITY_TYPE")
@IdClass(AdEntityType.AdEntityTypeId.class)
@Alias("AdEntityType")
public class AdEntityType extends BaseJpaModel<AdEntityType.AdEntityTypeId> {

    @Id
    @Column(name = "ENTITY_TYPE_UUID", length = 36, nullable = false)
    @Comment(value = "엔티티타입UUID")
    private String entityTypeUuid;

    @Column(name = "ENTITY_TYPE_CODE", length = 30, nullable = false)
    @Comment(value = "엔티티타입CODE")
    private String entityType;

    @Column(name = "ENTITY_TYPE_NAME", length = 50, nullable = false)
    @Comment(value = "엔티티타입NAME")
    private String entityName;

    @Column(name = "USE_YN", length = 1, nullable = false)
    @Comment(value = "사용여부")
    private String useYN;

    @Column(name = "AUDIT_YN", length = 1, nullable = false)
    @Comment(value = "AUDIT_YN")
    private String auditYN;

    @Override
    public AdEntityTypeId getId() {
        return AdEntityTypeId.of(entityTypeUuid);
    }

    /**
     * The type Ad entity type id.
     */
    @Embeddable
    @Data
    @NoArgsConstructor
    @RequiredArgsConstructor(staticName = "of")
    public static class AdEntityTypeId implements Serializable {

        @NonNull
        private String entityTypeUuid;
    }
}