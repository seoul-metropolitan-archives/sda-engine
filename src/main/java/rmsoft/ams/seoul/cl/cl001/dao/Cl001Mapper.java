package rmsoft.ams.seoul.cl.cl001.dao;

import io.onsemiro.core.mybatis.MyBatisMapper;
import rmsoft.ams.seoul.cl.cl001.vo.Cl00101VO;

import java.util.List;

public interface Cl001Mapper extends MyBatisMapper {
    List<Cl00101VO> getClassificationSchemeList(Cl00101VO cl00101VO);
}
