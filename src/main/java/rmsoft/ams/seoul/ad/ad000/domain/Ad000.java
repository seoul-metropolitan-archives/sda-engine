package rmsoft.ams.seoul.ad.ad000.domain;

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
@Table(name = "AD_SERVICE")
@IdClass(Ad000.Ad000Id.class)
@Alias("Ad000")
public class Ad000 extends BaseJpaModel<Ad000.Ad000Id> {

    @Id
    @Column(name = "SERVICE_UUID")
    @Comment(value = "서비스UUID")
    private String service_uuid;

    @Column(name="SERVICE_CODE")
    @Comment(value = "서비스 코드")
    private String service_code;

    @Column(name="SERVICE_NAME")
    @Comment(value = "서비스 명")
    private String service_name;

    @Comment(value = "설치여부")
    @Column(name="INSTALL_YN")
    private String install_yn;



    @Override
    public Ad000Id getId() {
        return Ad000Id.of(service_uuid);
    }

    @Embeddable
    @Data
    @NoArgsConstructor
    @RequiredArgsConstructor(staticName = "of")
    public static class Ad000Id implements Serializable {

        @NonNull
        private String service_uuid;
    }

}
