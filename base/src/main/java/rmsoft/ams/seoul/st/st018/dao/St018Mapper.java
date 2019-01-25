package rmsoft.ams.seoul.st.st018.dao;

import io.onsemiro.core.mybatis.MyBatisMapper;

import rmsoft.ams.seoul.st.st018.vo.St01801VO;
import rmsoft.ams.seoul.st.st018.vo.St01802VO;
import rmsoft.ams.seoul.st.st018.vo.St018PrinterVO;

import java.util.List;


public interface St018Mapper extends MyBatisMapper {
    List<St01801VO> getRcAggregation(St01801VO vo);

    List<St01802VO> getStRfidTag(St01802VO vo);
    St018PrinterVO getAggreationForPrint(St018PrinterVO vo);
}
