package rmsoft.ams.seoul.st.st019.dao;

import io.onsemiro.core.mybatis.MyBatisMapper;

import rmsoft.ams.seoul.st.st019.vo.St01901VO;
import rmsoft.ams.seoul.st.st019.vo.St01902VO;

import java.util.List;


public interface St019Mapper extends MyBatisMapper {
    List<St01901VO> getRcAggregation(St01901VO vo);

    List<St01902VO> getStRfidTag(St01902VO vo);
}
