package rmsoft.ams.seoul.utils;

import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

public class JdbcTemplateSingleton {
    private static JdbcTemplate instance;

    public static JdbcTemplate getInstance(DataSource dataSource) {
        if(instance == null) {
            instance = new JdbcTemplate(dataSource);
        }
        return instance;
    }

    public static JdbcTemplate getInstance(){
        if(instance == null) {
            return null;
        }
        return instance;
    }
}
