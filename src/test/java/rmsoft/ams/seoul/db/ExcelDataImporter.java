package rmsoft.ams.seoul.db;

import rmsoft.ams.seoul.socket.SocketMsgUtils;
import lombok.extern.slf4j.Slf4j;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.excel.XlsDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.sql.DataSource;
import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Created by Owner on 2016-08-09.
 */
@Slf4j
public class ExcelDataImporter {

    private DataSource getDataSource(String userName) {

        return  DataSourceBuilder.create()
                    .url("jdbc:oracle:thin:@shinhan.cwozeqnjffgl.ap-northeast-2.rds.amazonaws.com:1521:shinhan")
                    .driverClassName("oracle.jdbc.driver.OracleDriver")
                    .username(userName)
                    .password("ParkSt0re!")
                    .build();
    }

    private void execute(String userName, String... fileNames) {

        Connection conn = DataSourceUtils.getConnection(getDataSource(userName));
        IDatabaseConnection dbUnitCon = null;

        try {

            dbUnitCon = new DatabaseConnection(conn);

            //dbUnitCon.getConfig().setProperty(DatabaseConfig.FEATURE_ALLOW_EMPTY_FIELDS, true);
            //config.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new PostgresqlDataTypeFactory());
            //config.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new CustomDataTypeFactory());

            for (String fileName : Arrays.asList(fileNames)) {
                IDataSet dataset = new XlsDataSet(new File(SocketMsgUtils.getDbDatesetDir() + fileName));
                DatabaseOperation.CLEAN_INSERT.execute(dbUnitCon, dataset);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                dbUnitCon.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws Exception{

        String userName = args[0];

        String[] files = new String[args.length - 1];

        for (int i = 1; i < args.length; i++) {
            files[i-1] = args[i];
        }

        System.out.println("excel import job :: start...!");

        ExcelDataImporter excelDataImporter = new ExcelDataImporter();
        excelDataImporter.execute(userName, files);

        String fileNames = Arrays.stream(files).collect(Collectors.joining(", "));

        System.out.println("[file names] " +  fileNames);

        System.out.println("excel import job :: end...!");
    }

}
