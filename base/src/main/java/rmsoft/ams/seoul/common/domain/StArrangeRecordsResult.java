package rmsoft.ams.seoul.common.domain;

import io.onsemiro.core.domain.BaseJpaModel;
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
@Table(name = "ST_ARRANGE_RECORDS_RESULT")
@IdClass(StArrangeRecordsResult.StArrangeRecordsResultId.class)
@Alias("StArrangeRecordsResult")
public class StArrangeRecordsResult extends BaseJpaModel<StArrangeRecordsResult.StArrangeRecordsResultId> {

	@Id
	@Column(name = "ARRANGE_RECORDS_RESULT_UUID", length = 36, nullable = false)
	private String arrangeRecordsResultUuid;

	@Column(name = "CONTAINER_UUID", length = 36, nullable = false)
	private String containerUuid;

	@Column(name = "AGGREGATION_UUID", length = 36)
	private String aggregationUuid;

	@Column(name = "ITEM_UUID", length = 36)
	private String itemUuid;

	@Column(name = "STATUS_UUID", length = 36, nullable = false)
	private String statusUuid;

	@Column(name = "ARRANGED_DATE", nullable = false)
	private Timestamp arrangedDate;

	@Override
	public StArrangeRecordsResultId getId() {
		return StArrangeRecordsResultId.of(arrangeRecordsResultUuid);
	}

	/**
	 * The type Cl classification scheme id.
	 */
	@Embeddable
	@Data
	@NoArgsConstructor
	@RequiredArgsConstructor(staticName = "of")
	public static class StArrangeRecordsResultId implements Serializable {
		@NonNull
		private String arrangeRecordsResultUuid;
	}
}