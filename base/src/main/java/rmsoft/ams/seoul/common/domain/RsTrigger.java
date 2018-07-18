package rmsoft.ams.seoul.common.domain;

import io.onsemiro.core.domain.BaseJpaModel;
import lombok.*;
import org.apache.ibatis.type.Alias;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

@Setter
@Getter
@DynamicInsert
@DynamicUpdate
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "RS_TRIGGER")
@IdClass(RsTrigger.RsTriggerId.class)
@Alias("RsTrigger")
public class RsTrigger extends BaseJpaModel<RsTrigger.RsTriggerId> {

	@Id
	@Column(name = "TRIGGER_UUID", length = 36, nullable = false)
	private String triggerUuid;

	@Column(name = "STATUS_UUID", length = 36, nullable = false)
	private String statusUuid;

	@Column(name = "TRIGGER_CODE", length = 30, nullable = false)
	private String triggerCode;

	@Column(name = "TRIGGER_NAME", length = 9, nullable = false)
	private String triggerName;

	@Column(name = "TRIGGER_DATE")
	private String triggerDate;

	@Column(name = "USE_YN", length = 1, nullable = false)
	private String useYn;


	@Override
	public RsTriggerId getId() { return RsTriggerId.of(triggerUuid);}

	/**
	 * The type Cl classification scheme id.
	 */
	@Embeddable
	@Data
	@NoArgsConstructor
	@RequiredArgsConstructor(staticName = "of")
	public static class RsTriggerId implements Serializable {
		@NonNull
		private String triggerUuid;
	}
}