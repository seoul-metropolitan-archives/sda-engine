package rmsoft.ams.seoul.ac.ac010.dao;

import io.onsemiro.core.mybatis.MyBatisMapper;
import rmsoft.ams.seoul.ac.ac010.vo.Ac010;

import java.util.List;


public interface Ac010Mapper extends MyBatisMapper {

    List<Ac010> findAll();

    Ac010 findOne(Ac010 ac010);

    int update(Ac010 ac010);

    int delete(Ac010 ac010);

    int insert(Ac010 ac010);
}