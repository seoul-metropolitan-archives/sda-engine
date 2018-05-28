package rmsoft.ams.seoul.rs.rs001.dao;

import io.onsemiro.core.mybatis.MyBatisMapper;
import rmsoft.ams.seoul.rs.rs001.vo.Rs001;

import java.util.List;


public interface Rs001Mapper extends MyBatisMapper {

    List<Rs001> findAll();

    Rs001 findOne(Rs001 rs001);

    int update(Rs001 rs001);

    int delete(Rs001 rs001);

    int insert(Rs001 rs001);
}