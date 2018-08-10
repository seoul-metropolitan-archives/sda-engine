package rmsoft.ams.seoul.cl.cl003.vo;

import io.onsemiro.core.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * The type Cl 00301 vo.
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Cl00301VO extends BaseVO
{
    private String classifyRecordsUuid ;
    private String classUuid;
    private String statusUuid;
    private String title;
    private String aggregationTree;
    private String classifiedDate;
    private String aggregationUuid;
    private String itemUuid;
    private String changeStatus;
}
