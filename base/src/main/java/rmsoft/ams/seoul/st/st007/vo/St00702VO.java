package rmsoft.ams.seoul.st.st007.vo;

import io.onsemiro.core.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class St00702VO extends BaseVO {

    private String aggregationUuid;
    private String code;
    private String title;
    private String retentionPeriodName; // 보존기간
    private String descriptionStartDate;
    private String descriptionEndDate;

    private String initialDate;
    public String getInitialDate(){
        if( initialDate == null){
            return null;
        }
        return initialDate.substring(0, 10);
    }
    private String disposalDueDate;
    public String getDisposalDueDate(){
        if( disposalDueDate == null){
            return null;
        }
        return disposalDueDate.substring(0, 10);
    }
    private String disposalCompleteDate;
    public String getDisposalCompleteDate(){
        if( disposalCompleteDate == null){
            return null;
        }
        return disposalCompleteDate.substring(0, 10);
    }

    private String disposalDueDateStart; //filter 인애들은 string 을 주자
    private String disposalDueDateEnd;//filter 인애들은 string 을 주자


}
