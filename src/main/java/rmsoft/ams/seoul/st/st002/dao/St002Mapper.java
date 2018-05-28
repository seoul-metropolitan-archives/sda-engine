package rmsoft.ams.seoul.st.st002.dao;

import io.onsemiro.core.mybatis.MyBatisMapper;
import rmsoft.ams.seoul.st.st002.vo.St002;

import java.util.List;


public interface St002Mapper extends MyBatisMapper {

    List<St002> findAll();

    St002 findOne(St002 st002);

    int update(St002 st002);

    int delete(St002 st002);

    int insert(St002 st002);
}