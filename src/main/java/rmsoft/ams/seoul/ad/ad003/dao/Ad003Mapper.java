package rmsoft.ams.seoul.ad.ad003.dao;

import io.onsemiro.core.mybatis.MyBatisMapper;
import rmsoft.ams.seoul.ad.ad003.vo.Ad003VO_02;
import rmsoft.ams.seoul.ad.ad003.vo.Ad003VO_03;

import java.util.List;

public interface Ad003Mapper extends MyBatisMapper {


    List<Ad003VO_03> selectDetail(Ad003VO_02 param);
    List<Ad003VO_03> getCode(Ad003VO_03 param);

}
