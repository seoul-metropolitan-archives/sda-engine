package rmsoft.ams.seoul.common.domain;

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
@Table(name = "ST_TAKEOUT_RECORD_RESULT")
@IdClass(StTakeoutRecordResult.StTakeoutRecordResultId.class)
@Alias("StTakeoutRecordResult")
public class StTakeoutRecordResult extends SimpleJpaModel<StTakeoutRecordResult.StTakeoutRecordResultId> {

    @Id
    @Column(name = "TAKEOUT_RECORD_RESULT_UUID", length = 36, nullable = false)
    private String takeoutRecordResultUuid;

    @Column(name = "TAKEOUT_REQUEST_UUID", length = 36, nullable = false)
    private String takeoutRequestUuid;

    @Column(name = "AGGREGATION_UUID", length = 500, nullable = false)
    private String aggregationUuid;


    @Override
    public StTakeoutRecordResultId getId() { return StTakeoutRecordResultId.of(takeoutRecordResultUuid); }

    @Embeddable
    @Data
    @NoArgsConstructor
    @RequiredArgsConstructor(staticName = "of")
    public static class StTakeoutRecordResultId implements Serializable {
        @NonNull
        private String takeoutRecordResultUuid;
    }
}
