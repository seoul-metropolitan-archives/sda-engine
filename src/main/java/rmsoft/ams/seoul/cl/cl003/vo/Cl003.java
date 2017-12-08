package rmsoft.ams.seoul.cl.cl003.vo;

import io.onsemiro.core.annotations.Comment;
import io.onsemiro.core.domain.BaseJpaModel;
import lombok.*;
import org.apache.ibatis.type.Alias;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

/**
 * The type Cl 003.
 */
@Setter
@Getter
@DynamicInsert
@DynamicUpdate
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "AD_SERVICE")
@IdClass(Cl003.Cl003Id.class)
@Alias("Cl003")
public class Cl003 extends BaseJpaModel<Cl003.Cl003Id> {

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
    public Cl003Id getId() {
        return Cl003Id.of(service_uuid);
    }

    /**
     * The type Cl 003 id.
     */
    @Embeddable
    @Data
    @NoArgsConstructor
    @RequiredArgsConstructor(staticName = "of")
    public static class Cl003Id implements Serializable {

        @NonNull
        private String service_uuid;
    }

}
