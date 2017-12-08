package rmsoft.ams.seoul.db;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.dbunit.dataset.*;
import org.dbunit.dataset.datatype.DataType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Owner on 2016-09-07.
 */
@Slf4j
public class XlsDataSetWriter {

    private static final Logger logger = LoggerFactory.getLogger(org.dbunit.dataset.excel.XlsDataSetWriter.class);

    /**
     * The constant ZEROS.
     */
    public static final String ZEROS = "0000000000000000000000000000000000000000000000000000";

    /**
     * A special format pattern used to create a custom {@link DataFormat} which
     * marks {@link Date} values that are stored via POI to an XLS file.
     * Note that it might produce problems if a normal numeric value uses this format
     * pattern incidentally.
     */
    public static final String DATE_FORMAT_AS_NUMBER_DBUNIT = "####################";

    /**
     * Instead of recreating a new style object for each numeric cell, which
     * will cause the code to hit the POI limit of 4000 styles pretty quickly,
     * only create one per format and reuse the same style for all cells with
     * the same format.
     */
    private static final Map<Workbook, Map> cellStyleMap = new HashMap<Workbook, Map>();

    private CellStyle dateCellStyle;

    /**
     * Write the specified dataset to the specified Excel document.
     *
     * @param dataSet the data set
     * @param out     the out
     * @throws IOException      the io exception
     * @throws DataSetException the data set exception
     */
    public void write(IDataSet dataSet, OutputStream out) throws IOException, DataSetException {

        logger.debug("write(dataSet={}, out={}) - start", dataSet, out);

        Workbook workbook = createWorkbook();

        this.dateCellStyle = createDateCellStyle(workbook);

        int index = 0;

        ITableIterator iterator = dataSet.iterator();

        while(iterator.next()) {

            // create the table i.e. sheet
            ITable table = iterator.getTable();
            ITableMetaData metaData = table.getTableMetaData();
            Sheet sheet = workbook.createSheet(metaData.getTableName());

            // write table metadata i.e. first row in sheet
            workbook.setSheetName(index, metaData.getTableName());

            Row headerRow = sheet.createRow(0);
            Column[] columns = metaData.getColumns();

            for (int j = 0; j < columns.length; j++) {

                Column column = columns[j];

                Cell cell = headerRow.createCell(j);
                cell.setCellValue(column.getColumnName());
            }

            // write table data
            for (int j = 0; j < table.getRowCount(); j++) {

                Row row = sheet.createRow(j + 1);

                for (int k = 0; k < columns.length; k++) {

                    Column column = columns[k];
                    Object value = table.getValue(j, column.getColumnName());

                    if (value != null) {

                        Cell cell = row.createCell(k);

                        if(value instanceof Timestamp) {
                            setTimestampCell(cell, (Timestamp) value, workbook);
                        } else if(value instanceof Date) {
                            setDateCell(cell, (Date)value, workbook);
                        } else if(value instanceof BigDecimal) {
                            setNumericCell(cell, (BigDecimal)value, workbook);
                        } else if(value instanceof Long) {
                            setDateCell(cell, new Date( ((Long)value).longValue()), workbook);
                        } else {
                            cell.setCellValue(DataType.asString(value));
                        }
                    }
                }
            }

            index++;
        }

        // write xls document
        workbook.write(out);
        out.flush();
    }

    /**
     * Create date cell style cell style.
     *
     * @param workbook the workbook
     * @return the cell style
     */
    protected static CellStyle createDateCellStyle(Workbook workbook) {
        DataFormat format = workbook.createDataFormat();
        short dateFormatCode = format.getFormat(DATE_FORMAT_AS_NUMBER_DBUNIT);

        return getCellStyle(workbook, dateFormatCode);
    }

    /**
     * Gets cell style.
     *
     * @param workbook   the workbook
     * @param formatCode the format code
     * @return the cell style
     */
    protected static CellStyle getCellStyle(Workbook workbook, short formatCode) {
        Map<Short, CellStyle> map = findWorkbookCellStyleMap(workbook);
        CellStyle cellStyle = findCellStyle(workbook, formatCode, map);

        return cellStyle;
    }

    /**
     * Find workbook cell style map map.
     *
     * @param workbook the workbook
     * @return the map
     */
    protected static Map<Short, CellStyle> findWorkbookCellStyleMap(Workbook workbook) {

        Map<Short, CellStyle> map = cellStyleMap.get(workbook);

        if (map == null) {
            map = new HashMap<>();
            cellStyleMap.put(workbook, map);
        }

        return map;
    }

