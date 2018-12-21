package rmsoft.ams.seoul.st.st028.dao;

import io.onsemiro.core.mybatis.MyBatisMapper;
import rmsoft.ams.seoul.st.st028.vo.St02801VO;

import java.util.List;

public interface St028Mapper extends MyBatisMapper {

    List<St02801VO> getStGate(St02801VO st02801VO);
}
