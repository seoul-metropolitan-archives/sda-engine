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
@Table(name = "LT_FILE_FORMAT")
@IdClass(LtFileFormat.LtFileFormatId.class)
@Alias("LtFileFormat")
public class LtFileFormat extends BaseJpaModel<LtFileFormat.LtFileFormatId> {

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

	@Column(name = "USE_YN", length = 1, nullable = false)
	private String useYn;



	@Override
	public LtFileFormat.LtFileFormatId getId() { return LtFileFormat.LtFileFormatId.of(fileFormatUuid); }

	/**
	 * The type Ad entity type id.
	 */
	@Embeddable
	@Data
	@NoArgsConstructor
	@RequiredArgsConstructor(staticName = "of")
	public static class LtFileFormatId implements Serializable {

		@NonNull
		private String fileFormatUuid;
	}
}