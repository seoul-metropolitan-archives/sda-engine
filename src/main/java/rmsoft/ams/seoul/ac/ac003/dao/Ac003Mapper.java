package rmsoft.ams.seoul.ac.ac003.dao;

import io.onsemiro.core.mybatis.MyBatisMapper;
import rmsoft.ams.seoul.ac.ac003.domain.Ac003;

import java.util.List;

/**
 * Ac003Mapper
 *
 * @author james
 * @version 1.0.0
 * @since 2017-09-26 오후 3:44
 **/
public interface Ac003Mapper extends MyBatisMapper {

    List<Ac003> findAll();

    List<Ac003> findHistory(Ac003 ac003);

    Ac003 findOne(Ac003 ac003);

    int update(Ac003 ac003);

    int delete(Ac003 ac003);

    int insert(Ac003 ac003);
}