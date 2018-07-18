package rmsoft.ams.seoul.cl.cl001.dao;

import io.onsemiro.core.mybatis.MyBatisMapper;
import rmsoft.ams.seoul.cl.cl001.vo.Cl00101VO;
import rmsoft.ams.seoul.cl.cl001.vo.Cl00102VO;

import java.util.List;

/**
 * The interface Cl 001 mapper.
 */
public interface Cl001Mapper extends MyBatisMapper {
    /**
     * Gets classification scheme list.
     *
     * @param cl00101VO the cl 00101 vo
     * @return the classification scheme list
     */
    List<Cl00101VO> getClassificationSchemeList(Cl00101VO cl00101VO);

    /**
     * Gets classification scheme detail.
     *
     * @param cl00102VO the cl 00102 vo
     * @return the classification scheme detail
     */
    Cl00102VO getClassificationSchemeDetail(Cl00102VO cl00102VO);

    /**
     * Gets max classification code.
     *
     * @param cl00101VO the cl 00101 vo
     * @return the max classification code
     */
    String getMaxClassificationCode(Cl00101VO cl00101VO);

    /**
     * Gets child class.
     *
     * @param classificationSchemeUuid the classification scheme uuid
     * @return the child class
     */
    int getChildClass(String classificationSchemeUuid);
}
