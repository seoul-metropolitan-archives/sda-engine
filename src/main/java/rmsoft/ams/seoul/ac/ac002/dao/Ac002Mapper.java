package rmsoft.ams.seoul.ac.ac002.dao;


import io.onsemiro.core.mybatis.MyBatisMapper;
import rmsoft.ams.seoul.ac.ac002.vo.Ac002VO;

public interface Ac002Mapper extends MyBatisMapper{
    public Ac002VO getStartupProgram(Ac002VO param);
}
