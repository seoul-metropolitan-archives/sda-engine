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
 * The type Rc aggregation.
 */
@Setter
@Getter
@DynamicInsert
@DynamicUpdate
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "RC_AGGREGATION")
@IdClass(RcAggregation.RcAggregationId.class)
@Alias("RcAggregation")
public class RcAggregation extends BaseJpaModel<RcAggregation.RcAggregationId> {


    @Id
    @Column(name = "AGGREGATION_UUID", length = 36, nullable = false)
    @Comment(value = "AGGREGATION UUID")
    private String aggregationUuid;
    @Column(name = "PUBLISHED_STATUS_UUID", length = 36, nullable = true)
    @Comment(value = "")
    private String publishedStatusUuid;
    @Column(name = "AGGREGATION_CODE", length = 30, nullable = true)
    @Comment(value = "AGGREGATION 코드")
    private String aggregationCode;
    @Column(name = "TITLE", length = 500, nullable = true)
    @Comment(value = "제목")
    private String title;
    @Column(name = "TYPE_UUID", length = 36, nullable = true)
    @Comment(value = "유형 UUID")
    private String typeUuid;
    @Column(name = "PARENT_AGGREGATION_UUID", length = 36, nullable = true)
    @Comment(value = "부모 AGGREGATION_UUID")
    private String parentsAggregationUuid;
    @Column(name = "LEVEL_UUID", length = 36, nullable = true)
    @Comment(value = "계층 UUID")
    private String levelUuid;
    @Column(name = "AUTHOR", length = 500, nullable = true)
    @Comment(value = "저자")
    private String author;
    @Column(name = "DESCRIPTION_START_DATE", length = 8, nullable = true)
    @Comment(value = "")
    private String descriptionStartDate;
    @Column(name = "DESCRIPTION_END_DATE", length = 8, nullable = true)
    @Comment(value = "")
    private String descriptionEndDate;



    @Override
    public RcAggregation.RcAggregationId getId() { return RcAggregation.RcAggregationId.of(aggregationUuid); }

    /**
     * The type Rc aggregation id.
     */
    @Embeddable
    @Data
    @NoArgsConstructor
    @RequiredArgsConstructor(staticName = "of")
    public static class RcAggregationId implements Serializable {
        @NonNull
        private String aggregationUuid;
    }
}
