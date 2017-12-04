package rmsoft.ams.seoul.rc.rc002.vo;

import io.onsemiro.core.vo.BaseVO;
import io.onsemiro.utils.UUIDUtils;
import lombok.Data;

import java.util.List;

@Data
public class Rc002VO extends BaseVO
{
    Rc00201VO systemMeta;
    Rc00202VO contextualMeta;
    List<Rc00201VO> childrenAggregationList;
    List<Rc00201VO> referenceAggregationList;
    List<Rc00203VO> referenceItemList;

}
