package rmsoft.ams.seoul.st.st012.dao;

import io.onsemiro.core.mybatis.MyBatisMapper;

import rmsoft.ams.seoul.st.st012.vo.St01201VO;

import java.util.List;


public interface St012Mapper extends MyBatisMapper {
    List<St01201VO> getStInoutExcept(St01201VO vo);

    List<St01201VO> getStExceptRecordResult(St01201VO vo);
    List<St01201VO> getStWithoutNoticeInoutHistResult(St01201VO vo);

}
