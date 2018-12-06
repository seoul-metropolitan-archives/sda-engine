package rmsoft.ams.seoul.lc.lc001.vo;

import io.onsemiro.core.annotations.Comment;
import io.onsemiro.core.domain.SimpleJpaModel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Setter
@Getter
@DynamicInsert
@DynamicUpdate
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "LC_LEAD_CASE")
@Comment("")
@Alias("lc001")
public class Lc001 extends SimpleJpaModel<String> {
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

	@Column(name = "USE_YN", length = 1, nullable = false)
	private String useYn;

	@Column(name = "INSERT_UUID", length = 36, nullable = false)
	private String insertUuid;

	@Column(name = "INSERT_DATE", nullable = false)
	private Timestamp insertDate;

	@Column(name = "UPDATE_UUID", length = 36, nullable = false)
	private String updateUuid;

	@Column(name = "UPDATE_DATE", nullable = false)
	private Timestamp updateDate;


    @Override
    public String getId() {
        return leadCaseUuid;
    }
}