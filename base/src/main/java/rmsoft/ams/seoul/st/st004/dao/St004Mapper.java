package rmsoft.ams.seoul.st.st004.dao;

import io.onsemiro.core.mybatis.MyBatisMapper;
import rmsoft.ams.seoul.st.st004.vo.St00401VO;
import rmsoft.ams.seoul.st.st004.vo.St00402VO;

import java.util.List;


public interface St004Mapper extends MyBatisMapper {
    List<St00401VO> getStArrangeContainersResult(St00401VO st00401VO);

    List<St00402VO> getLocationList(St00402VO st00402VO);
}