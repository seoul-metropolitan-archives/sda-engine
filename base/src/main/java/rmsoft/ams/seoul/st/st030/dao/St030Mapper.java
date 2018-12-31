package rmsoft.ams.seoul.st.st030.dao;

import io.onsemiro.core.mybatis.MyBatisMapper;

import rmsoft.ams.seoul.st.st003.vo.St003VO;
import rmsoft.ams.seoul.st.st030.vo.St03001VO;


import java.util.List;


public interface St030Mapper extends MyBatisMapper {
    List<St03001VO> getStMissArrangeRequest(St03001VO vo);

    St003VO getStArrangeRecordsResult(St03001VO vo);
}
