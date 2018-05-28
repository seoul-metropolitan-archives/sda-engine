package rmsoft.ams.seoul.df.df003.dao;

import io.onsemiro.core.mybatis.MyBatisMapper;
import rmsoft.ams.seoul.df.df003.vo.Df003;

import java.util.List;


public interface Df003Mapper extends MyBatisMapper {

    List<Df003> findAll();

    Df003 findOne(Df003 df003);

    int update(Df003 df003);

    int delete(Df003 df003);

    int insert(Df003 df003);
}