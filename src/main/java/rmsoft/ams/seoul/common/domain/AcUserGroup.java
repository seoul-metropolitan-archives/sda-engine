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

@Setter
@Getter
@DynamicInsert
@DynamicUpdate
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "AC_USER_GROUP")
@IdClass(AcUserGroup.AcUserGroupId.class)
@Alias("AcUserGroup")
public class AcUserGroup extends BaseJpaModel<AcUserGroup.AcUserGroupId> {

    @Id
    @Column(name = "USER_GROUP_UUID", length = 36, nullable = false)
    @Comment(value = "사용자그룹UUID")
    private String AcUserGroupUuid;

    @Column(name = "USER_GROUP_NAME", length = 50, nullable = false)
    @Comment(value = "유저그룹명")
    private String AcUserGroupName;

    @Column(name = "USE_YN", length = 1, nullable = false)
    @Comment(value = "사용여부")
    @Type(type = "labelEnum")
    private AXBootTypes.Used useYn = AXBootTypes.Used.YES;

    @Override
    public AcUserGroupId getId() {
        return AcUserGroupId.of(AcUserGroupUuid);
    }

    @Embeddable
    @Data
    @NoArgsConstructor
    @RequiredArgsConstructor(staticName = "of")
    public static class AcUserGroupId implements Serializable {

        @NonNull
        private String AcUserGroupUuid;
    }
}