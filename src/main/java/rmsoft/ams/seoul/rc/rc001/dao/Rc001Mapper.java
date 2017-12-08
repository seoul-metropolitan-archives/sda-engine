package rmsoft.ams.seoul.rc.rc001.dao;

import io.onsemiro.core.mybatis.MyBatisMapper;
import rmsoft.ams.seoul.rc.rc001.vo.Rc00101VO;
import rmsoft.ams.seoul.rc.rc001.vo.Rc00102VO;
import rmsoft.ams.seoul.rc.rc001.vo.Rc00103VO;
import rmsoft.ams.seoul.rc.rc001.vo.Rc00104VO;

import java.util.List;
import java.util.Map;

/**
 * The interface Rc 001 mapper.
 */
public interface Rc001Mapper extends MyBatisMapper {
    /**
     * Gets aggregation node.
     *
     * @param param the param
     * @return the aggregation node
     */
    List<Rc00101VO> getAggregationNode(Rc00101VO param);

    /**
     * Gets item node.
     *
     * @param param the param
     * @return the item node
     */
    List<Rc00101VO> getItemNode(Rc00101VO param);

    /**
     * Gets total icon cnt.
     *
     * @param param the param
     * @return the total icon cnt
     */
    long getTotalIconCnt(Rc00104VO param);

    /**
     * Gets icon list.
     *
     * @param param the param
     * @return the icon list
     */
    List<Rc00101VO> getIconList(Rc00104VO param);

    /**
     * Gets aggregation info.
     *
     * @param param the param
     * @return the aggregation info
     */
    Rc00102VO getAggregationInfo(Rc00101VO param);

    /**
     * Gets item info.
     *
     * @param param the param
     * @return the item info
     */
    Rc00102VO getItemInfo(Rc00101VO param);

    /**
     * Gets grid data in aggregation.
     *
     * @param param the param
     * @return the grid data in aggregation
     */
    List<Rc00103VO> getGridDataInAggregation(Rc00101VO param);

    /**
     * Gets grid data in item.
     *
     * @param param the param
     * @return the grid data in item
     */
    List<Rc00103VO> getGridDataInItem(Rc00101VO param);

    /**
     * Save int.
     *
     * @param param the param
     * @return the int
     */
    int save(Map<String,String> param);

    /**
     * Gets menu.
     *
     * @param param the param
     * @return the menu
     */
    Object getMenu(Map<String,String> param);
}
