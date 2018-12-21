package rmsoft.ams.seoul.st.st010.dao;

import io.onsemiro.core.mybatis.MyBatisMapper;
import rmsoft.ams.seoul.st.st010.vo.St01004VO;

import java.util.List;

public interface St010Mapper extends MyBatisMapper {


    List<St01004VO> getAggregation(St01004VO st01004VO);
}
