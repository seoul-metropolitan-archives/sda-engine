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
public class At00103VO extends BaseVO {
    private String aggregationUuid;
    private String authorityRecordResultUuid;
    private String authorityUuid;
    private String entityColumnUuid;
    private String authorityEntityColumnUuid;
    private String entityColumnTitle;
    private String termName;
    private String termCode;
    private String entityTypeCode;
    private String entityTypeName;
}
