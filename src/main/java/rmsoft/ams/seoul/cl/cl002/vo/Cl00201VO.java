package rmsoft.ams.seoul.cl.cl002.vo;

import io.onsemiro.core.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

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
    private int orderNo;
    private String orderKey;
    private String useYn;

}
