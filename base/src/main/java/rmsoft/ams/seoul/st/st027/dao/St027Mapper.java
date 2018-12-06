package rmsoft.ams.seoul.st.st027.dao;

import io.onsemiro.core.mybatis.MyBatisMapper;
import rmsoft.ams.seoul.st.st027.vo.St02701VO;

import java.util.List;

public interface St027Mapper extends MyBatisMapper {

    List<St02701VO> getStZone(St02701VO st02701VO);
}
