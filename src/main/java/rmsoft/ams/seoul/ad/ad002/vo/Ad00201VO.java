package rmsoft.ams.seoul.ad.ad002.vo;

import io.onsemiro.core.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The type Ad 00201 vo.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Ad00201VO extends BaseVO {
    private String messageUuid;

    private String messageCode;

    private String messageName;

    private String serviceUuid;

    private String dbErrorCode;

    private String useYN;
}
