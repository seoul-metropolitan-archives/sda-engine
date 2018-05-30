package rmsoft.ams.seoul.ad.ad007.vo;

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
@Table(name = "AD_SERVICE")
@Comment("")
@Alias("ad007")
public class Ad007 extends SimpleJpaModel<String> {

	@Id
	@Column(name = "SERVICE_UUID", length = 36, nullable = false)
	private String serviceUuid;

	@Column(name = "SERVICE_CODE", length = 30, nullable = false)
	private String serviceCode;

	@Column(name = "SERVICE_NAME", length = 50, nullable = false)
	private String serviceName;

	@Column(name = "INSTALL_YN", length = 1, nullable = false)
	private String installYn;

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
        return serviceUuid;
    }
}