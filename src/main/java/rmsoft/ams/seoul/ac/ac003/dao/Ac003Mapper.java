package rmsoft.ams.seoul.ac.ac003.dao;

import io.onsemiro.core.mybatis.MyBatisMapper;
import rmsoft.ams.seoul.ac.ac003.vo.Ac00301VO;
import rmsoft.ams.seoul.ac.ac003.vo.Ac00302VO;
import rmsoft.ams.seoul.ac.ac003.vo.Ac00303VO;

import java.util.List;

/**
 * Ac003Mapper
 *
 * @author james
 * @version 1.0.0
 * @since 2017-09-26 오후 3:44
 **/
public interface Ac003Mapper extends MyBatisMapper {
    List<Ac00301VO> findAllUser(Ac00301VO ac00301VO);

    List<Ac00302VO> findUserGroupUserByUserUuid(String userUuid);

    List<Ac00303VO> findUserRole(Ac00303VO ac00303VO);
}