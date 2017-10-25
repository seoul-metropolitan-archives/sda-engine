package rmsoft.ams.seoul.ad.ad000.vo;

import io.onsemiro.core.vo.BaseVO;
import lombok.*;


@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Ad00001VO extends BaseVO
{
    private String configurationUuid;

    private String configurationCode;

    private String configurationValue;

    private String useYN;
}
