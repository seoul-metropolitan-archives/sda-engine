package rmsoft.ams.seoul.rc.rc001.dao;

import io.onsemiro.core.mybatis.MyBatisMapper;
import org.apache.ibatis.annotations.Mapper;
import rmsoft.ams.seoul.rc.rc001.vo.Rc00101VO;
import rmsoft.ams.seoul.rc.rc001.vo.Rc00102VO;
import rmsoft.ams.seoul.rc.rc001.vo.Rc00103VO;
import rmsoft.ams.seoul.rc.rc001.vo.Rc00104VO;

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

    int save(Map<String,String> param);

    Object getMenu(Map<String,String> param);
}
