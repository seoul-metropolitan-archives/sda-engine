package rmsoft.ams.seoul.ig.ig002.vo;

import io.onsemiro.core.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * The type Cl 00101 vo.
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Ig00201VO extends BaseVO {

    private String accessionRecordUuid;
    private String accessionNo;
    private String acquisitionDate;
    private String acquisitionFromDate;
    private String acquisitionToDate;
    private String acquisitionSource;
    private String acquisitionTypeUuid;
    private String inputCodeUuid;
    private String title;

}
