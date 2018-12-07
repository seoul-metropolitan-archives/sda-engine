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
 * The type RC_AGGR_RELATED_AUTHORITY.
 */
@Setter
@Getter
@DynamicInsert
@DynamicUpdate
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "RC_AGGR_RELATED_AUTHORITY")
@IdClass(RcAggrRelatedAuthority.RcAggrRelatedAuthorityId.class)
@Alias("RcAggrRelatedAuthority")
public class RcAggrRelatedAuthority extends BaseJpaModel<RcAggrRelatedAuthority.RcAggrRelatedAuthorityId> {


    @Id
    @Column(name = "AGGR_RELATED_AUTHORITY_UUID", length = 36, nullable = false)
    @Comment(value = "AGGR_RELATED_AUTHORITY_UUID")
    private String aggrRelatedAuthorityUuid;

    @Column(name = "AGGREGATION_UUID", length = 36, nullable = false)
    @Comment(value = "AGGREGATION UUID")
    private String aggregationUuid;

    @Column(name = "AUTHORITY_UUID", length = 36, nullable = false)
    @Comment(value = "")
    private String authorityUuid;


    @Override
    public RcAggrRelatedAuthority.RcAggrRelatedAuthorityId getId() { return RcAggrRelatedAuthority.RcAggrRelatedAuthorityId.of(aggrRelatedAuthorityUuid); }

    /**
     * The type Rc aggregation id.
     */
    @Embeddable
    @Data
    @NoArgsConstructor
    @RequiredArgsConstructor(staticName = "of")
    public static class RcAggrRelatedAuthorityId implements Serializable {
        @NonNull
        private String aggrRelatedAuthorityUuid;
    }
}
