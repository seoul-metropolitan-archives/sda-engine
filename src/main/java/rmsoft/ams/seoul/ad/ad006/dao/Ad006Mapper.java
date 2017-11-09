package rmsoft.ams.seoul.ad.ad006.dao;

import io.onsemiro.core.mybatis.MyBatisMapper;
import org.apache.ibatis.annotations.Mapper;
import rmsoft.ams.seoul.ad.ad006.vo.Ad00601VO;
import rmsoft.ams.seoul.ad.ad006.vo.Ad00602VO;
import rmsoft.ams.seoul.common.domain.AdEntityColumn;

import java.util.List;

@Mapper
public interface Ad006Mapper extends MyBatisMapper
{
    List<Ad00601VO> searchEntityType(Ad00601VO param);
    List<Ad00602VO> getEntityColumnList(Ad00601VO param);

    int checkEntityType(String param);

    int checkEntityColumn(AdEntityColumn adEntityColumn);
}
