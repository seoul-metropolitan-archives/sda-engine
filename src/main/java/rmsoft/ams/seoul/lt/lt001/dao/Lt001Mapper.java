package rmsoft.ams.seoul.lt.lt001.dao;

import io.onsemiro.core.mybatis.MyBatisMapper;
import rmsoft.ams.seoul.lt.lt001.vo.Lt00101VO;

import java.util.List;


public interface Lt001Mapper extends MyBatisMapper {

    /**
     * Search entity type list.
     *
     * @param param the param
     * @return the list
     */
    List<Lt00101VO> searchList(Lt00101VO param);

    /**
     * Gets child class.
     *
     * @param uuid the disposalFreezeEvent Uuid uuid
     * @return the child class
     */
    int checkUpdate(Lt00101VO param);
}