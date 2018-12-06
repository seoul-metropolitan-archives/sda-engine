package rmsoft.ams.seoul.lc.lc001.dao;

import io.onsemiro.core.mybatis.MyBatisMapper;
import rmsoft.ams.seoul.lc.lc001.vo.Lc00101VO;
import rmsoft.ams.seoul.lc.lc001.vo.Lc00102VO;

import java.util.List;


public interface Lc001Mapper extends MyBatisMapper {
    List<Lc00101VO> getLeadCaseList(Lc00101VO lc00101VO);
    List<Lc00102VO> getLeadCaseScheduleList(Lc00102VO lc00102VO);
}