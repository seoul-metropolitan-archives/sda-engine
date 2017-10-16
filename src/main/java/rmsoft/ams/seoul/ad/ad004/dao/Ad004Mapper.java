package rmsoft.ams.seoul.ad.ad004.dao;

import io.onsemiro.core.mybatis.MyBatisMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import rmsoft.ams.seoul.ad.ad004.vo.Ad00401VO;
import rmsoft.ams.seoul.ad.ad004.vo.Ad00402VO;

import java.util.List;

@Mapper
public interface Ad004Mapper extends MyBatisMapper
{
    List<Ad00401VO> searchPopupHeader(Ad00401VO param);

    List<Ad00402VO> getPopupDetail(Ad00402VO param);

}
