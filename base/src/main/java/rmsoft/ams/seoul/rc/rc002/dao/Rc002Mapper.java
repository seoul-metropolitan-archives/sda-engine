package rmsoft.ams.seoul.rc.rc002.dao;

import io.onsemiro.core.mybatis.MyBatisMapper;
import rmsoft.ams.seoul.rc.rc002.vo.Rc00204VO;

import java.util.List;

public interface Rc002Mapper extends MyBatisMapper
{
    List<Rc00204VO> getTreeData(Rc00204VO param);
}
