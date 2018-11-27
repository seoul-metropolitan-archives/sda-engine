package rmsoft.ams.seoul.rs.rs004.dao;

import io.onsemiro.core.mybatis.MyBatisMapper;
import rmsoft.ams.seoul.rs.rs004.vo.Rs00401VO;
import rmsoft.ams.seoul.rs.rs004.vo.Rs00402VO;

import java.util.List;


public interface Rs004Mapper extends MyBatisMapper {
    List<Rs00401VO> getRecordScheduleResultList(Rs00401VO rs00401VO);
    List<Rs00401VO> getRecordScheduleList();
    List<Rs00402VO> getRecordScheduleAggregationList(Rs00402VO rs00402VO);
}