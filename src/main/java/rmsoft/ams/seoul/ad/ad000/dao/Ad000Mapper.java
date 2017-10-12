package rmsoft.ams.seoul.ad.ad000.dao;

import io.onsemiro.core.mybatis.MyBatisMapper;
import rmsoft.ams.seoul.ad.ad000.vo.Ad00001VO;

import java.util.List;

public interface Ad000Mapper extends MyBatisMapper {
    List<Ad00001VO> getServiceList();
}
