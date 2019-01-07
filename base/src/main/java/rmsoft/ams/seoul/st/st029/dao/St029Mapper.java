package rmsoft.ams.seoul.st.st029.dao;

import io.onsemiro.core.mybatis.MyBatisMapper;
import rmsoft.ams.seoul.st.st028.vo.St02801VO;
import rmsoft.ams.seoul.st.st029.vo.St02901VO;

import java.util.List;


public interface St029Mapper extends MyBatisMapper {

    List<St02901VO> getStProgram(St02901VO st02901VO);
}
