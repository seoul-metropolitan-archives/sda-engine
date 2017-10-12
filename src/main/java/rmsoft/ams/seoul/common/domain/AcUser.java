package rmsoft.ams.seoul.common.domain;

import io.onsemiro.core.annotations.Comment;
import io.onsemiro.core.code.AXBootTypes;
import io.onsemiro.core.domain.BaseJpaModel;
import lombok.*;
import org.apache.ibatis.type.Alias;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Setter
@Getter
@DynamicInsert
@DynamicUpdate
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "AC_USER")
@IdClass(AcUser.AcUserId.class)
@Alias("AcUser")
public class AcUser extends BaseJpaModel<AcUser.AcUserId> {

    @Id
    @Column(name = "USER_UUID", length = 36, nullable = false)
    @Comment(value = "사용자UUID")
    private String userUuid;

    @Column(name = "USER_ID", length = 20, nullable = false)
    @Comment(value = "아이디")
    private String userId;

    @Column(name = "USER_NAME", length = 50, nullable = false)
    @Comment(value = "사용자명")
    private String userNm;

    @Column(name = "PASSWORD", length = 300, nullable = false)
    @Comment(value = "비밀번호")
    private String userPassword;

    @Column(name = "PASSWORD_UPDATE_DATE", nullable = false)
    @Comment(value = "비밀번호변경일시")
    private Timestamp passwordUpdateDate;

    @Column(name = "USER_TYPE_UUID", length = 36)
    @Comment(value = "사용자유형UUID")
    private String userTypeUuid;

    @Column(name = "START_PROGRAM_UUID", length = 36)
    @Comment(value = "시작프로그램UUID")
    private String startProgramUuid;

    @Column(name = "ORGANIZATION_UUID", length = 36)
    @Comment(value = "소속기관UUID")
    private String organizationUuid;

    @Column(name = "USE_YN", length = 1, nullable = false)
    @Comment(value = "사용여부")
    @Type(type = "labelEnum")
    private AXBootTypes.Used useYn = AXBootTypes.Used.YES;

    @Override
    public AcUserId getId() {
        return AcUserId.of(userUuid);
    }

    @Embeddable
    @Data
    @NoArgsConstructor
    @RequiredArgsConstructor(staticName = "of")
    public static class AcUserId implements Serializable {

        @NonNull
        private String userUuid;
    }
}