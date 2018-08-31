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
public class Ig00202VO extends BaseVO {

    private String accessionRecordEtcUuid;
    private String accessionRecordUuid;
    private String typeUuid;
    private String authorityUuid;
    private String name;
    private String tel;
    private String address;

}
