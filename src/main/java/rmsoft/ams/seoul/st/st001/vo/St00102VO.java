package rmsoft.ams.seoul.st.st001.vo;

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
public class St00102VO extends BaseVO {

    private String shelfUuid;
    private String repositoryUuid;
    private String statusUuid ;
    private String shelfCode;
    private String shelfName;
    private String useYn;
    private String changeStatus;
}
