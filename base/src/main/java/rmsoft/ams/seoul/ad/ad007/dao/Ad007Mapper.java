package rmsoft.ams.seoul.ad.ad007.dao;

import io.onsemiro.core.mybatis.MyBatisMapper;
import rmsoft.ams.seoul.ad.ad007.vo.Ad00701VO;
import rmsoft.ams.seoul.ad.ad007.vo.Ad00702VO;

import java.util.List;


public interface Ad007Mapper extends MyBatisMapper {

    /**
     * Search entity type list.
     *
     * @param param the param
     * @return the list
     */
    List<Ad00701VO> searchSetup(Ad00701VO param);
    /**
     * Search entity type list.
     *
     * @param param the param
     * @return the list
     */
    List<Ad00702VO> searchSegment(Ad00702VO param);

    /**
     * Gets child class.
     *
     * @param uuid the Uuid uuid
     * @return the child class
     */
    int checkDelete(String uuid);
}