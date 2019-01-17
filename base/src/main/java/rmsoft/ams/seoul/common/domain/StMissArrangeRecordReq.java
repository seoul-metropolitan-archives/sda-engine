package rmsoft.ams.seoul.common.domain;

import io.onsemiro.core.annotations.Comment;
import io.onsemiro.core.domain.BaseJpaModel;
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
@Table(name = "ST_MISS_ARRANGE_RECORD_REQ")
@IdClass(StMissArrangeRecordReq.StMissArrangeRecordReqId.class)
@Alias("StMissArrangeRecordReq")
public class StMissArrangeRecordReq extends SimpleJpaModel<StMissArrangeRecordReq.StMissArrangeRecordReqId> {


    @Id
    @Column(name = "MISS_ARRANGE_RECORD_UUID", length = 36, nullable = false)
    private String missArrangeRecordUuid;

    @Column(name = "AGGREGATION_UUID", length = 36, nullable = false)
    private String aggregationUuid;

    @Column(name = "CONTAINER_UUID", length = 36, nullable = false)
    private String containerUuid;

    @Column(name = "SOURCE_TYPE_UUID", length = 36, nullable = false)
    private String sourceTypeUuid;

    @Column(name = "REQUEST_DATE", length = 6, nullable = false)
    private String requestDate;

    @Column(name = "REPUBLISH_YN", length = 1, nullable = false)
    private String republishYn;

    @Column(name = "REPUBLISH_DATE", length = 6, nullable = false)
    private String republishDate;

    @Column(name = "CURRENT_CONTAINER_UUID", length = 36, nullable = false)
    private String currentContainerUuid;

    @Override
    public StMissArrangeRecordReqId getId() {
        return StMissArrangeRecordReqId.of(missArrangeRecordUuid);
    }


    @Embeddable
    @Data
    @NoArgsConstructor
    @RequiredArgsConstructor(staticName = "of")
    public static class StMissArrangeRecordReqId implements Serializable {
        @NonNull
        private String missArrangeRecordUuid;
    }
}
