package rmsoft.ams.seoul.common.domain;

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
@Table(name = "ST_EXCEPT_RECORD_RESULT")
@IdClass(StExceptRecordResult.StExceptRecordResultId.class)
@Alias("StExceptRecordResult")
public class StExceptRecordResult extends BaseJpaModel<StExceptRecordResult.StExceptRecordResultId> {


    @Id
    @Column(name = "EXCEPT_RECORD_RESULT_UUID", length = 36, nullable = false)
    private String exceptRecordResultUuid;

    @Column(name = "INOUT_EXCEPT_UUID", length = 36, nullable = false)
    private String inoutExceptUuid;

    @Column(name = "AGGREGATION_UUID", length = 36, nullable = false)
    private String aggregationUuid;


    @Override
    public StExceptRecordResultId getId() {
        return StExceptRecordResultId.of(exceptRecordResultUuid);
    }

    @Embeddable
    @Data
    @NoArgsConstructor
    @RequiredArgsConstructor(staticName = "of")
    public static class StExceptRecordResultId implements Serializable {
        @NonNull
        private String exceptRecordResultUuid;
    }
}
