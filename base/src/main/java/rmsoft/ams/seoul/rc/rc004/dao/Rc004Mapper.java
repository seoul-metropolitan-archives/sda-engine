package rmsoft.ams.seoul.rc.rc004.dao;

import io.onsemiro.core.mybatis.MyBatisMapper;
import rmsoft.ams.seoul.rc.rc004.vo.Rc00402VO;

/**
 * The interface Rc 004 mapper.
 */
public interface Rc004Mapper extends MyBatisMapper {
    /**
     * Save component list.
     *
     * @param rc00402VO the rc 00402 vo
     */
    void saveComponentList(Rc00402VO rc00402VO);
}
