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
@Table(name = "LC_LEAD_CASE")
@IdClass(LcLeadCase.LcLeadCaseId.class)
@Alias("LcLeadCase")
public class LcLeadCase extends BaseJpaModel<LcLeadCase.LcLeadCaseId>{

	@Id
	@Column(name = "LEAD_CASE_UUID", length = 36, nullable = false)
	private String leadCaseUuid;

	@Column(name = "LEAD_CASE_NO", length = 36, nullable = false)
	private String leadCaseNo;

	@Column(name = "LEAD_CASE_NAME", length = 500, nullable = false)
	private String leadCaseName;

	@Column(name = "MAJOR_CLASSIFICATION_UUID", length = 500)
	private String majorClassificationUuid;

	@Column(name = "MIDDLE_CLASSIFICATION_UUID", length = 36)
	private String middleClassificationUuid;

	@Column(name = "OCCUPATION", length = 500)
	private String occupation;

	@Column(name = "REGION", length = 500)
	private String region;

	@Column(name = "ADDRESS", length = 500)
	private String address;

	@Column(name = "CONTACT_TARGET", length = 500)
	private String contactTarget;

	@Column(name = "PHONE", length = 50)
	private String phone;

	@Column(name = "PERSON_IN_CHARGE", length = 50)
	private String personInCharge;

	@Column(name = "COLLECT_STATUS_UUID", length = 36)
	private String collectStatusUuid;

	@Column(name = "OWN_MATERIAL", length = 4000)
	private String ownMaterial;

	@Column(name = "DESCRIPTION", length = 4000)
	private String description;

	@Column(name = "NOTES", length = 4000)
	private String notes;

	@Override
	public LcLeadCaseId getId() {
		return LcLeadCaseId.of(leadCaseUuid);
	}

	/**
	 * The type Cl classification scheme id.
	 */
	@Embeddable
	@Data
	@NoArgsConstructor
	@RequiredArgsConstructor(staticName = "of")
	public static class LcLeadCaseId implements Serializable {
		@NonNull
		private String leadCaseUuid;
	}
}