package rmsoft.ams.seoul.common.domain;

import io.onsemiro.core.domain.BaseJpaModel;
import lombok.*;
import org.apache.ibatis.type.Alias;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Setter
@Getter
@DynamicInsert
@DynamicUpdate
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "ST_REPOSITORY")
@IdClass(StRepository.StRepositoryId.class)
@Alias("StRepository")
public class StRepository extends BaseJpaModel<StRepository.StRepositoryId>{

	@Id
	@Column(name = "REPOSITORY_UUID", length = 36, nullable = false)
	private String repositoryUuid;

	@Column(name = "STATUS_UUID", length = 36, nullable = false)
	private String statusUuid;

	@Column(name = "REPOSITORY_CODE", length = 30, nullable = false)
	private String repositoryCode;

	@Column(name = "REPOSITORY_NAME", length = 50, nullable = false)
	private String repositoryName;

	@Column(name = "USE_YN", length = 1, nullable = false)
	private String useYn;

	@Override
	public StRepositoryId getId() {
		return StRepositoryId.of(repositoryUuid);
	}

	/**
	 * The type Cl classification scheme id.
	 */
	@Embeddable
	@Data
	@NoArgsConstructor
	@RequiredArgsConstructor(staticName = "of")
	public static class StRepositoryId implements Serializable {
		@NonNull
		private String repositoryUuid;
	}
}