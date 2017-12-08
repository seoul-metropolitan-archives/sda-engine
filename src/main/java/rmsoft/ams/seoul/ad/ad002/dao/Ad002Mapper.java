package rmsoft.ams.seoul.ad.ad002.dao;

import io.onsemiro.core.mybatis.MyBatisMapper;
import rmsoft.ams.seoul.ad.ad002.vo.Ad00201VO;

import java.util.List;

/**
 * The interface Ad 002 mapper.
 */
public interface Ad002Mapper extends MyBatisMapper {


    /**
     * Select list.
     *
     * @param param the param
     * @return the list
     */
    List<Ad00201VO> select(Ad00201VO param);

}
