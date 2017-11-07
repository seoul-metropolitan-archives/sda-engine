package rmsoft.ams.seoul.ad.ad006.vo;

import io.onsemiro.core.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Ad00601VO extends BaseVO
{
    private String entityTypeUuid;
    private String entityType;
    private String entityName;
    private String useYN;
    private String auditYN;
}
