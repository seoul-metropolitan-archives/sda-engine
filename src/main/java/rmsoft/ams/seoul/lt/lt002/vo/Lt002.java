package rmsoft.ams.seoul.lt.lt002.vo;

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
@Table(name = "LT_SOFTWARE")
@Comment("")
@Alias("lt002")
public class Lt002 extends SimpleJpaModel<String> {

	@Id
	@Column(name = "SOFTWARE_UUID", length = 36, nullable = false)
	private String softwareUuid;

	@Column(name = "SOFTWARE_NAME", length = 500, nullable = false)
	private String softwareName;

	@Column(name = "SOFTWARE_VERSION", length = 10)
	private String softwareVersion;

	@Column(name = "VENDOR_NAME", length = 100)
	private String vendorName;

	@Column(name = "VENDOR_HOMEPAGE", length = 100)
	private String vendorHomepage;

	@Column(name = "LICENSE_CODE", length = 100)
	private String licenseCode;

	@Column(name = "INSTALL_PATH", length = 100, nullable = false)
	private String installPath;

	@Column(name = "EXE_FILE_NAME", length = 50, nullable = false)
	private String exeFileName;

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
        return softwareUuid;
    }
}