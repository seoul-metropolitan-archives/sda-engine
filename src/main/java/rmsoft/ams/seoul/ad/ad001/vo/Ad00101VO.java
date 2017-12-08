package rmsoft.ams.seoul.ad.ad001.vo;

import io.onsemiro.core.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The type Ad 00101 vo.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Ad00101VO extends BaseVO
{
    private String configurationUuid;
    private String configurationCode;
    private String configurationValue;
    private String serviceUuid;
    private String useYN;
}
