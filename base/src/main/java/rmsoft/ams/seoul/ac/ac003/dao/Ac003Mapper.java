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
 * @since 2017 -09-26 오후 3:44
 */
public interface Ac003Mapper extends MyBatisMapper {
    /**
     * Find all user list.
     *
     * @param ac00301VO the ac 00301 vo
     * @return the list
     */
    List<Ac00301VO> findAllUser(Ac00301VO ac00301VO);

    /**
     * Find user group user by user uuid list.
     *
     * @param userUuid the user uuid
     * @return the list
     */
    List<Ac00302VO> findUserGroupUserByUserUuid(String userUuid);

    /**
     * Find user role list.
     *
     * @param ac00303VO the ac 00303 vo
     * @return the list
     */
    List<Ac00303VO> findUserRole(Ac00303VO ac00303VO);

    /**
     * Save password.
     *
     * @param ac00301VO the ac 00301 vo
     */
    void savePassword(Ac00301VO ac00301VO);
}