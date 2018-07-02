package rmsoft.ams.seoul.ad.ad008.dao;

import io.onsemiro.core.mybatis.MyBatisMapper;
import rmsoft.ams.seoul.ad.ad008.vo.Ad00801VO;

import java.util.List;


public interface Ad008Mapper extends MyBatisMapper {

    /**
     * Search entity type list.
     *
     * @param param the param
     * @return the list
     */
    List<Ad00801VO> searchList(Ad00801VO param);
}