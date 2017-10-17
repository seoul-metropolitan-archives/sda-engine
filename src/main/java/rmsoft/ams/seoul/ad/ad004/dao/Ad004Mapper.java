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

    int insertPopupHeader(Ad00401VO data);
    int updatePopupHeader(Ad00401VO data);
    int deletePopupHeader(Ad00401VO data);
    int insertPopupSQL(Ad00401VO data);
    int insertPopupDetail(Ad00402VO data);
    int updatePopupDetail(Ad00402VO data);
    int deletePopupDetail(Ad00402VO data);

}
