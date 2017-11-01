package rmsoft.ams.seoul.common.dao;

import io.onsemiro.core.mybatis.MyBatisMapper;
import org.apache.ibatis.annotations.Mapper;
import rmsoft.ams.seoul.common.domain.AdPopupDetail;
import rmsoft.ams.seoul.common.domain.AdPopupHeader;
import rmsoft.ams.seoul.common.vo.BaseColumnVO;

import java.util.List;
import java.util.Map;

@Mapper
public interface CommonPopupMapper extends MyBatisMapper
{
    List<BaseColumnVO> getColumnInfo(AdPopupHeader adPopupHeader);
    String getSQL(Map<String,Object> param);
    AdPopupHeader popupInfo(AdPopupHeader adPopupHeader);
}
