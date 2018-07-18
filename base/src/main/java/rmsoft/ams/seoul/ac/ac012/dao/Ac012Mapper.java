package rmsoft.ams.seoul.ac.ac012.dao;

import io.onsemiro.core.mybatis.MyBatisMapper;
import rmsoft.ams.seoul.ac.ac012.vo.Ac012;

import java.util.List;


public interface Ac012Mapper extends MyBatisMapper {

    List<Ac012> findAll();

    Ac012 findOne(Ac012 ac012);

    int update(Ac012 ac012);

    int delete(Ac012 ac012);

    int insert(Ac012 ac012);
}