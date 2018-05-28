package rmsoft.ams.seoul.rs.rs005.dao;

import io.onsemiro.core.mybatis.MyBatisMapper;
import rmsoft.ams.seoul.rs.rs005.vo.Rs005;

import java.util.List;


public interface Rs005Mapper extends MyBatisMapper {

    List<Rs005> findAll();

    Rs005 findOne(Rs005 rs005);

    int update(Rs005 rs005);

    int delete(Rs005 rs005);

    int insert(Rs005 rs005);
}