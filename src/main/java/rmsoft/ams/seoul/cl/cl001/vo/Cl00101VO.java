package rmsoft.ams.seoul.cl.cl001.vo;

import io.onsemiro.core.vo.BaseVO;
import lombok.*;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Cl00101VO extends BaseVO {

    private String classificationSchemeUuid ;
    private String statusUuid;
    private String classificationCode;
    private String classificationName;
    private String classificationTypeUuid;
    private int orderNo;
    private String useYn;
    private String changeStatus;

}
