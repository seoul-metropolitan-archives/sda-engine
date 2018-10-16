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
public class At00101VO extends BaseVO {

    private String authorityUuid ;
    private String authorityTypeUuid;
    private String authorityNo;
    private String authorityName;
    private String orgTypeUuid;
    private String startDate;
    private String endDate;
    private String mainJob;
    private String descriptionDate;
    private String descriptorUuid;
    private String descriptorName;
    private String levelOfDetailUuid;
    private String useYn;
}
