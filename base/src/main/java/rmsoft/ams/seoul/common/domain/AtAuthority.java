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
 * The type Cl class.
 */
@Setter
@Getter
@DynamicInsert
@DynamicUpdate
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "AT_AUTHORITY")
@IdClass(AtAuthority.AtAuthorityId.class)
@Alias("AtAuthority")
public class AtAuthority extends BaseJpaModel<AtAuthority.AtAuthorityId> {

    @Id
    @Column(name = "AUTHORITY_UUID", length = 36, nullable = false)
    private String authorityUuid;

    @Column(name = "AUTHORITY_TYPE_UUID", length = 36)
    private String authorityTypeUuid;

    @Column(name = "AUTHORITY_NO", length = 30, nullable = false)
    private String authorityNo;

    @Column(name = "AUTHORITY_NAME", length = 100)
    private String authorityName;

    @Column(name = "ORG_TYPE_UUID", length = 36)
    private String orgTypeUuid;

    @Column(name = "START_DATE", length = 6)
    private String startDate;

    @Column(name = "END_DATE", length = 6)
    private String endDate;

    @Column(name = "MAIN_JOB",length = 4000)
    private String mainJob;

    @Column(name = "DESCRIPTOR_UUID", length = 4000)
    private String descriptorUuid;

    @Column(name = "DESCRIPTION_DATE", length = 4000)
    private Timestamp descriptionDate;

    @Column(name = "LEVEL_OF_DETAIL_UUID", length = 36)
    private String levelOfDetailUuid;

    @Column(name = "USE_YN", length = 1)
    private String useYn;

    @Override
    public AtAuthorityId getId() { return AtAuthorityId.of(authorityUuid); }

    /**
     * The type Cl class id.
     */
    @Embeddable
    @Data
    @NoArgsConstructor
    @RequiredArgsConstructor(staticName = "of")
    public static class AtAuthorityId implements Serializable {
        @NonNull
        private String authorityUuid;
    }
}
