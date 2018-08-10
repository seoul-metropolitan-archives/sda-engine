package rmsoft.ams.seoul.st.st003.vo;

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
@Table(name = "ST_ARRANGE_RECORDS_RESULT")
@Comment("")
@Alias("st003")
public class St003 extends SimpleJpaModel<String> {

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
    public String getId() {
        return arrangeRecordsResultUuid;
    }
}