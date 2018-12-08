package rmsoft.ams.seoul.st.st008.vo;

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
public class St00802VO extends BaseVO
{

    private String classUuid;
    private List<St00801VO> st00801VOList;

}
