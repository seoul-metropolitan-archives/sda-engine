package rmsoft.ams.seoul.st.st007.dao;

import io.onsemiro.core.mybatis.MyBatisMapper;

import rmsoft.ams.seoul.st.st007.vo.St00701VO;
import rmsoft.ams.seoul.st.st007.vo.St00702VO;

import java.util.List;


public interface St007Mapper extends MyBatisMapper {
    List<St00701VO> getDisposalList(St00701VO vo);

    List<St00702VO> getDisposalItem(St00702VO vo);
}
