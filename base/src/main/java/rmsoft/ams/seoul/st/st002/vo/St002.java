package rmsoft.ams.seoul.st.st002.vo;

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
import java.math.BigDecimal;
import java.sql.Timestamp;

@Setter
@Getter
@DynamicInsert
@DynamicUpdate
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "ST_CONTAINER")
@Comment("")
@Alias("st002")
public class St002 extends SimpleJpaModel<String> {

	@Id
	@Column(name = "CONTAINER_UUID", length = 36, nullable = false)
	private String containerUuid;

	@Column(name = "STATUS_UUID", length = 36, nullable = false)
	private String statusUuid;

	@Column(name = "CONTAINER_NAME", length = 50, nullable = false)
	private String containerName;

	@Column(name = "CONTAINER_TYPE_UUID", length = 36, nullable = false)
	private String containerTypeUuid;

	@Column(name = "CONTROL_NUMBER", length = 30, nullable = false)
	private String controlNumber;

	@Column(name = "PROVENANCE", length = 500)
	private String provenance;

	@Column(name = "CREATION_START_DATE", length = 8)
	private String creationStartDate;

	@Column(name = "CREATION_END_DATE", length = 8)
	private String creationEndDate;

	@Column(name = "ORDER_NO", precision = 0, scale = -127)
	private BigDecimal orderNo;

	@Column(name = "ORDER_KEY", length = 100)
	private String orderKey;

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
        return containerUuid;
    }
}