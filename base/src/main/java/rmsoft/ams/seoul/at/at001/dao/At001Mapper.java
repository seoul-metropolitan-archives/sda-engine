package rmsoft.ams.seoul.at.at001.dao;

import io.onsemiro.core.mybatis.MyBatisMapper;
import rmsoft.ams.seoul.at.at001.vo.At00101VO;
import rmsoft.ams.seoul.at.at001.vo.At00102VO;
import rmsoft.ams.seoul.at.at001.vo.At00103VO;

import java.util.List;

/**
 * The interface Cl 001 mapper.
 */
public interface At001Mapper extends MyBatisMapper {
    List<At00101VO> getAuthorityList(At00101VO at00101VO);
    List<At00102VO> getRelAuthorityList(At00102VO at00102VO);
    List<At00103VO> getAuthorityMetaInfoList(At00103VO at00103VO);
}
