package rmsoft.ams.seoul.st.st013.dao;

import io.onsemiro.core.mybatis.MyBatisMapper;
import rmsoft.ams.seoul.st.st013.vo.St01301VO;
import rmsoft.ams.seoul.st.st013.vo.St01302VO;

import java.util.List;


public interface St013Mapper extends MyBatisMapper {

    List<St01301VO> getStInoutExcept(St01301VO st01301VO);

    List<St01302VO> getStExceptRecordResult(St01302VO st01302VO);
}