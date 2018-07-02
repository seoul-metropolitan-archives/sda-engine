package rmsoft.ams.seoul.common.domain;

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
@Table(name = "LT_SOFTWARE")
@IdClass(LtSoftware.LtSoftwareId.class)
@Alias("LtSoftware")
public class LtSoftware extends BaseJpaModel<LtSoftware.LtSoftwareId> {

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

	@Column(name = "USE_YN", length = 1, nullable = false)
	private String useYn;



	@Override
	public LtSoftware.LtSoftwareId getId() { return LtSoftware.LtSoftwareId.of(softwareUuid); }

	/**
	 * The type Ad entity type id.
	 */
	@Embeddable
	@Data
	@NoArgsConstructor
	@RequiredArgsConstructor(staticName = "of")
	public static class LtSoftwareId implements Serializable {

		@NonNull
		private String softwareUuid;
	}
}