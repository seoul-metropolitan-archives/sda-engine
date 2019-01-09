package rmsoft.ams.seoul.st.st011.dao;

import io.onsemiro.core.mybatis.MyBatisMapper;
import rmsoft.ams.seoul.st.st011.vo.St01101VO;
import rmsoft.ams.seoul.st.st011.vo.St01102VO;

import java.util.List;

public interface St011Mapper extends MyBatisMapper {
    List<St01101VO> getAggregation(St01101VO st01004VO);

    List<St01102VO> getTakeInOutList(St01102VO st01102VO);
}
