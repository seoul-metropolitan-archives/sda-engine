package rmsoft.ams.seoul.ad.ad001.domain;

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
@IdClass(Ad001.Ad001Id.class)
@Alias("Ad001")
@Table(name = "AD_CONFIGURATION")
public class Ad001  extends BaseJpaModel<Ad001.Ad001Id>
{
    @Id
    @Column(name="CONFIGURATION UUID")
    @Comment(value="설정 UUID")
    private String configurationUUID;

    @Column(name="CONFIGURATION_CODE")
    @Comment(value="설정 코드")
    private String configurationCode;

    @Column(name="CONFIGURATION_VALUE")
    @Comment(value="설정 값")
    private String configurationValue;

    @Column(name="SERVICE_UUID")
    @Comment(value="서비스 UUID")
    private String serviceUUID;

    @Override
    public Ad001Id getId() {
        return Ad001Id.of(configurationUUID);
    }

    @Embeddable
    @Data
    @NoArgsConstructor
    @RequiredArgsConstructor(staticName = "of")
    public static class Ad001Id implements Serializable {

        @NonNull
        private String configurationUUID;
    }

}
