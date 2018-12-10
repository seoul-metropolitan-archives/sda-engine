package rmsoft.ams.seoul.st.st008.vo;

import io.onsemiro.core.annotations.Comment;
import io.onsemiro.core.domain.BaseJpaModel;
import io.onsemiro.core.domain.SimpleJpaModel;
import lombok.*;
import org.apache.ibatis.type.Alias;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * The type Cl 003.
 */
@Setter
@Getter
@DynamicInsert
@DynamicUpdate
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "ST_TAKEOUT_REQUEST")
@Alias("st008")
public class St008 extends SimpleJpaModel<String> {

    @Id
    @Column(name = "TAKEOUT_REQUEST_UUID", length = 36, nullable = false)
    private String takeoutRequestUuid;

    @Column(name = "REQUEST_TYPE_UUID", length = 36, nullable = false)
    private String requestTypeUuid;

    @Column(name = "REQUEST_NAME", length = 500, nullable = false)
    private String requestName;

    @Column(name = "REQUESTOR_UUID", length = 36, nullable = false)
    private String requestorUuid;


    @Column(name = "TAKEOUT_DATE"   , nullable = false)
    private String takeoutDate;

    @Column(name = "RETURN_DUE_DATE"  , nullable = false)
    private String returnDueDate;

    @Column(name = "RETURN_DATE"  , nullable = true)
    private String returnDate;

    @Column(name = "TAKEOUT_PROPOSE", length = 4000, nullable = true)
    private String takeoutPropose;

    @Column(name = "STATUS_UUID", length = 36, nullable = false)
    private String statusUuid;

    @Column(name = "OUTSOURCING_DEPARTMENT", length = 50, nullable = false)
    private String outsourcingDepartment;

    @Column(name = "OUTSOURCING_POSITION", length = 50, nullable = false)
    private String outsourcingPosition;

    @Column(name = "OUTSOURCING_PERSON_NAME", length = 10, nullable = false)
    private String outsourcingPersonName;


    @Column(name = "OUTSOURCING_PHONE", length = 10, nullable = false)
    private String outsourcingPhone;

    @Column(name = "INSERT_UUID", length = 36, nullable = false)
    private String insertUuid;

    @Column(name = "INSERT_DATE", nullable = false)
    private Timestamp insertDate;

    @Column(name = "UPDATE_UUID", length = 36, nullable = false)
    private String updateUuid;

    @Override
    public String getId() {
        return takeoutRequestUuid;
    }
}
