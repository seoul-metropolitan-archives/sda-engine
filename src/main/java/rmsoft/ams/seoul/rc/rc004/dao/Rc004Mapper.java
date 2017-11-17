package rmsoft.ams.seoul.rc.rc004.dao;

import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.mybatis.MyBatisMapper;
import rmsoft.ams.seoul.rc.rc004.vo.Rc00402VO;

public interface Rc004Mapper extends MyBatisMapper {
    void saveComponentList(Rc00402VO rc00402VO);
}
