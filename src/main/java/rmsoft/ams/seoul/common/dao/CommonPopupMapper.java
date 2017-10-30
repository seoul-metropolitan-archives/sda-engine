package rmsoft.ams.seoul.common.dao;

import io.onsemiro.core.mybatis.MyBatisMapper;
import org.apache.ibatis.annotations.Mapper;
import rmsoft.ams.seoul.common.domain.AdPopupDetail;
import rmsoft.ams.seoul.common.domain.AdPopupHeader;
import rmsoft.ams.seoul.common.vo.BaseColumnVO;

import java.util.List;

@Mapper
public interface CommonPopupMapper extends MyBatisMapper
{
    List<BaseColumnVO> getColumnInfo(AdPopupHeader adPopupHeader);
}
