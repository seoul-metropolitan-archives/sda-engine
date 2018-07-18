package rmsoft.ams.seoul.ad.ad007.dao;

import io.onsemiro.core.mybatis.MyBatisMapper;
import rmsoft.ams.seoul.ad.ad007.vo.Ad00701VO;

import java.util.List;


public interface Ad007Mapper extends MyBatisMapper {

    /**
     * Search entity type list.
     *
     * @param param the param
     * @return the list
     */
    List<Ad00701VO> searchList(Ad00701VO param);

    /**
     * Gets child class.
     *
     * @param uuid the Uuid uuid
     * @return the child class
     */
    int checkDelete(String uuid);
}