    /**
     * Find cell style cell style.
     *
     * @param workbook   the workbook
     * @param formatCode the format code
     * @param map        the map
     * @return the cell style
     */
    protected static CellStyle findCellStyle(Workbook workbook, Short formatCode, Map<Short, CellStyle> map) {

        CellStyle cellStyle = map.get(formatCode);

        if (cellStyle == null) {
            cellStyle = workbook.createCellStyle();
            cellStyle.setDataFormat(formatCode);

            map.put(formatCode, cellStyle);
        }

        return cellStyle;
    }

    /**
     * Sets timestamp cell.
     *
     * @param cell     the cell
     * @param value    the value
     * @param workbook the workbook
     */
    protected void setTimestampCell(Cell cell, Timestamp value, Workbook workbook) {

        try {

            DataFormat dataFormat = workbook.createDataFormat();
            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setDataFormat(dataFormat.getFormat("yyyy-MM-dd HH:mm:ss.0"));

            //log.debug("Timestamp Value :: {}", value.toLocalDateTime().toInstant(ZoneOffset.ofTotalSeconds(0)).toEpochMilli());

            cell.setCellValue(value);
            cell.setCellStyle(cellStyle);

        } catch (Exception e) {
            e.printStackTrace();
        }

//      System.out.println(HSSFDataFormat.getBuiltinFormats());
        // TODO Find out correct cell styles for date objects
//        HSSFCellStyle cellStyleDate = workbook.createCellStyle();
//        cellStyleDate.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy h:mm"));
//
//        HSSFCellStyle cellStyleDateTimeWithSeconds = workbook.createCellStyle();
//        cellStyleDateTimeWithSeconds.setDataFormat(HSSFDataFormat.getBuiltinFormat("h:mm:ss"));
//
//        HSSFDataFormat dataFormat = workbook.createDataFormat();
//        HSSFCellStyle cellStyle = workbook.createCellStyle();
//        cellStyle.setDataFormat(dataFormat.getFormat("dd/mm/yyyy hh:mm:ss"));
//
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
//        SimpleDateFormat formatter2 = new SimpleDateFormat("dd/MM/yyyy");
//        SimpleDateFormat formatter3 = new SimpleDateFormat("HH:mm:ss.SSS");
//
//
//        Date dateValue = (Date)value;
//        Calendar cal = null;
//
//        // If it is a date value that does not have seconds
//        if(dateValue.getTime() % 60000 == 0){
////            cellStyle = cellStyleDate;
//            cal=Calendar.getInstance();
//            cal.setTimeInMillis(dateValue.getTime());
//
//            cell.setCellValue(cal);
//            cell.setCellStyle(cellStyle);
////            cell.setCellValue(cal);
//        }
//        else {
////            HSSFDataFormatter formatter = new HSSFDataFormatter();
//
//            // If we have seconds assume that it is only h:mm:ss without date
//            // TODO Clean implementation where user can control date formats would be nice
////            double dateDouble = dateValue.getTime() % (24*60*60*1000);
//            cal = get1900Cal(dateValue);
//
//            String formatted = formatter3.format(dateValue);
//            //TODO Format ...
////            cellStyle = cellStyleDateTimeWithSeconds;
//            System.out.println("date formatted:"+formatted);
////            HSSFRichTextString s = new HSSFRichTextString(formatted);
////            cell.setCellValue(s);
//            cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
//            cell.setCellValue((double)dateValue.getTime());
//            cell.setCellStyle(cellStyleDateTimeWithSeconds);
//        }

    }

