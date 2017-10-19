package rmsoft.ams.seoul.cl.cl002.dao;

import io.onsemiro.core.mybatis.MyBatisMapper;
import rmsoft.ams.seoul.cl.cl002.vo.Cl00201VO;

import java.util.List;

public interface Cl002Mapper extends MyBatisMapper
{
    List<Cl00201VO> getClassList(Cl00201VO cl00201VO);
}
