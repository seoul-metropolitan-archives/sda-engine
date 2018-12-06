package rmsoft.ams.seoul.st.st025.dao;

import io.onsemiro.core.mybatis.MyBatisMapper;
import rmsoft.ams.seoul.st.st025.vo.St02501VO;

import java.util.List;

public interface St025Mapper extends MyBatisMapper {

    List<St02501VO> getReaderMachine(St02501VO st02501VO);
}
