package rmsoft.ams.seoul.at.at001.vo;

import io.onsemiro.core.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * The type Cl 00201 vo.
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class At00102VO extends BaseVO {
    private String authorityRelationUuid;
    private String authorityUuid;
    private String relationTypeUuid;
    private String relAuthorityUuid;
    private String relAuthorityName;
    private String relAuthorityNo;
}
