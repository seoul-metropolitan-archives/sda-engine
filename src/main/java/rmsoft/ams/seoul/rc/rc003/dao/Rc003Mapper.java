package rmsoft.ams.seoul.rc.rc003.dao;

import io.onsemiro.core.mybatis.MyBatisMapper;
import rmsoft.ams.seoul.rc.rc003.vo.Rc00301VO;
import java.util.List;

public interface Rc003Mapper extends MyBatisMapper {
    List<Rc00301VO> getRecordAggregationList(Rc00301VO rc00301VO);
}
