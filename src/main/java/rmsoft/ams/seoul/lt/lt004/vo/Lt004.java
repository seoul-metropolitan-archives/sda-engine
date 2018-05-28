package rmsoft.ams.seoul.lt.lt004.vo;

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
@Table(name = "LT_PATHWAY")
@Comment("")
@Alias("lt004")
public class Lt004 extends SimpleJpaModel<String> {

	@Id
	@Column(name = "PATHWAY_UUID", length = 36, nullable = false)
	private String pathwayUuid;

	@Column(name = "PATHWAY_NAME", length = 500, nullable = false)
	private String pathwayName;

	@Column(name = "SOURCE_FILE_FORMAT_UUID", length = 36, nullable = false)
	private String sourceFileFormatUuid;

	@Column(name = "TARGET_FILE_FORMAT_UUID", length = 36, nullable = false)
	private String targetFileFormatUuid;

	@Column(name = "TOOL_UUID", length = 36, nullable = false)
	private String toolUuid;

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
        return pathwayUuid;
    }
}