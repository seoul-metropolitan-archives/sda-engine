package rmsoft.ams.seoul.rc.rc002.vo;

import io.onsemiro.core.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * The type Rc 002 vo.
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class Rc002VO extends BaseVO
{
    /**
     * The System meta.
     */
    Rc00201VO systemMeta;
    /**
     * The Contextual meta.
     */
    Rc00202VO contextualMeta;
    /**
     * The Children aggregation list.
     */
    List<Rc00201VO> childrenAggregationList;
    /**
     * The Reference aggregation list.
     */
    List<Rc00206VO> referenceAggregationList;
    /**
     * The Reference item list.
     */
    List<Rc00206VO> referenceItemList;

}
