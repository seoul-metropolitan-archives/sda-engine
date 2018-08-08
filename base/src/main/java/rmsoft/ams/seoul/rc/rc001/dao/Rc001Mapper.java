package rmsoft.ams.seoul.rc.rc001.dao;

import io.onsemiro.core.mybatis.MyBatisMapper;
import rmsoft.ams.seoul.common.domain.RcAggregation;
import rmsoft.ams.seoul.rc.rc001.vo.*;

import java.util.List;
import java.util.Map;

public interface Rc001Mapper extends MyBatisMapper {
    List<Rc00101VO> getAggregationNode(Rc00101VO param);
    List<Rc00101VO> getItemNode(Rc00101VO param);

    long getTotalIconCnt(Rc00104VO param);
    List<Rc00101VO> getIconList(Rc00104VO param);

    Rc00102VO getAggregationInfo(Rc00101VO param);
    Rc00102VO getItemInfo(Rc00101VO param);
    List<Rc00103VO> getGridDataInAggregation(Rc00101VO param);
    List<Rc00103VO> getGridDataInItem(Rc00101VO param);

    long getTotalGridCnt(Rc00104VO param);
    List<Rc00103VO> getGridList(Rc00104VO param);

    int save(Map<String,String> param);

    Object getMenu(Map<String,String> param);


    List<Rc00101VO> getNaviData (Rc00101VO param);

    int deleteAggregation(Rc00101VO param);

    long getChildrenCnt (Rc00101VO param);
    int checkPublished(Rc00101VO param);
    int move(Rc00106VO data);
    int updateState(RcAggregation data);
}
