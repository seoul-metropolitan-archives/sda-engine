package rmsoft.ams.seoul.st.st002.dao;

import io.onsemiro.core.mybatis.MyBatisMapper;
import rmsoft.ams.seoul.st.st002.vo.St00201VO;

import java.util.List;

public interface St002Mapper extends MyBatisMapper {
    List<St00201VO> getContainerHierarchyList();
    List<St00201VO> getContainerList(St00201VO st00201VO);
    List<St00201VO> getSelectedContainerList(St00201VO st00201VO);
}