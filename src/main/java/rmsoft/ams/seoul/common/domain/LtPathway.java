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
@Table(name = "LT_PATHWAY")
@IdClass(LtPathway.LtPathwayId.class)
@Alias("LtPathway")
public class LtPathway extends BaseJpaModel<LtPathway.LtPathwayId> {

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

	@Column(name = "USE_YN", length = 1, nullable = false)
	private String useYn;


	@Override
	public LtPathway.LtPathwayId getId() { return LtPathway.LtPathwayId.of(pathwayUuid); }

	/**
	 * The type Ad entity type id.
	 */
	@Embeddable
	@Data
	@NoArgsConstructor
	@RequiredArgsConstructor(staticName = "of")
	public static class LtPathwayId implements Serializable {

		@NonNull
		private String pathwayUuid;
	}
}