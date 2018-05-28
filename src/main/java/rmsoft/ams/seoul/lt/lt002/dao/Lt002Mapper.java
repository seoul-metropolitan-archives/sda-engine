package rmsoft.ams.seoul.lt.lt002.dao;

import io.onsemiro.core.mybatis.MyBatisMapper;
import rmsoft.ams.seoul.lt.lt002.vo.Lt002;

import java.util.List;


public interface Lt002Mapper extends MyBatisMapper {

    List<Lt002> findAll();

    Lt002 findOne(Lt002 lt002);

    int update(Lt002 lt002);

    int delete(Lt002 lt002);

    int insert(Lt002 lt002);
}