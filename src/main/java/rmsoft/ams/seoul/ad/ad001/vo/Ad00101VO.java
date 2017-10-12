package rmsoft.ams.seoul.ad.ad001.vo;

import io.onsemiro.core.vo.BaseVO;
import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Ad00101VO extends BaseVO
{
    private String configurationUUID;
    private String configurationCode;
    private String configurationValue;
    private String serviceUUID;
    private String insertUUID;
    private String useYN;
}
