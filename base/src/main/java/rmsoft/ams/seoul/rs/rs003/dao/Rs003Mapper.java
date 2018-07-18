package rmsoft.ams.seoul.rs.rs003.dao;

import io.onsemiro.core.mybatis.MyBatisMapper;
import rmsoft.ams.seoul.rs.rs003.vo.Rs00301VO;

import java.util.List;


public interface Rs003Mapper extends MyBatisMapper {
    List<Rs00301VO> getRsRecordScheduleList(Rs00301VO rs00301VO);
}