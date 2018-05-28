package rmsoft.ams.seoul.rs.rs003.dao;

import io.onsemiro.core.mybatis.MyBatisMapper;
import rmsoft.ams.seoul.rs.rs003.vo.Rs003;

import java.util.List;


public interface Rs003Mapper extends MyBatisMapper {

    List<Rs003> findAll();

    Rs003 findOne(Rs003 rs003);

    int update(Rs003 rs003);

    int delete(Rs003 rs003);

    int insert(Rs003 rs003);
}