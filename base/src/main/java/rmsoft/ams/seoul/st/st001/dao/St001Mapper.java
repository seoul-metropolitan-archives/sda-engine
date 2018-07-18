package rmsoft.ams.seoul.st.st001.dao;

import io.onsemiro.core.mybatis.MyBatisMapper;
import rmsoft.ams.seoul.st.st001.vo.St00101VO;
import rmsoft.ams.seoul.st.st001.vo.St00102VO;
import rmsoft.ams.seoul.st.st001.vo.St00103VO;

import java.util.List;


public interface St001Mapper extends MyBatisMapper {

    List<St00101VO> getStRepositoryList(St00101VO st00101VO);
    List<St00102VO> getStShelfList(St00102VO st00102VO);
    List<St00103VO> getLocationList(St00103VO st00103VO);

}