package rmsoft.ams.seoul.df.df003.dao;

import io.onsemiro.core.mybatis.MyBatisMapper;
import rmsoft.ams.seoul.df.df003.vo.Df00301VO;
import rmsoft.ams.seoul.df.df003.vo.Df00302VO;

import java.util.List;


public interface Df003Mapper extends MyBatisMapper {

    /**
     * Search entity type list.
     *
     * @param param the param
     * @return the list
     */
    List<Df00301VO> searchTree(Df00301VO param);

    /**
     * Search entity type list.
     *
     * @param param the param
     * @return the list
     */
    List<Df00302VO> searchList(Df00302VO param);

    /**
     * Gets child class.
     *
     * @param uuid the disposalFreezeEvent Uuid uuid
     * @return the child class
     */
    int checkDelete(String uuid);

    /**
     * Search entity type list.
     *
     * @param param the param
     * @return the list
     */
    List<Df00302VO> search(Df00302VO param);
}