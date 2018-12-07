package rmsoft.ams.seoul.rc.rc003.dao;

import io.onsemiro.core.mybatis.MyBatisMapper;
import rmsoft.ams.seoul.rc.rc002.vo.Rc00207VO;
import rmsoft.ams.seoul.rc.rc002.vo.Rc00209VO;
import rmsoft.ams.seoul.rc.rc003.vo.Rc00301VO;

import java.util.List;

/**
 * The interface Rc 003 mapper.
 */
public interface Rc003Mapper extends MyBatisMapper {
    /**
     * Gets record aggregation list.
     *
     * @param rc00301VO the rc 00301 vo
     * @return the record aggregation list
     */
    List<Rc00301VO> getRecordAggregationList(Rc00301VO rc00301VO);

    List<Rc00207VO> getCreatorList(String aggreagtionUuid);

    List<Rc00209VO> getRelatedAuthorityList(String aggreagtionUuid);

}
