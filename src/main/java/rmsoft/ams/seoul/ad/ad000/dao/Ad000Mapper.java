package rmsoft.ams.seoul.ad.ad000.dao;

import io.onsemiro.core.mybatis.MyBatisMapper;
import rmsoft.ams.seoul.ad.ad000.domain.Ad000;

import java.util.List;

public interface Ad000Mapper extends MyBatisMapper
{
    List<Ad000> getServiceList();
}
