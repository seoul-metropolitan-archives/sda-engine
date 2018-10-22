/*
 * Copyright (c) 2017. RMSoft Co.,Ltd. All rights reserved
 */

package rmsoft.ams.seoul.common.domain;

import io.onsemiro.core.domain.SimpleJpaModel;
import lombok.*;
import org.apache.ibatis.type.Alias;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

/**
 * The type Cl class.
 */
@Setter
@Getter
@DynamicInsert
@DynamicUpdate
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "AT_AUTHORITY_RELATION")
@IdClass(AtAuthorityRelation.AtAuthorityRelationId.class)
@Alias("AtAuthorityRelation")
public class AtAuthorityRelation extends SimpleJpaModel<AtAuthorityRelation.AtAuthorityRelationId> {

    @Id
    @Column(name = "AUTHORITY_RELATION_UUID", length = 36, nullable = false)
    private String authorityRelationUuid;

    @Column(name = "AUTHORITY_UUID", length = 36, nullable = false)
    private String authorityUuid;

    @Column(name = "RELATION_TYPE_UUID", length = 36)
    private String relationTypeUuid;

    @Column(name = "REL_AUTHORITY_UUID", length = 36)
    private String relAuthorityUuid;



    @Override
    public AtAuthorityRelationId getId() { return AtAuthorityRelationId.of(authorityRelationUuid); }

    /**
     * The type Cl class id.
     */
    @Embeddable
    @Data
    @NoArgsConstructor
    @RequiredArgsConstructor(staticName = "of")
    public static class AtAuthorityRelationId implements Serializable {
        @NonNull
        private String authorityRelationUuid;
    }
}
