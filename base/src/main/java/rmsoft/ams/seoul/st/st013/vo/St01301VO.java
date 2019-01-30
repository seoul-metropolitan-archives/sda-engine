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
    private String repositoryUuid;
    private String shelfUuid;
    private String containerTypeUuid;
    private String locationUuid;
    private String code;
    private String title;

    private String requestorUuid;
    private String requestDate;

    private String exceptStartDate;
    public String getExceptStartDate(){
        if( exceptStartDate == null){
            return null;
        }
        return exceptStartDate.substring(0, 10);
    }
    private String exceptEndDate;
    public String getExceptEndDate(){
        if( exceptEndDate == null){
            return null;
        }
        return exceptEndDate.substring(0, 10);
    }
    private String exceptReason;

    private String exceptStartDateFrom;
    private String exceptStartDateTo;
    private String exceptEndDateFrom;
    private String exceptEndDateTo;


}
