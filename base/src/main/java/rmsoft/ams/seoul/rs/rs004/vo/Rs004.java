package rmsoft.ams.seoul.rs.rs004.vo;

import io.onsemiro.core.annotations.ColumnPosition;
import io.onsemiro.core.domain.SimpleJpaModel;
import io.onsemiro.core.annotations.Comment;
import lombok.*;
import org.apache.ibatis.type.Alias;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import javax.persistence.*;
import java.sql.Timestamp;

@Setter
@Getter
@DynamicInsert
@DynamicUpdate
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "RS_RECORD_SCHEDULE_RESULT")
@Comment("")
@Alias("rs004")
public class Rs004 extends SimpleJpaModel<String> {

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
	private Timestamp initialDate;

	@Column(name = "DISPOSAL_DUE_DATE")
	private Timestamp disposalDueDate;

	@Column(name = "DISPOSAL_CONFIRM_DATE")
	private Timestamp disposalConfirmDate;

	@Column(name = "DISPOSAL_CONFIRM_REASON", length = 4000)
	private String disposalConfirmReason;

	@Column(name = "DISPOSAL_COMPLETE_DATE")
	private Timestamp disposalCompleteDate;

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
        return recordScheduleResultUuid;
    }
}