package rmsoft.ams.seoul.common.dao;

import io.onsemiro.core.mybatis.MyBatisMapper;
import org.apache.ibatis.annotations.Mapper;
import rmsoft.ams.seoul.common.domain.AdPopupHeader;
import rmsoft.ams.seoul.common.vo.BaseColumnVO;

import java.util.List;
import java.util.Map;

/**
 * The interface Common popup mapper.
 */
@Mapper
public interface CommonPopupMapper extends MyBatisMapper
{
    /**
     * Gets column info.
     *
     * @param adPopupHeader the ad popup header
     * @return the column info
     */
    List<BaseColumnVO> getColumnInfo(AdPopupHeader adPopupHeader);

    /**
     * Gets sql.
     *
     * @param param the param
     * @return the sql
     */
    String getSQL(Map<String,Object> param);

    /**
     * Popup info ad popup header.
     *
     * @param adPopupHeader the ad popup header
     * @return the ad popup header
     */
    AdPopupHeader popupInfo(AdPopupHeader adPopupHeader);
}
