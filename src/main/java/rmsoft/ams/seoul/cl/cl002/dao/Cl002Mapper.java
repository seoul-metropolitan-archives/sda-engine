package rmsoft.ams.seoul.cl.cl002.dao;

import io.onsemiro.core.mybatis.MyBatisMapper;
import rmsoft.ams.seoul.cl.cl001.vo.Cl00101VO;
import rmsoft.ams.seoul.cl.cl002.vo.Cl00201VO;
import rmsoft.ams.seoul.cl.cl002.vo.Cl00202VO;

import java.util.List;

public interface Cl002Mapper extends MyBatisMapper
{
    List<Cl00201VO> getClassList(Cl00201VO cl00201VO);
    List<Cl00201VO> getSelectedClassList(Cl00201VO cl00201VO);
    List<Cl00201VO> getClassHierarchyList(Cl00201VO cl00201VO);
    Cl00101VO getClassificationScheme();
    Cl00202VO getSelectedClassDetail(Cl00201VO cl00201VO);
    int getChildClass(String classUuid);
    String getMaxClassCode(String classificationSchemeUuid);
    void updateStatusConfirm(Cl00201VO cl00201VO);
}
