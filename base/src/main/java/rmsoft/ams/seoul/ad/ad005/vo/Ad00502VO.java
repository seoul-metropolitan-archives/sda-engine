package rmsoft.ams.seoul.ad.ad005.vo;

import io.onsemiro.core.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The type Ad 00502 vo.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Ad00502VO extends BaseVO
{
    private String glossaryUuid;
    private String entityTypeUuid;
    private String entityColumnUuid;
    private String termCode;
    private String tableCode;
    private String tableName;
    private String tableDescription;
    private String columnName;
    private String columnDescription;
    private String dataType;
    private String nullable;
}
