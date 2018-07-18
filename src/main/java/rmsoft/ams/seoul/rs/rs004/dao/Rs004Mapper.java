package rmsoft.ams.seoul.rs.rs004.dao;

import io.onsemiro.core.mybatis.MyBatisMapper;
import rmsoft.ams.seoul.rs.rs004.vo.Rs004;
import rmsoft.ams.seoul.rs.rs005.vo.Rs00501VO;

import java.util.List;


public interface Rs004Mapper extends MyBatisMapper {
    List<Rs00501VO> getRecordScheduleResultList(Rs00501VO rs00501VO);
}