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
@Table(name = "LT_TOOL")
@IdClass(LtTool.LtToolId.class)
@Alias("LtTool")
public class LtTool extends BaseJpaModel<LtTool.LtToolId> {

	@Id
	@Column(name = "TOOL_UUID", length = 36, nullable = false)
	private String toolUuid;

	@Column(name = "TOOL_NAME", length = 500, nullable = false)
	private String toolName;

	@Column(name = "API", length = 500, nullable = false)
	private String api;

	@Column(name = "SOFTWARE_UUID", length = 36, nullable = false)
	private String softwareUuid;

	@Column(name = "USE_YN", length = 1, nullable = false)
	private String useYN;


	@Override
	public LtTool.LtToolId getId() { return LtTool.LtToolId.of(toolUuid); }

	/**
	 * The type Ad entity type id.
	 */
	@Embeddable
	@Data
	@NoArgsConstructor
	@RequiredArgsConstructor(staticName = "of")
	public static class LtToolId implements Serializable {

		@NonNull
		private String toolUuid;
	}
}