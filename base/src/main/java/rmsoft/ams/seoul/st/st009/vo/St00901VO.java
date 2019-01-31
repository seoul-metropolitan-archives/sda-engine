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

    private String requestorUuid;
    private String statusUuid;
    private String outsourcingDepartment;
    private String outsourcingPosition;
    private String outsourcingPersonName;
    private String outsourcingPhone;

    private String requestName; // 반출의뢰서
    private String requestorName; // 반출자
    private String userGroupName; //  소속
    private String takeoutDate; //  반출일자
    private Timestamp returnDate; //  반입일
    private String returnDueDate; //  반입예정일

    public String getTakeoutDate(){
        return takeoutDate.substring(0,10);
    }

    public String getReturnDueDate(){
        return returnDueDate.substring(0,10);
    }

    private String takeoutDateFrom;
    private String takeoutDateTo;
    private String returnDueDateFrom;
    private String returnDueDateTo;
    private String status; //  상태
    private String takeoutPropose ; // 반출목적

    private String employeeYn;
    private Timestamp startDate;

    private String code;
    private String title;

}
