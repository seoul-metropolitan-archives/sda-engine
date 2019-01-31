package rmsoft.ams.seoul.st.st022.dao;

import io.onsemiro.core.mybatis.MyBatisMapper;
import rmsoft.ams.seoul.st.st022.vo.St02201VO;

import java.util.List;

public interface St022Mapper extends MyBatisMapper {
    List<St02201VO> getInventoryPlanList(St02201VO st02201VO);
}
