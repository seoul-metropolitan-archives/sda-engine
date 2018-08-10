package rmsoft.ams.seoul.ac.ac012.vo;

import io.onsemiro.core.annotations.Comment;
import io.onsemiro.core.domain.SimpleJpaModel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Setter
@Getter
@DynamicInsert
@DynamicUpdate
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "AC_ACCESS_CONTROL")
@Comment("")
@Alias("ac012")
public class Ac012 extends SimpleJpaModel<String> {

	@Id
	@Column(name = "ACCESS_CONTROL_UUID", length = 36, nullable = false)
	private String accessControlUuid;

	@Column(name = "USER_UUID", length = 36)
	private String userUuid;

	@Column(name = "USER_GROUP_UUID", length = 36)
	private String userGroupUuid;

	@Column(name = "ROLE_UUID", length = 36, nullable = false)
	private String roleUuid;

	@Column(name = "DESCRIPTION", length = 4000)
	private String description;

	@Column(name = "NOTES", length = 4000)
	private String notes;

	@Column(name = "USE_YN", length = 1, nullable = false)
	private String useYn;

	@Column(name = "INSERT_UUID", length = 36, nullable = false)
	private String insertUuid;

	@Column(name = "INSERT_DATE", nullable = false)
	private Timestamp insertDate;

	@Column(name = "UPDATE_UUID", length = 36, nullable = false)
	private String updateUuid;

	@Column(name = "UPDATE_DATE", nullable = false)
	private Timestamp updateDate;


    @Override
    public String getId() {
        return accessControlUuid;
    }
}