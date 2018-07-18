package rmsoft.ams.seoul.rc.rc006.dao;

import io.onsemiro.core.mybatis.MyBatisMapper;
import org.apache.ibatis.annotations.Mapper;
import rmsoft.ams.seoul.rc.rc006.vo.Rc00601VO;

import java.util.List;

/**
 * The interface Rc 006 mapper.
 */
@Mapper
public interface Rc006Mapper extends MyBatisMapper
{
    /**
     * Search level of description list.
     *
     * @param param the param
     * @return the list
     */
    List<Rc00601VO> searchLevelOfDescription(Rc00601VO param);

}
