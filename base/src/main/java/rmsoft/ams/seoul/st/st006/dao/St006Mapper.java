package rmsoft.ams.seoul.st.st006.dao;

import io.onsemiro.core.mybatis.MyBatisMapper;

import rmsoft.ams.seoul.st.st004.vo.St00401VO;
import rmsoft.ams.seoul.st.st006.vo.St00601VO;

import rmsoft.ams.seoul.st.st006.vo.St00603VO;

import java.util.List;


public interface St006Mapper extends MyBatisMapper
{

    List<St00601VO> getStContainer(St00601VO param);

    /**
     * 자신포함 하위 container tree를 싹 가져옴.
     * @param param
     * @return
     */
    List<St00401VO> getContainerTree(St00401VO param);
    List<St00603VO> getSelectedItem(St00603VO param);

}
