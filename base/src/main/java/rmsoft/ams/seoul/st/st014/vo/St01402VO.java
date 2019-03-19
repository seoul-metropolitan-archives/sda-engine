package rmsoft.ams.seoul.st.st014.vo;

import io.onsemiro.core.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class St01402VO extends BaseVO {
    private String withoutNoticeIoRecordUuid;
    private String inoutDateTime;
    private String gateName;
    private String inoutTypeUuid;
    private String withoutNoticeYn;
    private String reason;
    private String disposerName;
}
