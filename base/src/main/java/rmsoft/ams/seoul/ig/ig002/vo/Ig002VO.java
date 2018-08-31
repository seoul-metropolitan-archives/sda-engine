package rmsoft.ams.seoul.ig.ig002.vo;

import io.onsemiro.core.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * The type Cl 00101 vo.
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Ig002VO extends BaseVO {
    Ig00201VO accessionRecord;
    List<Ig00202VO> childrenMngInfoList;
    List<Ig00202VO> childrenDrnInfoList;
}
