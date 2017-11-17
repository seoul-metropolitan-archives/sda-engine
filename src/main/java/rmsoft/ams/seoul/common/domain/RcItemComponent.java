package rmsoft.ams.seoul.common.domain;

import io.onsemiro.core.annotations.Comment;
import io.onsemiro.core.domain.SimpleJpaModel;
import lombok.*;
import org.apache.ibatis.type.Alias;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

@Setter
@Getter
@DynamicInsert
@DynamicUpdate
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "RC_ITEM_COMPONENT")
@IdClass(RcItemComponent.RcItemComponentId.class)
@Alias("RcItemComponent")

public class RcItemComponent extends SimpleJpaModel<RcItemComponent.RcItemComponentId> {

    @Id
    @Column(name = "ITEM_COMPONENT_UUID", length = 36, nullable = false)
    @Comment(value = "ITEM COMPONENT UUID")
    private String itemComponentUuid;

    @Column(name = "ITEM_UUID", length = 36, nullable = true)
    @Comment(value = "OPEN STATUS UUID")
    private String openStatusUuid;

    @Column(name = "COMPONENT_UUID", length = 36, nullable = true)
    @Comment(value = "KEYWORD")
    private String keyword;

    @Override
    public RcItemComponentId getId() { return RcItemComponentId.of(itemComponentUuid); }

    @Embeddable
    @Data
    @NoArgsConstructor
    @RequiredArgsConstructor(staticName = "of")
    public static class RcItemComponentId implements Serializable {
        @NonNull
        private String itemComponentUuid;
    }
}
