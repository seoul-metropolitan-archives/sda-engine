package rmsoft.ams.seoul.rs.rs001.dao;

import io.onsemiro.core.mybatis.MyBatisMapper;
import rmsoft.ams.seoul.rs.rs001.vo.Rs00101VO;

import java.util.List;


public interface Rs001Mapper extends MyBatisMapper {

    List<Rs00101VO> getRsGeneralRecordScheduleList(Rs00101VO rs00101VO);
    int getRelatedData(String generalRecordScheduleUuid);
}