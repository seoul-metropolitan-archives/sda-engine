package rmsoft.ams.seoul.common.dao;

import io.onsemiro.core.mybatis.MyBatisMapper;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import rmsoft.ams.seoul.common.domain.AdPopupHeader;
import rmsoft.ams.seoul.common.vo.BaseColumnVO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

@Repository
public class CommonPopupDAO
{
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Map<String,Object>> search(String sql, Map<String,Object> param)
    {
        //param.get("searchField");\$([A-Z])*\$

        sql = sql.replaceAll("@([A-Za-z1-9_])*",null == param.get("searchField") ? "''" : "'"+param.get("searchField").toString()+"'");
        sql = sql.replaceAll(";","");
        System.out.println("sql=>"+sql);
        return jdbcTemplate.queryForList(sql);
    }

}
