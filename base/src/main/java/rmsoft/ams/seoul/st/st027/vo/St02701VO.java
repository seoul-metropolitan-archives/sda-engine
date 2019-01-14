package rmsoft.ams.seoul.st.st027.vo;

import io.onsemiro.core.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class St02701VO extends BaseVO {

    private String zoneUuid;


    private String zoneId;

    private String zoneName;
}
