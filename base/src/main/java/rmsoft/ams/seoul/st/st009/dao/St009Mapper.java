package rmsoft.ams.seoul.st.st009.dao;

import io.onsemiro.core.mybatis.MyBatisMapper;
import rmsoft.ams.seoul.st.st009.vo.St00901VO;
import rmsoft.ams.seoul.st.st009.vo.St00902VO;

import java.util.List;

public interface St009Mapper extends MyBatisMapper {

    List<St00901VO> getTakeoutRequest(St00901VO st00901VO);

    List<St00902VO> getTakeoutRecordResult(St00902VO st00902VO);
}
