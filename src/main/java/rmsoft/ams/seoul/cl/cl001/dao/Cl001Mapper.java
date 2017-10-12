package rmsoft.ams.seoul.cl.cl001.dao;

import io.onsemiro.core.mybatis.MyBatisMapper;
import rmsoft.ams.seoul.cl.cl001.domain.Cl001;

import java.util.List;

public interface Cl001Mapper extends MyBatisMapper
{
    List<Cl001> getServiceList();
}
