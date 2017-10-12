package rmsoft.ams.seoul.cl.cl003.dao;

import io.onsemiro.core.mybatis.MyBatisMapper;
import rmsoft.ams.seoul.cl.cl003.domain.Cl003;

import java.util.List;

public interface Cl003Mapper extends MyBatisMapper
{
    List<Cl003> getServiceList();
}
