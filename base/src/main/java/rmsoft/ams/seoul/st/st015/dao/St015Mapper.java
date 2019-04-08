package rmsoft.ams.seoul.st.st015.dao;

import io.onsemiro.core.mybatis.MyBatisMapper;
import rmsoft.ams.seoul.st.st015.vo.St01501VO;
import rmsoft.ams.seoul.st.st015.vo.St01502VO;
import rmsoft.ams.seoul.st.st015.vo.St01503VO;
import rmsoft.ams.seoul.st.st029.vo.St02901VO;

import java.util.List;


public interface St015Mapper extends MyBatisMapper {


    List<St01501VO> getStInventoryPlan(St01501VO st01501VO);
    List<St01501VO> getStInventoryPlanExl(St01501VO st01501VO);

    List<St01502VO> getStInventoryContainerResult(St01502VO st01502VO);

    List<St01503VO> getStInventoryRecordResult(St01503VO st01503VO);
}
