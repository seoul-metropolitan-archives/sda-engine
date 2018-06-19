package rmsoft.ams.seoul.lt.lt004.dao;

import io.onsemiro.core.mybatis.MyBatisMapper;
import rmsoft.ams.seoul.lt.lt004.vo.Lt00401VO;

import java.util.List;


public interface Lt004Mapper extends MyBatisMapper {

    /**
     * Search entity type list.
     *
     * @param param the param
     * @return the list
     */
    List<Lt00401VO> searchList(Lt00401VO param);

    /**
     * Gets child class.
     *
     * @param param the param
     * @return the child class
     */
    int checkIndex01(Lt00401VO param);

    /**
     * Gets child class.
     *
     * @param param the param
     * @return the child class
     */
    int checkIndex02(Lt00401VO param);
}