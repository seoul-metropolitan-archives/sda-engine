package rmsoft.ams.seoul.rs.rs005.dao;

import io.onsemiro.core.mybatis.MyBatisMapper;
import rmsoft.ams.seoul.rs.rs005.vo.Rs005;
import rmsoft.ams.seoul.rs.rs005.vo.Rs00501VO;

import java.util.List;


public interface Rs005Mapper extends MyBatisMapper {
    List<Rs00501VO> getDisposalRecordList(Rs00501VO rs00501VO);
}