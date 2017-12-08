package rmsoft.ams.seoul.cl.cl001.vo;

import io.onsemiro.core.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * The type Cl 00102 vo.
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Cl00102VO extends BaseVO {

    private String classificationSchemeUuid ;

    private String managerOrganization;

    private String manager;

    private String basedOn;

    private String useYes;

    private String useNo;

    private String aggregationCnt;

    private String itemCnt;
}
