package rmsoft.ams.seoul.ad.ad000.vo;

import io.onsemiro.core.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


/**
 * The type Ad 00001 vo.
 */
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
