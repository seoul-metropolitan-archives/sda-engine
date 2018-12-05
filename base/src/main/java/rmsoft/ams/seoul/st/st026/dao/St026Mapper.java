package rmsoft.ams.seoul.st.st026.dao;

import io.onsemiro.core.mybatis.MyBatisMapper;

import rmsoft.ams.seoul.st.st026.vo.St026VO;

import java.util.List;


public interface St026Mapper extends MyBatisMapper {
    List<St026VO> getStRfidMachine(St026VO vo);

}
