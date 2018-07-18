package rmsoft.ams.seoul.lt.lt003.dao;

import io.onsemiro.core.mybatis.MyBatisMapper;
import rmsoft.ams.seoul.lt.lt003.vo.Lt00301VO;

import java.util.List;


public interface Lt003Mapper extends MyBatisMapper {

    /**
     * Search entity type list.
     *
     * @param param the param
     * @return the list
     */
    List<Lt00301VO> searchList(Lt00301VO param);

    /**
     * Gets child class.
     *
     * @param param the param
     * @return the child class
     */
    int checkDelete(String param);
}