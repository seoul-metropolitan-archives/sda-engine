package rmsoft.ams.seoul.common.domain;

import io.onsemiro.core.domain.BaseJpaModel;
import lombok.*;
import org.apache.ibatis.type.Alias;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Setter
@Getter
@DynamicInsert
@DynamicUpdate
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "LC_LEAD_CASE_SCHEDULE")
@IdClass(LcLeadCaseSchedule.LcLeadCaseScheduleId.class)
@Alias("LcLeadCaseSchedule")
public class LcLeadCaseSchedule extends BaseJpaModel<LcLeadCaseSchedule.LcLeadCaseScheduleId>{

	@Id
	@Column(name = "LEAD_CASE_SCHEDULE_UUID", length = 36, nullable = false)
	private String leadCaseScheduleUuid;

	@Column(name = "LEAD_CASE_UUID", length = 36, nullable = false)
	private String leadCaseUuid;

	@Column(name = "SCHEDULE_NO", nullable = false)
	private String scheduleNo;

	@Column(name = "INGEST_ORG_UUID", length = 36, nullable = false)
	private String ingestOrgUuid;

	@Column(name = "CREATOR_UUID", length = 36)
	private String creatorUuid;

	@Column(name = "COLLECTION_TYPE_UUID", length = 36)
	private String collectionTypeUuid;

	@Column(name = "CONTACT_TYPE_UUID", length = 36)
	private String contactTypeUuid;

	@Column(name = "CONTACT_MAKER", length = 50)
	private String contactMaker;

	@Column(name = "COLLECT_MAKER", length = 50)
	private String collectMaker;

	@Column(name = "COLLECTION_DATE")
	private Date collectionDate;

	@Column(name = "COLLECTION_CONTENTS", length = 4000)
	private String collectionContents;

	@Column(name = "DESCRIPTION", length = 4000)
	private String description;

	@Column(name = "NOTES", length = 4000)
	private String notes;

	@Override
	public LcLeadCaseScheduleId getId() {
		return LcLeadCaseScheduleId.of(leadCaseScheduleUuid);
	}

	/**
	 * The type Cl classification scheme id.
	 */
	@Embeddable
	@Data
	@NoArgsConstructor
	@RequiredArgsConstructor(staticName = "of")
	public static class LcLeadCaseScheduleId implements Serializable {
		@NonNull
		private String leadCaseScheduleUuid;
	}
}