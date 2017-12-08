package rmsoft.ams.seoul.ad.ad000.dao;

import io.onsemiro.core.mybatis.MyBatisMapper;
import rmsoft.ams.seoul.ad.ad000.vo.Ad00001VO;

import java.util.List;

/**
 * The interface Ad 000 mapper.
 */
public interface Ad000Mapper extends MyBatisMapper {
    /**
     * Gets service list.
     *
     * @return the service list
     */
    List<Ad00001VO> getServiceList();
}
