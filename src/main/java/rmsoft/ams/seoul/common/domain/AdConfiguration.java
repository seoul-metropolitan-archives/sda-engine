package rmsoft.ams.seoul.common.domain;

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
@IdClass(AdConfiguration.AdConfigurationId.class)
@Alias("Ad001")
@Table(name = "AD_CONFIGURATION")
public class AdConfiguration  extends BaseJpaModel<AdConfiguration.AdConfigurationId>
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
    public AdConfigurationId getId() {
        return AdConfigurationId.of(configurationUUID);
    }

    @Embeddable
    @Data
    @NoArgsConstructor
    @RequiredArgsConstructor(staticName = "of")
    public static class AdConfigurationId implements Serializable {
        @NonNull
        private String configurationUUID;
    }

}
