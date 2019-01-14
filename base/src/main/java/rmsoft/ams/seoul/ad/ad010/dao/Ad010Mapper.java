package rmsoft.ams.seoul.ad.ad010.dao;

import io.onsemiro.core.mybatis.MyBatisMapper;
import rmsoft.ams.seoul.ad.ad010.vo.Ad01001VO;
import rmsoft.ams.seoul.ad.ad010.vo.Ad01002VO;

import java.util.List;


public interface Ad010Mapper extends MyBatisMapper {
    Ad01001VO getList01();
    List<Ad01002VO> getNoticeList();
}