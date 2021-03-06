package rmsoft.ams.seoul.ad.ad006.vo;

import io.onsemiro.core.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The type Ad 00602 vo.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Ad00602VO extends BaseVO
{
    private String entityColumnUuid;
    private String entityTypeUuid;
    private String glossaryUuid;
    private String termCode;
    private String termName;
    private String columnName;
    private String dataType1;
    private String comments;
    private String dataType2;
    private String nullable;
    private String auditYN = "N";
    private String useYN = "N";

}
