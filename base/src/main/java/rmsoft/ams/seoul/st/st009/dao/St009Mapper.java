package rmsoft.ams.seoul.st.st009.dao;

import io.onsemiro.core.mybatis.MyBatisMapper;
import rmsoft.ams.seoul.st.st009.vo.St00901VO;

import java.util.List;

public interface St009Mapper extends MyBatisMapper {

    List<St00901VO> getTakeoutRequest(St00901VO st00901VO);

}
