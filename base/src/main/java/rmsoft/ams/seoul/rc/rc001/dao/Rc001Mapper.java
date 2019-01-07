package rmsoft.ams.seoul.rc.rc001.dao;

import io.onsemiro.core.mybatis.MyBatisMapper;
import rmsoft.ams.seoul.common.domain.RcAggregation;
import rmsoft.ams.seoul.rc.rc001.vo.*;

import java.math.BigDecimal;
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
     * Gets total grid cnt.
     *
     * @param param the param
     * @return the total grid cnt
     */
    long getTotalGridCnt(Rc00104VO param);

    /**
     * Gets grid list.
     *
     * @param param the param
     * @return the grid list
     */
    List<Rc00103VO> getGridList(Rc00104VO param);

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


    /**
     * Gets navi data.
     *
     * @param param the param
     * @return the navi data
     */
    List<Rc00101VO> getNaviData (Rc00101VO param);

    /**
     * Delete aggregation int.
     *
     * @param param the param
     * @return the int
     */
    int deleteAggregation(Rc00101VO param);

    /**
     * Gets children cnt.
     *
     * @param param the param
     * @return the children cnt
     */
    long getChildrenCnt (Rc00101VO param);

    /**
     * Check published int.
     *
     * @param param the param
     * @return the int
     */
    int checkPublished(Rc00101VO param);

    /**
     * Move int.
     *
     * @param data the data
     * @return the int
     */
    int move(Rc00106VO data);

    /**
     * Update state int.
     *
     * @param data the data
     * @return the int
     */
    int updateState(RcAggregation data);

    /**
     * Gets bottom aggregations.
     *
     * @param param the param
     * @return the bottom aggregations
     */
    List<Rc00101VO> getBottomAggregations (Rc00101VO param);

    /**
     * Gets item aggregation cnt.
     *
     * @param uuid the uuid
     * @return the item aggregation cnt
     */
    Map<String, BigDecimal> getItemAggregationCnt(String uuid);


    /**
     * Gets total Search cnt.
     *
     * @param param the param
     * @return the total grid cnt
     */
    long getTotalSearchCnt(Rc00107VO param);
    /**
     * Gets Search list.
     *
     * @param param the param
     * @return the grid list
     */
    List<Rc00107VO> getSearchList(Rc00107VO param);
}
