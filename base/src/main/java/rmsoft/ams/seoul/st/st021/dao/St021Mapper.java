package rmsoft.ams.seoul.st.st021.dao;

import io.onsemiro.core.mybatis.MyBatisMapper;

import rmsoft.ams.seoul.st.st021.vo.St02101VO;

import java.util.List;


public interface St021Mapper extends MyBatisMapper {
    List<St02101VO> getStWithoutNoticeInoutHistStatistic(St02101VO vo);

}
