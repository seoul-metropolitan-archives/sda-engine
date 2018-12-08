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
 * The type ITEM_CREATOR_UUID.
 */
@Setter
@Getter
@DynamicInsert
@DynamicUpdate
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "RC_ITEM_CREATOR")
@IdClass(RcItemCreator.RcItemCreatorId.class)
@Alias("RcItemCreator")
public class RcItemCreator extends BaseJpaModel<RcItemCreator.RcItemCreatorId> {


    @Id
    @Column(name = "ITEM_CREATOR_UUID", length = 36, nullable = false)
    @Comment(value = "ITEM_CREATOR_UUID")
    private String itemCreatorUuid;

    @Column(name = "ITEM_UUID", length = 36, nullable = false)
    @Comment(value = "ITEM_UUID")
    private String itemUuid;

    @Column(name = "CREATOR_UUID", length = 36, nullable = false)
    @Comment(value = "")
    private String creatorUuid;


    @Override
    public RcItemCreator.RcItemCreatorId getId() { return RcItemCreator.RcItemCreatorId.of(itemCreatorUuid); }

    /**
     * The type Rc aggregation id.
     */
    @Embeddable
    @Data
    @NoArgsConstructor
    @RequiredArgsConstructor(staticName = "of")
    public static class RcItemCreatorId implements Serializable {
        @NonNull
        private String itemCreatorUuid;
    }
}
