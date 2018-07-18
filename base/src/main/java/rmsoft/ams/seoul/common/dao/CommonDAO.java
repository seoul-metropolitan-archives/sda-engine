/*
 * Copyright (c) 2017. RMSoft Co.,Ltd. All rights reserved
 */

package rmsoft.ams.seoul.common.dao;

import egovframework.rte.psl.dataaccess.EgovAbstractDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

/**
 * The type Common Dao.
 */
public class CommonDAO extends EgovAbstractDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * Search list.
     *
     * @param sql   the sql
     * @param param the param
     * @return the list
     */
    public List<Map<String, Object>> search(String sql, Map<String, Object> param) {
        //param.get("searchField");\$([A-Z])*\$

        sql = sql.replaceAll("@([A-Za-z1-9_])*", null == param.get("searchField") ? "''" : "'" + param.get("searchField").toString() + "'");
        sql = sql.replaceAll(";", "");
        
        return jdbcTemplate.queryForList(sql);
    }

}
