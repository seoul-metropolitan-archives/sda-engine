package rmsoft.ams.seoul.st.st013.vo;

import io.onsemiro.core.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class St01301VO extends BaseVO {

    private String inoutExceptUuid;
    private String requestName;

    private String requestorUuid;
    private String requestDate;
    public String getRequestDate(){
        return requestDate.substring(0, 10);
    }
    private String exceptStartDate;
    public String getExceptStartDate(){
        return exceptStartDate.substring(0, 10);
    }
    private String exceptEndDate;
    public String getExceptEndDate(){
        return exceptEndDate.substring(0, 10);
    }
    private String exceptReason;
    private String exceptStartDateFrom;

    private String exceptStartDateTo;


}
