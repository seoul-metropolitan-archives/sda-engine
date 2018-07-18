package rmsoft.ams.seoul.ad.ad005.vo;

import io.onsemiro.core.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The type Ad 00501 vo.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Ad00501VO extends BaseVO
{
    private String glossaryUuid;
    private String termCode;
    private String termName;
    private String dataTypeUuid;
    private String useYN;
}
