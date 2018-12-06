package rmsoft.ams.seoul.st.st013.vo;

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
@Table(name = "ST_INOUT_EXCEPT")
@Comment("")
@Alias("st013")
public class St013 extends SimpleJpaModel<String> {

	@Id
	@Column(name = "INOUT_EXCEPT_UUID", length = 36, nullable = false)
	private String inoutExceptUuid;

	@Column(name = "REQUEST_NAME", length = 500, nullable = false)
	private String requestName;

	@Column(name = "REQUESTOR_UUID", length = 36, nullable = false)
	private String requestorUuid;

	@Column(name = "REQUEST_DATE", length = 36, nullable = false)
	private Timestamp requestDate;

	@Column(name = "EXCEPT_START_DATE", length = 36, nullable = false)
	private Timestamp exceptStartDate;

	@Column(name = "EXCEPT_END_DATE", length = 36, nullable = false)
	private Timestamp exceptEndDate;

	@Column(name = "EXCEPT_REASON", length = 4000)
	private String exceptReason;

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
        return inoutExceptUuid;
    }
}