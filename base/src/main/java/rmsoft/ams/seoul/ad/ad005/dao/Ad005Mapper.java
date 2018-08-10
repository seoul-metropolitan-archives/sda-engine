package rmsoft.ams.seoul.ad.ad005.dao;

import io.onsemiro.core.mybatis.MyBatisMapper;
import org.apache.ibatis.annotations.Mapper;
import rmsoft.ams.seoul.ad.ad005.vo.Ad00501VO;
import rmsoft.ams.seoul.ad.ad005.vo.Ad00502VO;

import java.util.List;

/**
 * The interface Ad 005 mapper.
 */
@Mapper
public interface Ad005Mapper extends MyBatisMapper
{
    /**
     * Search entity type list.
     *
     * @param param the param
     * @return the list
     */
    List<Ad00501VO> searchGlossary(Ad00501VO param);

    /**
     * Gets entity column list.
     *
     * @param param the param
     * @return the entity column list
     */
    List<Ad00502VO> getEntityColumnList(Ad00502VO param);

    /**
     * Check entity type int.
     *
     * @param param the param
     * @return the int
     */
    int checkGlossary(String param);

}
