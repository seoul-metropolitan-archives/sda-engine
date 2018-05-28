package rmsoft.ams.seoul.df.df002.vo;

import io.onsemiro.core.annotations.ColumnPosition;
import io.onsemiro.core.domain.SimpleJpaModel;
import io.onsemiro.core.annotations.Comment;
import lombok.*;
import org.apache.ibatis.type.Alias;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Setter
@Getter
@DynamicInsert
@DynamicUpdate
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "DF_DISPOSAL_FREEZE_DEGREE")
@Comment("")
@Alias("df002")
public class Df002 extends SimpleJpaModel<String> {

	@Id
	@Column(name = "DISPOSAL_FREEZE_DEGREE_UUID", length = 36, nullable = false)
	private String disposalFreezeDegreeUuid;

	@Column(name = "DISPOSAL_FREEZE_EVENT_UUID", length = 36, nullable = false)
	private String disposalFreezeEventUuid;

	@Column(name = "FREEZE_YN", length = 1, nullable = false)
	private String freezeYn;

	@Column(name = "DEGREE", precision = 0, scale = -127, nullable = false)
	private BigDecimal degree;

	@Column(name = "DESCRIPTION", length = 4000)
	private String description;

	@Column(name = "NOTES", length = 4000)
	private String notes;

	@Column(name = "END_YN", length = 1, nullable = false)
	private String endYn;

	@Column(name = "TERMINATOR_UUID", length = 36)
	private String terminatorUuid;

	@Column(name = "END_DATE")
	private Timestamp endDate;

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
        return disposalFreezeDegreeUuid;
    }
}