package rmsoft.ams.seoul.st.st010.dao;

import io.onsemiro.core.mybatis.MyBatisMapper;
import rmsoft.ams.seoul.st.st010.vo.St01004VO;
import rmsoft.ams.seoul.st.st010.vo.St010Excel03VO;
import rmsoft.ams.seoul.st.st010.vo.St010ExcelVO;

import java.util.List;

public interface St010Mapper extends MyBatisMapper {


    List<St01004VO> getAggregation(St01004VO st01004VO);

    List<St010ExcelVO> getExcelList01(St010ExcelVO st010ExcelVO);

    List<St010ExcelVO> getExcelList02(St010ExcelVO st010ExcelVO);

    List<St010Excel03VO> getExcelList03(St010Excel03VO st010Excel03VO);
}
