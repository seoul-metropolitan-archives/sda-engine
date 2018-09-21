package rmsoft.ams.seoul.cl.cl003.vo;

import io.onsemiro.core.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * The type Cl 00301 vo.
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Cl00302VO extends BaseVO
{

    private String classUuid;
    private List<Cl00301VO> cl00301VOList;

}
