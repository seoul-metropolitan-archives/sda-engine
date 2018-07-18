package rmsoft.ams.seoul.ad.ad009.dao;

import io.onsemiro.core.mybatis.MyBatisMapper;
import rmsoft.ams.seoul.ad.ad009.vo.Ad009;

import java.util.List;


public interface Ad009Mapper extends MyBatisMapper {

    List<Ad009> findAll();

    Ad009 findOne(Ad009 ad009);

    int update(Ad009 ad009);

    int delete(Ad009 ad009);

    int insert(Ad009 ad009);
}