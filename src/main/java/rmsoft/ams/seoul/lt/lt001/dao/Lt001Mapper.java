package rmsoft.ams.seoul.lt.lt001.dao;

import io.onsemiro.core.mybatis.MyBatisMapper;
import rmsoft.ams.seoul.lt.lt001.vo.Lt001;

import java.util.List;


public interface Lt001Mapper extends MyBatisMapper {

    List<Lt001> findAll();

    Lt001 findOne(Lt001 lt001);

    int update(Lt001 lt001);

    int delete(Lt001 lt001);

    int insert(Lt001 lt001);
}