package rmsoft.ams.seoul.st.st020.dao;

import io.onsemiro.core.mybatis.MyBatisMapper;

import rmsoft.ams.seoul.st.st020.vo.St02001VO;
import rmsoft.ams.seoul.st.st020.vo.St02002VO;

import java.util.List;


public interface St020Mapper extends MyBatisMapper {
    List<St02001VO> getRcAggregation(St02001VO vo);

    List<St02002VO> getStRfidTag(St02002VO vo);
}
