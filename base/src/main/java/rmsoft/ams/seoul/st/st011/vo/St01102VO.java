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
    private String takeoutDate;
    private String returnDueDate;
    private String returnDate;
    private String takeoutPropose;
    private String statusUuid;
    private String outsourcingDepartment;
    private String outsourcingPosition;
    private String outsourcingPersonName;
    private String outsourcingPhone;
    private String changeStatus;
    private String aggregationUuid;

    private String approverUuid;
    private String approvalName;
    private String approveDate;
    private String rejectorUuid;
    private String rejectorName;
    private String rejectDate;

    private String requestorName;
    private String userGroupName;

    private String inoutTypeUuid;
    private String gateName;
    private String gateUuid;
    private String inoutDateTime;

}
