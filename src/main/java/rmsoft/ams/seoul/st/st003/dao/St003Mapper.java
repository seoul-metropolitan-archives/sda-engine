package rmsoft.ams.seoul.st.st003.dao;

import io.onsemiro.core.mybatis.MyBatisMapper;
import rmsoft.ams.seoul.st.st003.vo.St003;

import java.util.List;


public interface St003Mapper extends MyBatisMapper {

    List<St003> findAll();

    St003 findOne(St003 st003);

    int update(St003 st003);

    int delete(St003 st003);

    int insert(St003 st003);
}