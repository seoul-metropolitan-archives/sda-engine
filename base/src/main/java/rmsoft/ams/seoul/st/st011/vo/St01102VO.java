package rmsoft.ams.seoul.st.st011.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class St01102VO  {
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
    private String aggregationUuid;
}
