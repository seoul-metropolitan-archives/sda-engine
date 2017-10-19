package rmsoft.ams.seoul.cl.cl003.vo;

import io.onsemiro.core.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Cl00301VO extends BaseVO
{
    private String classifiedRecordsUuid ;
    private String statusUuid;
    private String classUuid;
    private String aggregationId;
    private String itemUuid;
    private Timestamp classifiedDate;
}
