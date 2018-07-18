package rmsoft.ams.seoul.ad.ad003.dao;

import io.onsemiro.core.mybatis.MyBatisMapper;
import rmsoft.ams.seoul.ad.ad003.vo.Ad00301VO;
import rmsoft.ams.seoul.ad.ad003.vo.Ad00302VO;
import rmsoft.ams.seoul.ad.ad003.vo.Ad00303VO;

import java.util.List;

/**
 * The interface Ad 003 mapper.
 */
public interface Ad003Mapper extends MyBatisMapper {


    /**
     * Select detail list.
     *
     * @param param the param
     * @return the list
     */
    List<Ad00301VO> selectDetail(Ad00301VO param);

    /**
     * Search code header list.
     *
     * @param param the param
     * @return the list
     */
    List<Ad00301VO> searchCodeHeader(Ad00301VO param);

    /**
     * Gets code detail list.
     *
     * @param param the param
     * @return the code detail list
     */
    List<Ad00302VO> getCodeDetailList(Ad00302VO param);

    /**
     * Gets code.
     *
     * @param param the param
     * @return the code
     */
    List<Ad00303VO> getCode(Ad00303VO param);

}
