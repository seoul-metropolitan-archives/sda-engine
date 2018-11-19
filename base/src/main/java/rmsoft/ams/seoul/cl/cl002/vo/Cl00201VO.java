package rmsoft.ams.seoul.cl.cl002.vo;

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
public class Cl00201VO extends BaseVO {

    private String classUuid ;
    private String classificationSchemeUuid;
    private String statusUuid;
    private String parentClassUuid;
    private String classCode;
    private String className;
    private String classLevelUuid;
    private String orderNo;
    private String orderKey;
    private String useYn;
    private String changeStatus;
    private String parentClassName;
    private String parentClassCode;
    private String classTreeName;
    private String orderKey1;
    private String path;
    private String isLeaf;
    private String description;
    private String aggregationUuid;
    private int classifyCount;
}
