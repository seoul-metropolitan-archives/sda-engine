package rmsoft.ams.seoul.lt.lt004.dao;

import io.onsemiro.core.mybatis.MyBatisMapper;
import rmsoft.ams.seoul.lt.lt004.vo.Lt004;

import java.util.List;


public interface Lt004Mapper extends MyBatisMapper {

    List<Lt004> findAll();

    Lt004 findOne(Lt004 lt004);

    int update(Lt004 lt004);

    int delete(Lt004 lt004);

    int insert(Lt004 lt004);
}