    /**
     * Sets date cell.
     *
     * @param cell     the cell
     * @param value    the value
     * @param workbook the workbook
     */
    protected void setDateCell(Cell cell, Date value, Workbook workbook) {

//        double excelDateValue = HSSFDateUtil.getExcelDate(value);
//        cell.setCellValue(excelDateValue);
//        cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);

        long timeMillis = value.getTime();
        cell.setCellValue( (double)timeMillis );
        cell.setCellType(Cell.CELL_TYPE_NUMERIC);
        cell.setCellStyle(this.dateCellStyle);

//      System.out.println(HSSFDataFormat.getBuiltinFormats());
        // TODO Find out correct cell styles for date objects
//        HSSFCellStyle cellStyleDate = workbook.createCellStyle();
//        cellStyleDate.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy h:mm"));
//
//        HSSFCellStyle cellStyleDateTimeWithSeconds = workbook.createCellStyle();
//        cellStyleDateTimeWithSeconds.setDataFormat(HSSFDataFormat.getBuiltinFormat("h:mm:ss"));
//
//        HSSFDataFormat dataFormat = workbook.createDataFormat();
//        HSSFCellStyle cellStyle = workbook.createCellStyle();
//        cellStyle.setDataFormat(dataFormat.getFormat("dd/mm/yyyy hh:mm:ss"));
//
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
//        SimpleDateFormat formatter2 = new SimpleDateFormat("dd/MM/yyyy");
//        SimpleDateFormat formatter3 = new SimpleDateFormat("HH:mm:ss.SSS");
//
//
//        Date dateValue = (Date)value;
//        Calendar cal = null;
//
//        // If it is a date value that does not have seconds
//        if(dateValue.getTime() % 60000 == 0){
////            cellStyle = cellStyleDate;
//            cal=Calendar.getInstance();
//            cal.setTimeInMillis(dateValue.getTime());
//
//            cell.setCellValue(cal);
//            cell.setCellStyle(cellStyle);
////            cell.setCellValue(cal);
//        }
//        else {
////            HSSFDataFormatter formatter = new HSSFDataFormatter();
//
//            // If we have seconds assume that it is only h:mm:ss without date
//            // TODO Clean implementation where user can control date formats would be nice
////            double dateDouble = dateValue.getTime() % (24*60*60*1000);
//            cal = get1900Cal(dateValue);
//
//            String formatted = formatter3.format(dateValue);
//            //TODO Format ...
////            cellStyle = cellStyleDateTimeWithSeconds;
//            System.out.println("date formatted:"+formatted);
////            HSSFRichTextString s = new HSSFRichTextString(formatted);
////            cell.setCellValue(s);
//            cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
//            cell.setCellValue((double)dateValue.getTime());
//            cell.setCellStyle(cellStyleDateTimeWithSeconds);
//        }

    }

    /**
     * Sets numeric cell.
     *
     * @param cell     the cell
     * @param value    the value
     * @param workbook the workbook
     */
    protected void setNumericCell(Cell cell, BigDecimal value, Workbook workbook) {

        if(log.isDebugEnabled())
            log.debug("setNumericCell(cell={}, value={}, workbook={}) - start", cell, value, workbook);

        cell.setCellValue(value.doubleValue());

        DataFormat df = workbook.createDataFormat();

        int scale = (value).scale();

        short format;

        if (scale <= 0) {
            format = df.getFormat("####");
        } else {
            String zeros = createZeros(((BigDecimal)value).scale());
            format = df.getFormat("####." + zeros);
        }

        if(log.isDebugEnabled())
            log.debug("Using format '{}' for value '{}'.", String.valueOf(format), value);

        CellStyle cellStyleNumber = getCellStyle(workbook, format);
        cell.setCellStyle(cellStyleNumber);
    }

//    public static Date get1900(Date date) {
//        Calendar cal = Calendar.getInstance();
//        cal.setTimeInMillis(date.getTime() % (24*60*60*1000));
//        cal.set(1900, 0, 1); // 1.1.1900
//        return cal.getTime();
//    }
//
//    public static Calendar get1900Cal(Date date) {
//        Calendar cal = Calendar.getInstance();
//        cal.clear();
////        long hoursInMillis = date.getTime() % (24*60*60*1000);
////        long smallerThanDays = date.getTime() % (24*60*60*1000);
////        cal.setTimeInMillis(date.getTime() % (24*60*60*1000));
//        cal.set(Calendar.SECOND, (int) (date.getTime() % (24*60*60*1000)) / (1000) );
//        cal.set(Calendar.MINUTE, (int) (date.getTime() % (24*60*60*1000)) / (1000*60) );
//        cal.set(Calendar.HOUR, (int) (date.getTime() % (24*60*60*1000)) / (1000*60*60) );
////        cal.set(1900, 0, 1); // 1.1.1900
//        System.out.println(cal.isSet(Calendar.DATE));
//        return cal;
//    }

    private static String createZeros(int count) {
        return ZEROS.substring(0, count);
    }

    /**
     * Create workbook workbook.
     *
     * @return the workbook
     */
    protected Workbook createWorkbook() {
        return new HSSFWorkbook();
    }
}
