package rmsoft.ams.seoul.rs.rs004.dao;

import io.onsemiro.core.mybatis.MyBatisMapper;
import rmsoft.ams.seoul.rs.rs004.vo.Rs004;

import java.util.List;


public interface Rs004Mapper extends MyBatisMapper {

    List<Rs004> findAll();

    Rs004 findOne(Rs004 rs004);

    int update(Rs004 rs004);

    int delete(Rs004 rs004);

    int insert(Rs004 rs004);
}