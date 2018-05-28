package rmsoft.ams.seoul.df.df001.dao;

import io.onsemiro.core.mybatis.MyBatisMapper;
import rmsoft.ams.seoul.df.df001.vo.Df001;

import java.util.List;


public interface Df001Mapper extends MyBatisMapper {

    List<Df001> findAll();

    Df001 findOne(Df001 df001);

    int update(Df001 df001);

    int delete(Df001 df001);

    int insert(Df001 df001);
}