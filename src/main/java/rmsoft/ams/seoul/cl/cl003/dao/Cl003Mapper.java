package rmsoft.ams.seoul.cl.cl003.dao;

import io.onsemiro.core.mybatis.MyBatisMapper;
import rmsoft.ams.seoul.cl.cl003.vo.Cl003;
import rmsoft.ams.seoul.cl.cl003.vo.Cl00301VO;

import java.util.List;

public interface Cl003Mapper extends MyBatisMapper
{
    List<Cl003> getClassificationSchemeList(Cl00301VO cl00301VO);
}
