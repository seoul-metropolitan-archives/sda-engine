package rmsoft.ams.seoul.ad.ad002.dao;

import io.onsemiro.core.mybatis.MyBatisMapper;
import rmsoft.ams.seoul.ad.ad002.vo.Ad00201VO;

import java.util.List;

public interface Ad002Mapper extends MyBatisMapper {


    List<Ad00201VO> select(Ad00201VO param);

}
