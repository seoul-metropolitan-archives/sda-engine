package rmsoft.ams.seoul.st.st009.vo;

import io.onsemiro.core.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class St00901VO extends BaseVO {

    private String takeoutRequestUuid;
    private String requestTypeUuid;
    private String requestName;
    private String requestorUuid;
    private Timestamp takeoutDate;
    private Timestamp returnDueDate;
    private Timestamp returnDate;
    private String takeoutPropose;
    private String statusUuid;
    private String outsourcingDepartment;
    private String outsourcingPosition;
    private String outsourcingPersonName;
    private String outsourcingPhone;
    private String changeStatus;
}
