package rmsoft.ams.seoul.st.st001.dao;

import io.onsemiro.core.mybatis.MyBatisMapper;
import rmsoft.ams.seoul.st.st001.vo.St001;

import java.util.List;


public interface St001Mapper extends MyBatisMapper {

    List<St001> findAll();

    St001 findOne(St001 st001);

    int update(St001 st001);

    int delete(St001 st001);

    int insert(St001 st001);
}