package com.bgf.shbank.db;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Owner on 2016-08-09.
 */
@Slf4j
public class ExcelDataExporter {

    private DataSource getDataSource() {

        return  DataSourceBuilder.create()
                    .url("jdbc:oracle:thin:@shinhan.cwozeqnjffgl.ap-northeast-2.rds.amazonaws.com:1521:shinhan")
                    .driverClassName("oracle.jdbc.driver.OracleDriver")
                    .username("tw_jang")
                    .password("ParkSt0re!")
                    .build();
    }

    private void execute(String file, String... tableNames) {

        Connection conn = DataSourceUtils.getConnection(getDataSource());
        IDatabaseConnection dbUnitCon;

        try {

            dbUnitCon = new DatabaseConnection(conn);
            //dbUnitCon = new DatabaseConnection(conn, "public");

            //dbUnitCon.getConfig().setProperty(DatabaseConfig.FEATURE_ALLOW_EMPTY_FIELDS, true);
            //config.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new PostgresqlDataTypeFactory());
            //config.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new CustomDataTypeFactory());

            IDataSet target = dbUnitCon.createDataSet(tableNames);

            FileOutputStream stream = new FileOutputStream(file);

            XlsDataSetWriter dataSetWriter = new XlsDataSetWriter();
            dataSetWriter.write(target, stream);

            //FileOutputStream stream = new FileOutputStream("dept-emp.xml");
            //FlatXmlDataSet.write(target, stream);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void exportExcelTemplate(String path) {
        Connection conn = DataSourceUtils.getConnection(getDataSource());
        List<String> tableList = Lists.newArrayList();
        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("SELECT table_name FROM user_tables WHERE table_name like 'ATMS_%'");
            while (rs.next()) {
                String tableName = rs.getString("table_name");
                tableList.add(tableName);
                System.out.println(tableName);
            }
            for (String tableName : tableList) {
                String progCD = tableName.replace("ATMS_", "sh");
                if (progCD.contains("_")) {
                    progCD = tableName.replace("ATMS_", "");
                }

                ResultSet rs2 = statement.executeQuery("SELECT PROG_NM, PROG_PH FROM ATMS_PROGRAM WHERE PROG_CD = '" + progCD.toLowerCase() + "'");
                if (rs2.next()) {
                    String progName = rs2.getString("PROG_NM");
                    String progPH = rs2.getString("PROG_PH");
                    progPH = progPH.replace("/", ".");
                    String className = "com.bgf.shbank.domain" + progPH + "."+ progCD.toUpperCase().charAt(0) + progCD.substring(1) + "VO";
                    if (!progCD.startsWith("sh")) {
                        progCD = progCD.toLowerCase();
                        String [] temp = progCD.split("_");
                        String name1 = temp[0].toUpperCase().charAt(0) + temp[0].substring(1);
                        for (int j = 1; j < temp.length; ++j) {
                            name1 = name1 + temp[j].toUpperCase().charAt(0) + temp[j].substring(1);
                        }

                        className = "com.bgf.shbank.domain" + progPH + "."+ name1 + "VO";
                    }
                    java.lang.reflect.Field[] fields = Class.forName(className).getDeclaredFields();

                    String filename = path + progCD + ".xlsx";
                    HSSFWorkbook workbook = new HSSFWorkbook();
                    HSSFSheet sheet = workbook.createSheet("Sheet1");

                    HSSFRow rowhead = sheet.createRow((short)0);
                    rowhead.createCell(0).setCellValue(progName);

                    HSSFRow row = sheet.createRow((short)1);

                    HSSFRow row2 = sheet.createRow((short)2);

                    HSSFRow row3 = sheet.createRow((short)3);

                    for (int i = 0 ; i < fields.length; ++i) {
                        row3.createCell(i).setCellValue(String.format("${vo.%s}", fields[i].getName()));
                    }
                    FileOutputStream fileOut = new FileOutputStream(filename);
                    workbook.write(fileOut);
                    fileOut.close();
                }

            }

        }  catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception{

        File saveDir = new File(DIR_NAME);

        boolean notExistsDir = !saveDir.exists();

        if (notExistsDir) {
            new File(DIR_NAME).mkdir();
        }

        log.info("excel export job :: start...!");

        ExcelDataExporter exporter = new ExcelDataExporter();
        exporter.execute(DIR_NAME + FILE_NAME, TABLE_NAMES);

        log.info("[file name] {}", FILE_NAME);
        log.info("[tables] {}", String.join(", ", Arrays.asList(TABLE_NAMES)));

        log.info("excel export job :: end...!");
    }

    private static final String DIR_NAME = "C:/dbunit_dataset/";

    private static final String FILE_NAME = "ATMS_01001170_.xls";

    private static final String[] TABLE_NAMES = {"ATMS_01001170"};
}
