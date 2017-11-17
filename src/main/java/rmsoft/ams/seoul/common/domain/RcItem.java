package rmsoft.ams.seoul.common.domain;

import io.onsemiro.core.annotations.Comment;
import io.onsemiro.core.domain.BaseJpaModel;
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
@Table(name = "RC_ITEM")
@IdClass(RcItem.RcItemId.class)
@Alias("RcItem")

public class RcItem extends BaseJpaModel<RcItem.RcItemId> {

    @Id
    @Column(name = "ITEM_UUID", length = 36, nullable = false)
    @Comment(value = "ITEM UUID")
    private String itemUuid;

    @Column(name = "PUBLISHED_STATUS_UUID", length = 36, nullable = false)
    @Comment(value = "PUBLISHED STATUS UUID")
    private String publishedStatusUuid;

    @Column(name = "ITEM_CODE", length = 30, nullable = false)
    @Comment(value = "ITEM CODE")
    private String itemCode;

    @Column(name = "TITLE", length = 500, nullable = false)
    @Comment(value = "TITLE")
    private String title;

    @Column(name = "TYPE_UUID", length = 36, nullable = true)
    @Comment(value = "TYPE UUID")
    private String typeUuid;

    @Column(name = "AGGREGATION_UUID", length = 36, nullable = false)
    @Comment(value = "AGGREGATION UUID")
    private String aggregationUuid;

    @Column(name = "AUTHOR", length = 500, nullable = true)
    @Comment(value = "AUTHOR")
    private String author;

    @Column(name = "DESCRIPTION_START_DATE", length = 8, nullable = true)
    @Comment(value = "DESCRIPTION START DATE")
    private String descriptionStartDate;

    @Column(name = "DESCRIPTION_END_DATE", length = 8, nullable = true)
    @Comment(value = "DESCRIPTION END DATE")
    private String descriptionEndDate;


    @Override
    public RcItem.RcItemId getId() { return RcItem.RcItemId.of(itemUuid); }

    @Embeddable
    @Data
    @NoArgsConstructor
    @RequiredArgsConstructor(staticName = "of")
    public static class RcItemId implements Serializable {
        @NonNull
        private String itemUuid;
    }
}
