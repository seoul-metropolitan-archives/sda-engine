package rmsoft.ams.seoul.ac.ac010.vo;

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
@Table(name = "AC_ROW_SECURITY")
@Comment("")
@Alias("ac010")
public class Ac010 extends SimpleJpaModel<String> {

	@Id
	@Column(name = "ROW_SECURITY_UUID", length = 36, nullable = false)
	private String rowSecurityUuid;

	@Column(name = "RS_ENTITY_TYPE_UUID", length = 36, nullable = false)
	private String rsEntityTypeUuid;

	@Column(name = "RS_ENTITY_COLUMN_UUID", length = 36, nullable = false)
	private String rsEntityColumnUuid;

	@Column(name = "FROM_VALUE", length = 100, nullable = false)
	private String fromValue;

	@Column(name = "TO_VALUE", length = 100, nullable = false)
	private String toValue;

	@Column(name = "SELECT_YN", length = 1, nullable = false)
	private String selectYn;

	@Column(name = "INSERT_YN", length = 1, nullable = false)
	private String insertYn;

	@Column(name = "UPDATE_YN", length = 1, nullable = false)
	private String updateYn;

	@Column(name = "DELETE_YN", length = 1, nullable = false)
	private String deleteYn;

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
        return rowSecurityUuid;
    }
}