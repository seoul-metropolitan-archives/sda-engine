package rmsoft.ams.seoul.ad.ad001.vo;

import io.onsemiro.core.vo.BaseVO;
import lombok.*;

import java.io.Serializable;

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
