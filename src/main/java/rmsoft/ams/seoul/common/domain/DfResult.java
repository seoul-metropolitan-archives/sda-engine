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
@Table(name = "DF_DISPOSAL_FREEZE_RESULT")
@IdClass(DfResult.DfResultId.class)
@Alias("dfResult")
public class DfResult extends BaseJpaModel<DfResult.DfResultId> {

	@Id
	@Column(name = "DISPOSAL_FREEZE_RESULT_UUID", length = 36, nullable = false)
	private String disposalFreezeResultUuid;

	@Column(name = "DISPOSAL_FREEZE_EVENT_UUID", length = 36, nullable = false)
	private String disposalFreezeEventUuid;

	@Column(name = "DISPOSAL_FREEZE_DEGREE_UUID", length = 36, nullable = false)
	private String disposalFreezeDegreeUuid;

	@Column(name = "AGGREGATION_UUID", length = 36)
	private String aggregationUuid;

	@Column(name = "ITEM_UUID", length = 36)
	private String itemUuid;

	@Column(name = "FREEZED_DATE")
	private Timestamp freezedDate;

	@Override
	public DfResult.DfResultId getId() { return DfResult.DfResultId.of(disposalFreezeResultUuid); }

	/**
	 * The type Ad entity type id.
	 */
	@Embeddable
	@Data
	@NoArgsConstructor
	@RequiredArgsConstructor(staticName = "of")
	public static class DfResultId implements Serializable {

		@NonNull
		private String disposalFreezeResultUuid;
	}
}