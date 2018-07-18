package rmsoft.ams.seoul.lt.lt002.dao;

import io.onsemiro.core.mybatis.MyBatisMapper;
import rmsoft.ams.seoul.lt.lt001.vo.Lt00101VO;
import rmsoft.ams.seoul.lt.lt002.vo.Lt00201VO;

import java.util.List;


public interface Lt002Mapper extends MyBatisMapper {

    /**
     * Search entity type list.
     *
     * @param param the param
     * @return the list
     */
    List<Lt00201VO> searchList(Lt00201VO param);

    /**
     * Gets child class.
     *
     * @param param the disposalFreezeEvent Uuid uuid
     * @return the child class
     */
    int checkDelete(Lt00201VO param);
}