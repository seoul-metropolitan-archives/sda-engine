package rmsoft.ams.seoul.st.st012.vo;

import io.onsemiro.core.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class St01202VO extends BaseVO {

    private String withoutNoticeIoRecordUuid;

    private String aggregationUuid;

    private String disposerUuid;

    private Timestamp disposalDate;

    private String reason;


}
