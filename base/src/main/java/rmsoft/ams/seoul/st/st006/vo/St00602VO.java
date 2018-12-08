package rmsoft.ams.seoul.st.st006.vo;

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
public class St00602VO extends BaseVO
{

    private String classUuid;
    private List<St00601VO> st00601VOList;

}
