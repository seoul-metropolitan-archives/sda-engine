package rmsoft.ams.seoul.at.at001.dao;

import io.onsemiro.core.mybatis.MyBatisMapper;
import rmsoft.ams.seoul.at.at001.vo.At00101VO;

import java.util.List;

/**
 * The interface Cl 001 mapper.
 */
public interface At001Mapper extends MyBatisMapper {
    List<At00101VO> getAuthorityList(At00101VO at00101VO);
}
