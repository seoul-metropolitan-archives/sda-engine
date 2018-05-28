package rmsoft.ams.seoul.lt.lt003.dao;

import io.onsemiro.core.mybatis.MyBatisMapper;
import rmsoft.ams.seoul.lt.lt003.vo.Lt003;

import java.util.List;


public interface Lt003Mapper extends MyBatisMapper {

    List<Lt003> findAll();

    Lt003 findOne(Lt003 lt003);

    int update(Lt003 lt003);

    int delete(Lt003 lt003);

    int insert(Lt003 lt003);
}