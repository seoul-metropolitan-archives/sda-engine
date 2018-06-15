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
@Table(name = "RS_GENERAL_RECORD_SCHEDULE")
@IdClass(RsGeneralRecordSchedule.RsGeneralRecordScheduleId.class)
@Alias("RsGeneralRecordSchedule")
public class RsGeneralRecordSchedule extends BaseJpaModel<RsGeneralRecordSchedule.RsGeneralRecordScheduleId> {

    @Id
    @Column(name = "GENERAL_RECORD_SCHEDULE_UUID", length = 36, nullable = false)
    private String generalRecordScheduleUuid;

    @Column(name = "STATUS_UUID", length = 36, nullable = false)
    private String statusUuid;

    @Column(name = "GRS_CODE", length = 30, nullable = false)
    private String grsCode;

    @Column(name = "GRS_NAME", length = 50, nullable = false)
    private String grsName;

    @Column(name = "RETENTION_PERIOD_UUID", length = 36, nullable = false)
    private String retentionPeriodUuid;

    @Column(name = "DISPOSAL_TYPE_UUID", length = 36, nullable = false)
    private String disposalTypeUuid;

    @Column(name = "BASED_ON", length = 1000, nullable = false)
    private String basedOn;

    @Column(name = "TRIGGER_YN", length = 1, nullable = false)
    private String triggerYn;

    @Column(name = "USE_YN", length = 1, nullable = false)
    private String useYn;


    @Override
    public RsGeneralRecordScheduleId getId() {
        return RsGeneralRecordScheduleId.of(generalRecordScheduleUuid);
    }

    /**
     * The type Cl classification scheme id.
     */
    @Embeddable
    @Data
    @NoArgsConstructor
    @RequiredArgsConstructor(staticName = "of")
    public static class RsGeneralRecordScheduleId implements Serializable {
        @NonNull
        private String generalRecordScheduleUuid;
    }
}