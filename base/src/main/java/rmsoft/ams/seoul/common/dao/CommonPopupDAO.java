package rmsoft.ams.seoul.common.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * The type Common popup dao.
 */
@Repository
public class CommonPopupDAO
{
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * Search list.
     *
     * @param sql   the sql
     * @param param the param
     * @return the list
     */
    public List<Map<String,Object>> search(String sql, Map<String,Object> param)
    {
        //param.get("searchField");\$([A-Z])*\$

        sql = sql.replaceAll("@([A-Za-z1-9_])*",null == param.get("searchField") ? "''" : "'"+param.get("searchField").toString()+"'");
        sql = sql.replaceAll(";","");

        return jdbcTemplate.queryForList(sql);
    }

    public List<Map<String,Object>> search2Param(String sql, Map<String,Object> param)
    {
        //param.get("searchField");\$([A-Z])*\$

        sql = sql.replaceAll("@PARAM2", "'"+(null==param.get("searchField2")? "":param.get("searchField2").toString())+"'");

        sql = sql.replaceAll("@([A-Za-z1-9_])*",null == param.get("searchField") ? "''" : "'"+param.get("searchField").toString()+"'");
        sql = sql.replaceAll(";","");

        return jdbcTemplate.queryForList(sql);
    }
}
