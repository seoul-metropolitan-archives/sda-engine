package rmsoft.ams.seoul.rs.rs002.dao;

import io.onsemiro.core.mybatis.MyBatisMapper;
import rmsoft.ams.seoul.rs.rs002.vo.Rs00201VO;

import java.util.List;


public interface Rs002Mapper extends MyBatisMapper {

    List<Rs00201VO> getRsTriggerList(Rs00201VO rs00201VO);
    int getRelatedData(String triggerUuid);

}