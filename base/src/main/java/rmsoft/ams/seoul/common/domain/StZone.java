package rmsoft.ams.seoul.common.domain;

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
@Table(name = "ST_ZONE")
@IdClass(StZone.StZoneId.class)
@Alias("StZone")
public class StZone extends SimpleJpaModel<StZone.StZoneId> {

    @Id
    @Column(name = "ZONE_UUID", length = 36, nullable = false)
    private String zoneUuid;

    /*@Column(name = "NO", length = 4, nullable = false)
    private int no;*/

    @Column(name = "ZONE_ID", length = 36, nullable = false)
    private String zoneId;

    @Column(name = "ZONE_NAME", length = 50, nullable = false)
    private String zoneName;


    @Override
    public StZoneId getId() {
        return StZoneId.of(zoneUuid);
    }

    @Embeddable
    @Data
    @NoArgsConstructor
    @RequiredArgsConstructor(staticName = "of")
    public static class StZoneId implements Serializable {
        @NonNull
        private String zoneUuid;
    }

}
