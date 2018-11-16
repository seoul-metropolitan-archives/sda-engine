package rmsoft.ams.seoul.st.st011.vo;

import io.onsemiro.core.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class St01102VO extends BaseVO{

    private String exceptRecordResultUuid;
    private String inoutExceptUuid;
    private String aggregationUuid;


}
