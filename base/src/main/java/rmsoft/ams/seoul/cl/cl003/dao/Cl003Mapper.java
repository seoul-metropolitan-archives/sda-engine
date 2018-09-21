package rmsoft.ams.seoul.cl.cl003.dao;

import io.onsemiro.core.mybatis.MyBatisMapper;
import rmsoft.ams.seoul.cl.cl003.vo.Cl003;
import rmsoft.ams.seoul.cl.cl003.vo.Cl00301VO;
import rmsoft.ams.seoul.rc.rc001.vo.Rc00101VO;
import rmsoft.ams.seoul.st.st003.vo.St00303VO;

import java.util.List;

/**
 * The interface Cl 003 mapper.
 */
public interface Cl003Mapper extends MyBatisMapper
{
    /**
     * Gets classification scheme list.
     *
     * @param cl00301VO the cl 00301 vo
     * @return the classification scheme list
     */
    List<Cl003> getClassAggregationList(Cl00301VO cl00301VO);
    List<Cl003> getClassItemList(Cl00301VO cl00301VO);
    List<St00303VO> getSelectedItem(St00303VO st00303VO);
    List<Rc00101VO> getAggregationNode(Rc00101VO param);
}
