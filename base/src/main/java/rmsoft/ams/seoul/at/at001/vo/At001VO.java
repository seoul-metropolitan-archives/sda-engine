package rmsoft.ams.seoul.at.at001.vo;

import io.onsemiro.core.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * The type Cl 00201 vo.
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class At001VO extends BaseVO {

    private At00101VO authInfo;
    private List<At00102VO> relAuthList;
}
