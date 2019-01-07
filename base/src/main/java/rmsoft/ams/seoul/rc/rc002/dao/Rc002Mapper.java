package rmsoft.ams.seoul.rc.rc002.dao;

import io.onsemiro.core.mybatis.MyBatisMapper;
import rmsoft.ams.seoul.rc.rc002.vo.Rc00204VO;

import java.util.List;

/**
 * The interface Rc 002 mapper.
 */
public interface Rc002Mapper extends MyBatisMapper
{
    /**
     * Gets tree data.
     *
     * @param param the param
     * @return the tree data
     */
    List<Rc00204VO> getTreeData(Rc00204VO param);
}
