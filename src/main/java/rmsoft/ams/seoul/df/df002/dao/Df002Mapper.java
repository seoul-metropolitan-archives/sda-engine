package rmsoft.ams.seoul.df.df002.dao;

import io.onsemiro.core.mybatis.MyBatisMapper;
import rmsoft.ams.seoul.df.df002.vo.Df002;

import java.util.List;


public interface Df002Mapper extends MyBatisMapper {

    List<Df002> findAll();

    Df002 findOne(Df002 df002);

    int update(Df002 df002);

    int delete(Df002 df002);

    int insert(Df002 df002);
}