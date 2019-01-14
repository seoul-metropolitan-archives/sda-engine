package rmsoft.ams.seoul.st.st004.vo;

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
public class St00402VO extends BaseVO {

    private String locationUuid;
    private String shelfUuid;
    private String statusUuid ;
    private String rowNo;
    private String columnNo;
    private String totalContainer;
    private String useYn;
    private String changeStatus;
}
