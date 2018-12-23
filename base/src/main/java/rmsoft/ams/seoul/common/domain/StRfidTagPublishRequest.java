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
@Table(name = "ST_RFID_TAG_PUBLISH_REQUEST")
@IdClass(StRfidTagPublishRequest.StRfidTagPublishRequestId.class)
@Alias("stRfidTagPublishRequest")
public class StRfidTagPublishRequest extends SimpleJpaModel<StRfidTagPublishRequest.StRfidTagPublishRequestId> {


    @Id
    @Column(name = "RFID_TAG_REPUBLISH_UUID", length = 36, nullable = false)
    private String rfidTagRepublishUuid;

    @Column(name = "AGGREGATION_UUID", length = 36, nullable = false)
    private String aggregationUuid;

    @Column(name = "PUBLISH_SOURCE_TYPE_UUID", length = 36, nullable = false)
    private String publishSourceTypeUuid;

    @Column(name = "REPUBLISH_YN", length = 1, nullable = false)
    private String republishYn;

    @Column(name = "REPUBLISH_DATE", length = 6, nullable = false)
    private Timestamp republishDate;


    @Override
    public StRfidTagPublishRequestId getId() {
        return StRfidTagPublishRequestId.of(rfidTagRepublishUuid);
    }

    @Embeddable
    @Data
    @NoArgsConstructor
    @RequiredArgsConstructor(staticName = "of")
    public static class StRfidTagPublishRequestId implements Serializable {
        @NonNull
        private String rfidTagRepublishUuid;
    }
}
