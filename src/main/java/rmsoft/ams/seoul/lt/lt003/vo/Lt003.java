package rmsoft.ams.seoul.lt.lt003.vo;

import io.onsemiro.core.annotations.ColumnPosition;
import io.onsemiro.core.domain.SimpleJpaModel;
import io.onsemiro.core.annotations.Comment;
import lombok.*;
import org.apache.ibatis.type.Alias;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import javax.persistence.*;
import java.sql.Timestamp;

@Setter
@Getter
@DynamicInsert
@DynamicUpdate
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "LT_TOOL")
@Comment("")
@Alias("lt003")
public class Lt003 extends SimpleJpaModel<String> {

	@Id
	@Column(name = "TOOL_UUID", length = 36, nullable = false)
	private String toolUuid;

	@Column(name = "TOOL_NAME", length = 500, nullable = false)
	private String toolName;

	@Column(name = "API", length = 500, nullable = false)
	private String api;

	@Column(name = "SOFWARE_UUID", length = 36, nullable = false)
	private String sofwareUuid;

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
        return toolUuid;
    }
}