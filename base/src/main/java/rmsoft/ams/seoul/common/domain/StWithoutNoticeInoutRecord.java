package rmsoft.ams.seoul.common.domain;

import io.onsemiro.core.domain.SimpleJpaModel;
import lombok.*;
import org.apache.ibatis.type.Alias;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Setter
@Getter
@DynamicInsert
@DynamicUpdate
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "ST_WITHOUT_NOTICE_INOUT_RECORD")
@IdClass(StWithoutNoticeInoutRecord.StWithoutNoticeInoutRecordId.class)
@Alias("StWithoutNoticeInoutRecord")
public class StWithoutNoticeInoutRecord extends SimpleJpaModel<StWithoutNoticeInoutRecord.StWithoutNoticeInoutRecordId> {

    @Id
    @Column(name = "WITHOUT_NOTICE_IO_RECORD_UUID", length = 36, nullable = false)
    private String withoutNoticeIoRecordUuid;

    @Column(name = "AGGREGATION_UUID", length = 36, nullable = false)
    private String aggregationUuid;

    @Column(name = "DISPOSER_UUID", length = 36, nullable = false)
    private String disposerUuid;

    @Column(name = "DISPOSAL_DATE", nullable = false)
    private Timestamp disposalDate;

    @Column(name = "REASON", length = 1000, nullable = false)
    private String reason;





    @Override
    public StWithoutNoticeInoutRecordId getId() {return StWithoutNoticeInoutRecordId.of(withoutNoticeIoRecordUuid);}

    @Embeddable
    @Data
    @NoArgsConstructor
    @RequiredArgsConstructor(staticName = "of")
    public static class StWithoutNoticeInoutRecordId implements Serializable {
        @NonNull
        private String withoutNoticeIoRecordUuid;
    }
}
