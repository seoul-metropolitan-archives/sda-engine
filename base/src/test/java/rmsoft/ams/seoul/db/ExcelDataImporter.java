package rmsoft.ams.seoul.db;

import lombok.extern.slf4j.Slf4j;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.jdbc.datasource.DataSourceUtils;
import rmsoft.ams.seoul.socket.SocketMsgUtils;
import rmsoft.ams.seoul.xls.AMSXlsDataSet;

import javax.sql.DataSource;
import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Owner on 2016-08-09.
 */
@Slf4j
public class ExcelDataImporter {

    private DataSource getDataSource(String userName) {

        return DataSourceBuilder.create()
                .url("jdbc:oracle:thin:@//192.168.0.62:1521/amsdb")
                .driverClassName("oracle.jdbc.driver.OracleDriver")
                .username(userName)
                .password("ams")
                .build();
    }

    private void execute(String userName, String fileName) {

        Connection conn = DataSourceUtils.getConnection(getDataSource(userName));
        IDatabaseConnection dbUnitCon = null;

        try {

            dbUnitCon = new DatabaseConnection(conn, "ams");

            //dbUnitCon.getConfig().setProperty(DatabaseConfig.FEATURE_ALLOW_EMPTY_FIELDS, true);
            //config.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new PostgresqlDataTypeFactory());
            //config.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new CustomDataTypeFactory());

//            for (String fileName : Arrays.asList(fileNames)) {
//                IDataSet dataset = new AMSXlsDataSet(new File(SocketMsgUtils.getDbDatesetDir() + fileName), getDataSource("ams"));
//                DatabaseOperation.INSERT.execute(dbUnitCon, dataset);
//            }

            //IDataSet dataset = new AMSXlsDataSet(new File(SocketMsgUtils.getDbDatesetDir() + fileName), getDataSource("ams"));
            //DatabaseOperation.INSERT.execute(dbUnitCon, dataset);



            IDataSet dataset = new AMSXlsDataSet(new File(SocketMsgUtils.getDbDatesetDir() + fileName));
            DatabaseOperation.CLEAN_INSERT.execute(dbUnitCon, dataset);


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

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     * @throws Exception the exception
     */
    public static void main(String[] args) throws Exception {

//        String userName = args[0];
//
//        String[] files = new String[args.length - 1];
//
//        for (int i = 1; i < args.length; i++) {
//            files[i - 1] = args[i];
//        }

        System.out.println("excel import job :: start...!");

        ExcelDataImporter excelDataImporter = new ExcelDataImporter();
        excelDataImporter.execute("ams", "20181008_업무관리시스템_철건파일목록.xlsx");

        //String fileNames = Arrays.stream(files).collect(Collectors.joining(", "));

        //System.out.println("[file names] " + fileNames);

        System.out.println("excel import job :: end...!");

    }

}
