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
@Table(name = "ST_RFID_TAG")
@IdClass(StRfidTag.StRfidTagId.class)
@Alias("StRfidTag")
public class StRfidTag extends SimpleJpaModel<StRfidTag.StRfidTagId> {


    @Id
    @Column(name = "RFID_MACHINE_UUID", length = 36, nullable = false)
    private String rfidTagUuid;

    @Column(name = "AGGREGATION_UUID", length = 36, nullable = false)
    private String aggregationUuid;


    @Column(name = "SEQ", length = 30, nullable = false)
    private int seq;

    @Column(name = "PUBLISH_SOURCE_TYPE_UUID", length = 36, nullable = false)
    private String publishSourceTypeUuid;

    @Column(name = "PUBLISH_STATUS_UUID", length = 36, nullable = false)
    private String publishStatusUuid;


    @Column(name = "PUBLISH_DATE", length = 6, nullable = false)
    private Timestamp publishDate;

    @Column(name = "TAG", length = 100, nullable = false)
    private String tag;


    @Override
    public StRfidTagId getId() {
        return StRfidTagId.of(rfidTagUuid);
    }

    @Embeddable
    @Data
    @NoArgsConstructor
    @RequiredArgsConstructor(staticName = "of")
    public static class StRfidTagId implements Serializable {
        @NonNull
        private String rfidTagUuid;
    }
}
