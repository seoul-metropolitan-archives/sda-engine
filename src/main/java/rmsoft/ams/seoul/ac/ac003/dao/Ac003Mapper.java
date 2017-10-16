package rmsoft.ams.seoul.ac.ac003.dao;

import io.onsemiro.core.mybatis.MyBatisMapper;
import rmsoft.ams.seoul.ac.ac003.vo.Ac00301VO;

import java.util.List;

/**
 * Ac003Mapper
 *
 * @author james
 * @version 1.0.0
 * @since 2017-09-26 오후 3:44
 **/
public interface Ac003Mapper extends MyBatisMapper {
    List<Ac00301VO> findAllUser(String userId);

    List<Ac00301VO> findUserGroupUserByUserUuid(String userUuid);

    List<Ac00301VO> findUserGroupUserByUserGroupUuid(String userGroupUuid);
}