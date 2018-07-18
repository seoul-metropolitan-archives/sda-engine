package rmsoft.ams.seoul.ad.ad006.dao;

import io.onsemiro.core.mybatis.MyBatisMapper;
import org.apache.ibatis.annotations.Mapper;
import rmsoft.ams.seoul.ad.ad006.vo.Ad00601VO;
import rmsoft.ams.seoul.ad.ad006.vo.Ad00602VO;
import rmsoft.ams.seoul.common.domain.AdEntityColumn;

import java.util.List;

/**
 * The interface Ad 006 mapper.
 */
@Mapper
public interface Ad006Mapper extends MyBatisMapper
{
    /**
     * Search entity type list.
     *
     * @param param the param
     * @return the list
     */
    List<Ad00601VO> searchEntityType(Ad00601VO param);

    /**
     * Gets entity column list.
     *
     * @param param the param
     * @return the entity column list
     */
    List<Ad00602VO> getEntityColumnList(Ad00601VO param);

    /**
     * Check entity type int.
     *
     * @param param the param
     * @return the int
     */
    int checkEntityType(String param);

    /**
     * Check entity column int.
     *
     * @param adEntityColumn the ad entity column
     * @return the int
     */
    int checkEntityColumn(AdEntityColumn adEntityColumn);
}
