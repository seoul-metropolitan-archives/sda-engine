package rmsoft.ams.seoul.st.st011.dao;

import io.onsemiro.core.mybatis.MyBatisMapper;
import rmsoft.ams.seoul.st.st011.vo.St01101VO;
import rmsoft.ams.seoul.st.st011.vo.St01102VO;

import java.util.List;


public interface St011Mapper extends MyBatisMapper {

    List<St01101VO> getStInoutExcept(St01101VO st01101VO);

    List<St01102VO> getStExceptRecordResult(St01102VO st01102VO);
}