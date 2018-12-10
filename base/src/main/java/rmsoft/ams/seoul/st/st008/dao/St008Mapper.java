package rmsoft.ams.seoul.st.st008.dao;

import io.onsemiro.core.mybatis.MyBatisMapper;

import rmsoft.ams.seoul.st.st008.vo.St00801VO;

import rmsoft.ams.seoul.st.st008.vo.St00802VO;
import rmsoft.ams.seoul.st.st008.vo.St00803VO;

import java.util.List;


public interface St008Mapper extends MyBatisMapper
{

    List<St00801VO> getStTakeoutRequest(St00801VO param);
    List<St00802VO> getStTakeoutRecordResult(St00802VO param);
}
