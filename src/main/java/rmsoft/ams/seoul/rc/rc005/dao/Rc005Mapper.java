package rmsoft.ams.seoul.rc.rc005.dao;

import io.onsemiro.core.mybatis.MyBatisMapper;
import rmsoft.ams.seoul.rc.rc005.vo.Rc00501VO;
import rmsoft.ams.seoul.rc.rc005.vo.Rc00502VO;

import java.util.List;

public interface Rc005Mapper extends MyBatisMapper {
    List<Rc00501VO> getRecordItemList(Rc00501VO rc00501VO);
    List<Rc00502VO> getRecordComponentList(Rc00502VO rc00502VO);
}
