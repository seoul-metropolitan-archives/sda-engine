package rmsoft.ams.seoul.ad.ad008.dao;

import io.onsemiro.core.mybatis.MyBatisMapper;
import rmsoft.ams.seoul.ad.ad008.vo.Ad008VO;

import java.util.List;


public interface Ad008Mapper extends MyBatisMapper {

    /**
     * Search entity type list.
     *
     * @param param the param
     * @return the list
     */
    List<Ad008VO> searchAuditList(Ad008VO param);
}