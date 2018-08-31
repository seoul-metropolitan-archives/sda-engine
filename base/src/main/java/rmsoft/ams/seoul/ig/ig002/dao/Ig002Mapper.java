package rmsoft.ams.seoul.ig.ig002.dao;

import io.onsemiro.core.mybatis.MyBatisMapper;
import rmsoft.ams.seoul.ig.ig002.vo.Ig00201VO;
import rmsoft.ams.seoul.ig.ig002.vo.Ig00202VO;
import rmsoft.ams.seoul.st.st001.vo.St00101VO;
import rmsoft.ams.seoul.st.st001.vo.St00102VO;
import rmsoft.ams.seoul.st.st001.vo.St00103VO;

import java.util.List;


public interface Ig002Mapper extends MyBatisMapper {
    Ig00201VO getIgAccessionNo();
    Ig00201VO getIgAccessionRecord(Ig00201VO ig00201VO);
    List<Ig00202VO> getChildrenDrnInfoList(Ig00201VO ig00201VO);
    List<Ig00202VO> getChildrenMngInfoList(Ig00201VO ig00201VO);
}