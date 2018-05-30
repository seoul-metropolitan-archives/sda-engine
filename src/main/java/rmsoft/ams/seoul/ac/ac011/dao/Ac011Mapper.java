package rmsoft.ams.seoul.ac.ac011.dao;

import io.onsemiro.core.mybatis.MyBatisMapper;
import rmsoft.ams.seoul.ac.ac011.vo.Ac011;

import java.util.List;


public interface Ac011Mapper extends MyBatisMapper {

    List<Ac011> findAll();

    Ac011 findOne(Ac011 ac011);

    int update(Ac011 ac011);

    int delete(Ac011 ac011);

    int insert(Ac011 ac011);
}