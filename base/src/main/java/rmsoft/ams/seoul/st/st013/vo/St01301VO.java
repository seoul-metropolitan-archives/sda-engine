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
    private Timestamp requestDate;
    private Timestamp exceptStartDate;
    private Timestamp exceptEndDate;
    private String exceptReason;


}
