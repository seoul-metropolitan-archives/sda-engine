package rmsoft.ams.seoul.rc.rc005.dao;

import io.onsemiro.core.mybatis.MyBatisMapper;
import rmsoft.ams.seoul.rc.rc005.vo.Rc00501VO;
import rmsoft.ams.seoul.rc.rc005.vo.Rc00502VO;

import java.util.List;

/**
 * The interface Rc 005 mapper.
 */
public interface Rc005Mapper extends MyBatisMapper {
    /**
     * Gets record item list.
     *
     * @param rc00501VO the rc 00501 vo
     * @return the record item list
     */
    List<Rc00501VO> getRecordItemList(Rc00501VO rc00501VO);

    /**
     * Gets record component list.
     *
     * @param rc00502VO the rc 00502 vo
     * @return the record component list
     */
    List<Rc00502VO> getRecordComponentList(Rc00502VO rc00502VO);
}
