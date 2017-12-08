package rmsoft.ams.seoul.ad.ad004.dao;

import io.onsemiro.core.mybatis.MyBatisMapper;
import org.apache.ibatis.annotations.Mapper;
import rmsoft.ams.seoul.ad.ad004.vo.Ad00401VO;
import rmsoft.ams.seoul.ad.ad004.vo.Ad00402VO;

import java.util.List;

/**
 * The interface Ad 004 mapper.
 */
@Mapper
public interface Ad004Mapper extends MyBatisMapper
{
    /**
     * Search popup header list.
     *
     * @param param the param
     * @return the list
     */
    List<Ad00401VO> searchPopupHeader(Ad00401VO param);

    /**
     * Gets popup detail.
     *
     * @param param the param
     * @return the popup detail
     */
    List<Ad00402VO> getPopupDetail(Ad00402VO param);

    /**
     * Insert popup header int.
     *
     * @param data the data
     * @return the int
     */
    int insertPopupHeader(Ad00401VO data);

    /**
     * Update popup header int.
     *
     * @param data the data
     * @return the int
     */
    int updatePopupHeader(Ad00401VO data);

    /**
     * Delete popup header int.
     *
     * @param data the data
     * @return the int
     */
    int deletePopupHeader(Ad00401VO data);

    /**
     * Insert popup sql int.
     *
     * @param data the data
     * @return the int
     */
    int insertPopupSQL(Ad00401VO data);

    /**
     * Insert popup detail int.
     *
     * @param data the data
     * @return the int
     */
    int insertPopupDetail(Ad00402VO data);

    /**
     * Update popup detail int.
     *
     * @param data the data
     * @return the int
     */
    int updatePopupDetail(Ad00402VO data);

    /**
     * Delete popup detail int.
     *
     * @param data the data
     * @return the int
     */
    int deletePopupDetail(Ad00402VO data);

}
