package rmsoft.ams.seoul.rs.rs002.dao;

import io.onsemiro.core.mybatis.MyBatisMapper;
import rmsoft.ams.seoul.rs.rs002.vo.Rs002;

import java.util.List;


public interface Rs002Mapper extends MyBatisMapper {

    List<Rs002> findAll();

    Rs002 findOne(Rs002 rs002);

    int update(Rs002 rs002);

    int delete(Rs002 rs002);

    int insert(Rs002 rs002);
}