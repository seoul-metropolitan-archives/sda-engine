package rmsoft.ams.seoul.cl.cl003.dao;

import io.onsemiro.core.mybatis.MyBatisMapper;
import rmsoft.ams.seoul.cl.cl003.vo.Cl003;
import rmsoft.ams.seoul.cl.cl003.vo.Cl00301VO;

import java.util.List;

/**
 * The interface Cl 003 mapper.
 */
public interface Cl003Mapper extends MyBatisMapper
{
    /**
     * Gets classification scheme list.
     *
     * @param cl00301VO the cl 00301 vo
     * @return the classification scheme list
     */
    List<Cl003> getClassificationSchemeList(Cl00301VO cl00301VO);
}
