package rmsoft.ams.seoul.st.st018.vo;

import io.onsemiro.core.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class St01802VO extends BaseVO {

    private int seq;

    private String aggregationUuid;
    private String publishSourceTypeUuid;
    private String publishStatusUuid;
    private String publishDate;
    private String tag;

}
