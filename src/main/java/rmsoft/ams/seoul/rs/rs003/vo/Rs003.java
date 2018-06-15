package rmsoft.ams.seoul.rs.rs003.vo;

import io.onsemiro.core.annotations.Comment;
import io.onsemiro.core.domain.SimpleJpaModel;
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
@Table(name = "RS_TRIGGER")
@Comment("")
@Alias("rs003")
public class Rs003 extends SimpleJpaModel<String> {

	@Id
	@Column(name = "RECORD_SCHEDULE_UUID", length = 36, nullable = false)
	private String recordScheduleUuid;

	@Column(name = "STATUS_UUID", length = 36, nullable = false)
	private String statusUuid;

	@Column(name = "RS_CODE", length = 30, nullable = false)
	private String rsCode;

	@Column(name = "RS_NAME", length = 50, nullable = false)
	private String rsName;

	@Column(name = "GENERAL_RECORD_SCHEDULE_UUID", length = 36)
	private String generalRecordScheduleUuid;

	@Column(name = "TRIGGER_UUID", length = 36)
	private String triggerUuid;

	@Column(name = "RETENTION_PERIOD_UUID", length = 36)
	private String retenionPeriodUuid;

	@Column(name = "DISPOSAL_TYPE_UUID", length = 36, nullable = false)
	private String disposalTypeUuid;

	@Column(name = "BASED_ON", length = 1000, nullable = false)
	private String basedOn;

	@Column(name = "USE_YN", length = 1, nullable = false)
	private String useYn;


	@Override
	public String getId() {
		return recordScheduleUuid;
	}
}