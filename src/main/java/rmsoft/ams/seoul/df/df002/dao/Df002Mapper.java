package rmsoft.ams.seoul.df.df002.dao;

import io.onsemiro.core.mybatis.MyBatisMapper;
import rmsoft.ams.seoul.df.df002.vo.Df00201VO;
import rmsoft.ams.seoul.df.df002.vo.Df00202VO;

import java.util.List;


public interface Df002Mapper extends MyBatisMapper {

    /**
     * Search entity type list.
     *
     * @param param the param
     * @return the list
     */
    List<Df00201VO> searchList(Df00201VO param);

    /**
     * Gets child class.
     *
     * @param disposalFreezeEventUuid the disposalFreezeEvent Uuid uuid
     * @return the child class
     */
    int checkDegree(String disposalFreezeEventUuid);
}