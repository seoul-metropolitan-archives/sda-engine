package rmsoft.ams.seoul.st.st004.dao;

import io.onsemiro.core.mybatis.MyBatisMapper;
import rmsoft.ams.seoul.st.st004.vo.St004;

import java.util.List;


public interface St004Mapper extends MyBatisMapper {

    List<St004> findAll();

    St004 findOne(St004 st004);

    int update(St004 st004);

    int delete(St004 st004);

    int insert(St004 st004);
}