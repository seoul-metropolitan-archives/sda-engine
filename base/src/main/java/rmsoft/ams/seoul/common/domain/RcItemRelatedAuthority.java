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
@Table(name = "RC_ITEM_RELATED_AUTHORITY")
@IdClass(RcItemRelatedAuthority.RcItemRelatedAuthorityId.class)
@Alias("RcItemRelatedAuthority")
public class RcItemRelatedAuthority extends BaseJpaModel<RcItemRelatedAuthority.RcItemRelatedAuthorityId> {


    @Id
    @Column(name = "ITEM_RELATED_AUTHORITY_UUID", length = 36, nullable = false)
    @Comment(value = "ITEM_RELATED_AUTHORITY_UUID")
    private String itemRelatedAuthorityUuid;

    @Column(name = "ITEM_UUID", length = 36, nullable = false)
    @Comment(value = "ITEM_UUID")
    private String itemUuid;

    @Column(name = "AUTHORITY_UUID", length = 36, nullable = false)
    @Comment(value = "")
    private String authorityUuid;


    @Override
    public RcItemRelatedAuthority.RcItemRelatedAuthorityId getId() { return RcItemRelatedAuthority.RcItemRelatedAuthorityId.of(itemRelatedAuthorityUuid); }

    /**
     * The type Rc aggregation id.
     */
    @Embeddable
    @Data
    @NoArgsConstructor
    @RequiredArgsConstructor(staticName = "of")
    public static class RcItemRelatedAuthorityId implements Serializable {
        @NonNull
        private String itemRelatedAuthorityUuid;
    }
}
