package rmsoft.ams.seoul.cl.cl002.dao;

import io.onsemiro.core.mybatis.MyBatisMapper;
import rmsoft.ams.seoul.cl.cl001.vo.Cl00101VO;
import rmsoft.ams.seoul.cl.cl002.vo.Cl00201VO;
import rmsoft.ams.seoul.cl.cl002.vo.Cl00202VO;

import java.util.List;

/**
 * The interface Cl 002 mapper.
 */
public interface Cl002Mapper extends MyBatisMapper
{
    /**
     * Gets class list.
     *
     * @param cl00201VO the cl 00201 vo
     * @return the class list
     */
    List<Cl00201VO> getClassList(Cl00201VO cl00201VO);

    /**
     * Gets selected class list.
     *
     * @param cl00201VO the cl 00201 vo
     * @return the selected class list
     */
    List<Cl00201VO> getSelectedClassList(Cl00201VO cl00201VO);

    int getClassForClassify(Cl00201VO cl00201VO);
    /**
     * Gets class hierarchy list.
     *
     * @param cl00201VO the cl 00201 vo
     * @return the class hierarchy list
     */
    List<Cl00201VO> getClassHierarchyList(Cl00201VO cl00201VO);

    List<Cl00201VO> getClassHierarchyListForClassify(Cl00201VO cl00201VO);

    /**
     * Gets classification scheme.
     *
     * @return the classification scheme
     */
    Cl00101VO getClassificationScheme();

    /**
     * Gets selected class detail.
     *
     * @param cl00201VO the cl 00201 vo
     * @return the selected class detail
     */
    Cl00202VO getSelectedClassDetail(Cl00201VO cl00201VO);

    /**
     * Gets child class.
     *
     * @param classUuid the class uuid
     * @return the child class
     */
    int getChildClass(String classUuid);

    /**
     * Gets max class code.
     *
     * @param classificationSchemeUuid the classification scheme uuid
     * @return the max class code
     */
    String getMaxClassCode(String classificationSchemeUuid);

    /**
     * Update status confirm.
     *
     * @param cl00201VO the cl 00201 vo
     */
    void updateStatusConfirm(Cl00201VO cl00201VO);
}
