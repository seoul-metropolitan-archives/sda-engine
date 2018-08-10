package rmsoft.ams.seoul.ad.ad008.vo;

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
@Table(name = "AD_AUDIT")
@Comment("")
@Alias("ad008")
public class Ad008 extends SimpleJpaModel<String> {

	@Id
	@Column(name = "AUDIT_UUID", length = 40, nullable = false)
	private String auditUuid;

	@Column(name = "ENTITY_TYPE_UUID", length = 36, nullable = false)
	private String entityTypeUuid;

	@Column(name = "ENTITY_COLUMN_UUID", length = 36)
	private String entityColumnUuid;

	@Column(name = "PROGRAM_UUID", length = 36, nullable = false)
	private String programUuid;

	@Column(name = "PROGRAM_NAME", length = 50)
	private String programName;

	@Column(name = "FUNCTION_UUID", length = 36)
	private String functionUuid;

	@Column(name = "PRIMARY_KEY_UUID", length = 36)
	private String primaryKeyUuid;

	@Column(name = "PREVIOUS_VALUE", length = 4000)
	private String previousValue;

	@Column(name = "NEW_VALUE", length = 4000)
	private String newValue;

	@Column(name = "DESCRIPTION", length = 4000)
	private String description;

	@Column(name = "NOTES", length = 4000)
	private String notes;

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
        return auditUuid;
    }
}