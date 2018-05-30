package rmsoft.ams.seoul.ad.ad007.dao;

import io.onsemiro.core.mybatis.MyBatisMapper;
import rmsoft.ams.seoul.ad.ad007.vo.Ad007;

import java.util.List;


public interface Ad007Mapper extends MyBatisMapper {

    List<Ad007> findAll();

    Ad007 findOne(Ad007 ad007);

    int update(Ad007 ad007);

    int delete(Ad007 ad007);

    int insert(Ad007 ad007);
}