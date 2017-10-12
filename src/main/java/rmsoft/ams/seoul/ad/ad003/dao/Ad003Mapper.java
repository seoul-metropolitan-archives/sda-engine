package rmsoft.ams.seoul.ad.ad003.dao;

import io.onsemiro.core.mybatis.MyBatisMapper;
import rmsoft.ams.seoul.ad.ad003.vo.Ad00302VO;
import rmsoft.ams.seoul.ad.ad003.vo.Ad00303VO;

import java.util.List;

public interface Ad003Mapper extends MyBatisMapper {


    List<Ad00302VO> selectDetail(Ad00302VO param);
    List<Ad00303VO> getCode(Ad00303VO param);

}
