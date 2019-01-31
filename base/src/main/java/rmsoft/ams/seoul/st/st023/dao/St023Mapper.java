package rmsoft.ams.seoul.st.st023.dao;

import io.onsemiro.core.mybatis.MyBatisMapper;
import rmsoft.ams.seoul.st.st023.vo.St02301VO;

import java.util.List;

public interface St023Mapper extends MyBatisMapper {
    List<St02301VO> getInoutList(St02301VO st02301VO);
}
