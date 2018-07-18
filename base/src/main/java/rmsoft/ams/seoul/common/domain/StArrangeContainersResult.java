package rmsoft.ams.seoul.common.domain;

import io.onsemiro.core.annotations.Comment;
import io.onsemiro.core.domain.BaseJpaModel;
import io.onsemiro.core.domain.SimpleJpaModel;
import lombok.*;
import org.apache.ibatis.type.Alias;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Setter
@Getter
@DynamicInsert
@DynamicUpdate
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "ST_ARRANGE_CONTAINERS_RESULT")
@IdClass(StArrangeContainersResult.StArrangeContainersResultId.class)
@Alias("StArrangeContainersResult")
public class StArrangeContainersResult extends BaseJpaModel<StArrangeContainersResult.StArrangeContainersResultId> {

	@Id
	@Column(name = "ARRANGE_CONTAINERS_RESULT_UUID", length = 36, nullable = false)
	private String arrangeContainersResultUuid;

	@Column(name = "LOCATION_UUID", length = 36, nullable = false)
	private String locationUuid;

	@Column(name = "CONTAINER_UUID", length = 36, nullable = false)
	private String containerUuid;

	@Column(name = "STATUS_UUID", length = 36, nullable = false)
	private String statusUuid;

	@Column(name = "ARRANGED_DATE", nullable = false)
	private Timestamp arrangedDate;

	@Column(name = "DESCRIPTION", length = 4000)
	private String description;

	@Column(name = "NOTES", length = 4000)
	private String notes;

	@Column(name = "UPDATE_DATE", nullable = false)
	private Timestamp updateDate;

	@Column(name = "INSERT_UUID", length = 36, nullable = false)
	private String insertUuid;

	@Column(name = "INSERT_DATE", nullable = false)
	private Timestamp insertDate;

	@Column(name = "UPDATE_UUID", length = 36, nullable = false)
	private String updateUuid;


	@Override
	public StArrangeContainersResultId getId() {
		return StArrangeContainersResultId.of(arrangeContainersResultUuid);
	}

	/**
	 * The type Cl classification scheme id.
	 */
	@Embeddable
	@Data
	@NoArgsConstructor
	@RequiredArgsConstructor(staticName = "of")
	public static class StArrangeContainersResultId implements Serializable {
		@NonNull
		private String arrangeContainersResultUuid;
	}
}