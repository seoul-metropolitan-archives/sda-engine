package rmsoft.ams.seoul.st.st014.dao;

import io.onsemiro.core.mybatis.MyBatisMapper;

import rmsoft.ams.seoul.st.st014.vo.St01401VO;
import rmsoft.ams.seoul.st.st014.vo.St01402VO;

import java.util.List;


public interface St014Mapper extends MyBatisMapper {
    List<St01401VO> getStWithoutNoticeInoutRecord(St01401VO vo);

    List<St01402VO> getStWithoutNoticeInoutHist(St01402VO vo);
}
