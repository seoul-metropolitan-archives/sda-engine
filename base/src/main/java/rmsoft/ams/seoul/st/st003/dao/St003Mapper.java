package rmsoft.ams.seoul.st.st003.dao;

import io.onsemiro.core.mybatis.MyBatisMapper;
import rmsoft.ams.seoul.rc.rc001.vo.Rc00101VO;
import rmsoft.ams.seoul.st.st003.vo.St00301VO;
import rmsoft.ams.seoul.st.st003.vo.St00302VO;
import rmsoft.ams.seoul.st.st003.vo.St00303VO;

import java.util.Collection;
import java.util.List;


public interface St003Mapper extends MyBatisMapper {
    List<St00301VO> getContainerAggregationList(St00301VO st00301VO);
    List<St00301VO> getContainerItemList(St00301VO st00301VO);
    List<St00302VO> getAggregationHierarchyList(St00301VO st00301VO);
    List<St00303VO> getSelectedItem(St00303VO st00303VO);

    List<Rc00101VO> getAggregationNode(Rc00101VO param);
}