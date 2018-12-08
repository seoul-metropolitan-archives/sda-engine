package rmsoft.ams.seoul.common.domain;

import io.onsemiro.core.domain.SimpleJpaModel;
import lombok.*;
import org.apache.ibatis.type.Alias;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Setter
@Getter
@DynamicInsert
@DynamicUpdate
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "ST_TAKEOUT_REQUEST")
@IdClass(StTakeoutRequest.StTakeoutRequestId.class)
@Alias("StTakeoutRequest")
public class StTakeoutRequest extends SimpleJpaModel<StTakeoutRequest.StTakeoutRequestId> {

    @Id
    @Column(name = "TAKEOUT_REQUEST_UUID", length = 36, nullable = false)
    private String takeoutRequestUuid;

    @Column(name = "REQUEST_TYPE_UUID", length = 36, nullable = false)
    private String requestTypeUuid;

    @Column(name = "REQUEST_NAME", length = 500, nullable = false)
    private String requestName;

    @Column(name = "REQUESTOR_UUID", length = 36, nullable = false)
    private String requestorUuid;

    @Column(name = "TAKEOUT_DATE", nullable = false)
    private Timestamp takeoutDate;

    @Column(name = "RETURN_DUE_DATE", nullable = false)
    private Timestamp returnDueDate;

    @Column(name = "RETURN_DATE", nullable = false)
    private Timestamp returnDate;

    @Column(name = "TAKEOUT_PROPOSE", length = 4000, nullable = false)
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

    @Column(name = "INSERT_DATE", length = 36, nullable = false)
    private Timestamp insertDate;

    @Column(name = "UPDATE_UUID", length = 36, nullable = false)
    private String updateUuid;

    @Column(name = "UPDATE_DATE", nullable = false)
    private Timestamp updateDate;

    @Override
    public StTakeoutRequestId getId() {return StTakeoutRequestId.of(takeoutRequestUuid);}

    @Embeddable
    @Data
    @NoArgsConstructor
    @RequiredArgsConstructor(staticName = "of")
    public static class StTakeoutRequestId implements Serializable {
        @NonNull
        private String takeoutRequestUuid;
    }
}
