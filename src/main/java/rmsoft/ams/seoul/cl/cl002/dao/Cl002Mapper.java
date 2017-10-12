package rmsoft.ams.seoul.cl.cl002.dao;

import io.onsemiro.core.mybatis.MyBatisMapper;
import rmsoft.ams.seoul.cl.cl002.domain.Cl002;

import java.util.List;

public interface Cl002Mapper extends MyBatisMapper
{
    List<Cl002> getServiceList();
}
