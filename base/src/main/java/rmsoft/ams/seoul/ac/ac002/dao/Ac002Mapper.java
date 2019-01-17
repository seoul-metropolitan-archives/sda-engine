package rmsoft.ams.seoul.ac.ac002.dao;


import io.onsemiro.core.mybatis.MyBatisMapper;
import rmsoft.ams.seoul.ac.ac002.vo.Ac00201VO;
import rmsoft.ams.seoul.ac.ac002.vo.Ac002VO;

import java.util.List;

/**
 * The interface Ac 002 mapper.
 */
public interface Ac002Mapper extends MyBatisMapper{
    /**
     * Gets startup program.
     *
     * @param param the param
     * @return the startup program
     */
    Ac002VO getStartupProgram(Ac002VO param);

    /**
     * Gets Bookmark.
     *
     * @param param the param
     * @return the startup program
     */
    List<Ac00201VO> getBookmark(Ac00201VO param);
}
