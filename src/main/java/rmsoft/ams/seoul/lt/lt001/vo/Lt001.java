package rmsoft.ams.seoul.lt.lt001.vo;

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
@Table(name = "LT_FILE_FORMAT")
@Comment("")
@Alias("lt001")
public class Lt001 extends SimpleJpaModel<String> {

	@Id
	@Column(name = "FILE_FORMAT_UUID", length = 36, nullable = false)
	private String fileFormatUuid;

	@Column(name = "PUID", length = 30, nullable = false)
	private String puid;

	@Column(name = "FORMAT_NAME", length = 500, nullable = false)
	private String formatName;

	@Column(name = "FORMAT_VERSION", length = 50)
	private String formatVersion;

	@Column(name = "FORMAT_RISK", length = 50)
	private String formatRisk;

	@Column(name = "EXTENSION", length = 50)
	private String extension;

	@Column(name = "FORMAT_GROUP_UUID", length = 36)
	private String formatGroupUuid;

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
        return fileFormatUuid;
    }
}