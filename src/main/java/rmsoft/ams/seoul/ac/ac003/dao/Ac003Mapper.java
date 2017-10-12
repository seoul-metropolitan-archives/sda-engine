package rmsoft.ams.seoul.ac.ac003.dao;

import io.onsemiro.core.mybatis.MyBatisMapper;
import rmsoft.ams.seoul.common.domain.AcUser;

import java.util.List;

/**
 * Ac003Mapper
 *
 * @author james
 * @version 1.0.0
 * @since 2017-09-26 오후 3:44
 **/
public interface Ac003Mapper extends MyBatisMapper {

    List<AcUser> findAll();

    List<AcUser> findHistory(AcUser acUser);

    AcUser findOne(AcUser acUser);

    int update(AcUser acUser);

    int delete(AcUser acUser);

    int insert(AcUser acUser);
}