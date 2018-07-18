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
@Table(name = "RS_RECORD_SCHEDULE_RESULT")
@IdClass(RsRecordScheduleResult.RsRecordScheduleResultId.class)
@Alias("RsRecordScheduleResult")
public class RsRecordScheduleResult extends BaseJpaModel<RsRecordScheduleResult.RsRecordScheduleResultId> {

	@Id
	@Column(name = "RECORD_SCHEDULE_RESULT_UUID", length = 36, nullable = false)
	private String recordScheduleResultUuid;

	@Column(name = "RECORD_SCHEDULE_UUID", length = 36, nullable = false)
	private String recordScheduleUuid;

	@Column(name = "STATUS_UUID", length = 36, nullable = false)
	private String statusUuid;

	@Column(name = "ITEM_UUID", length = 36, nullable = false)
	private String itemUuid;

	@Column(name = "DISPOSAL_TYPE_UUID", length = 36, nullable = false)
	private String disposalTypeUuid;

	@Column(name = "INITIAL_DATE")
	private String initialDate;

	@Column(name = "DISPOSAL_DUE_DATE")
	private String disposalDueDate;

	@Column(name = "DISPOSAL_CONFIRM_DATE")
	private String disposalConfirmDate;

	@Column(name = "DISPOSAL_CONFIRM_REASON", length = 4000)
	private String disposalConfirmReason;

	@Column(name = "DISPOSAL_COMPLETE_DATE")
	private Timestamp disposalCompleteDate;

	@Column(name = "DESCRIPTION", length = 4000)
	private String description;

	@Column(name = "NOTES", length = 4000)
	private String notes;

	@Column(name = "DISPOSAL_STATUS", length = 36, nullable = false)
	private String disposalStatus;

	@Override
	public RsRecordScheduleResultId getId() {
		return RsRecordScheduleResultId.of(recordScheduleResultUuid);
	}

	/**
	 * The type Cl classification scheme id.
	 */
	@Embeddable
	@Data
	@NoArgsConstructor
	@RequiredArgsConstructor(staticName = "of")
	public static class RsRecordScheduleResultId implements Serializable {
		@NonNull
		private String recordScheduleResultUuid;
	}
}