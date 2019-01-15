package rmsoft.ams.seoul.st.st008.vo;

import io.onsemiro.core.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * The type Cl 00301 vo.
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class St00801VO extends BaseVO
{
    private String takeoutRequestUuid;
    private String requestTypeUuid;

    private String requestorUuid;
    private String statusUuid;
    private String outsourcingDepartment;
    private String outsourcingPosition;
    private String outsourcingPersonName;
    private String outsourcingPhone;

    private String requestName; // 반출의뢰서
    private String requestorName; // 반출자
    private String userGroupName; //  소속
    private Timestamp takeoutDate; //  반출일자
    private Timestamp returnDate; //  반입일
    private Timestamp returnDueDate; //  반입예정일
    private String takeoutDateFrom;
    private String takeoutDateTo;
    private String returnDueDateFrom;
    private String returnDueDateTo;
    private String status; //  상태
    private String takeoutPropose ; // 반출목적

}
