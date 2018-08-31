package rmsoft.ams.seoul.ig.ig003.dao;

import io.onsemiro.core.mybatis.MyBatisMapper;
import rmsoft.ams.seoul.ig.ig002.vo.Ig00201VO;

import java.util.List;


public interface Ig003Mapper extends MyBatisMapper {
    List<Ig00201VO> getIgAccessionRecordList(Ig00201VO ig00201VO);
}