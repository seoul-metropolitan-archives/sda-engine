package rmsoft.ams.seoul.ad.ad008.dao;

import io.onsemiro.core.mybatis.MyBatisMapper;
import rmsoft.ams.seoul.ad.ad008.vo.Ad008;

import java.util.List;


public interface Ad008Mapper extends MyBatisMapper {

    List<Ad008> findAll();

    Ad008 findOne(Ad008 ad008);

    int update(Ad008 ad008);

    int delete(Ad008 ad008);

    int insert(Ad008 ad008);
}