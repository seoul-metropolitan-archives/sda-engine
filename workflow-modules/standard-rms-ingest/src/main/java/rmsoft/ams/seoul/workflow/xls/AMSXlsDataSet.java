/*
 * Copyright (c) 2018. RMSoft Co.,Ltd. All rights reserved
 *
 */

package rmsoft.ams.seoul.workflow.xls;

import lombok.extern.log4j.Log4j;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.dbunit.dataset.*;

import java.io.*;


/**
 * The type Ams xls data set.
 */
@Log4j
public class AMSXlsDataSet extends AbstractDataSet {

    private final OrderedTableNameMap _tables;
    
    /**
     * Creates a new XlsDataSet object that loads the specified Excel document.
     *
     * @param file the file
     * @throws IOException      the io exception
     * @throws DataSetException the data set exception
     */
    public AMSXlsDataSet(File file) throws IOException, DataSetException {
        this(new FileInputStream(file));
    }

    /**
     * Creates a new XlsDataSet object that loads the specified Excel document.
     *
     * @param in the in
     * @throws IOException      the io exception
     * @throws DataSetException the data set exception
     */
    public AMSXlsDataSet(InputStream in) throws IOException, DataSetException {
        _tables = super.createTableNameMap();

        Workbook workbook;
        try {
            workbook = WorkbookFactory.create(in);
        } catch (InvalidFormatException e) {
            throw new IOException(e);
        }

        int sheetCount = workbook.getNumberOfSheets();

        String sheetName = "";
        String tableName = "";
        ITable table = null;

        for (int i = 0; i < sheetCount; i++) {
            sheetName = workbook.getSheetName(i);

            if (sheetName.equals("업무관리_기록물철목록") || sheetName.equals("업무관리_기록물건목록") || sheetName.equals("업무관리_전자파일목록")) {
                if (sheetName.equals("업무관리_기록물철목록")) {
                    tableName = "rc_aggregation_rms_inf";
                } else if (sheetName.equals("업무관리_기록물건목록")) {
                    tableName = "rc_item_rms_inf";
                } else if (sheetName.equals("업무관리_전자파일목록")) {
                    tableName = "rc_component_rms_inf";
                }

                workbook.getSheetAt(i).shiftRows(2, workbook.getSheetAt(i).getLastRowNum(), -1);
                table = new AMSXlsTable(tableName, workbook.getSheetAt(i));
                _tables.add(tableName, table);

            }
        }
    }

    /**
     * Write the specified dataset to the specified Excel document.
     *
     * @param dataSet the data set
     * @param out     the out
     * @throws IOException      the io exception
     * @throws DataSetException the data set exception
     */
    public static void write(IDataSet dataSet, OutputStream out)
            throws IOException, DataSetException {
        log.debug("write(dataSet=" + dataSet + ", out=" + out + ") - start");

        new AMSXlsDataSetWriter().write(dataSet, out);
    }


    ////////////////////////////////////////////////////////////////////////////
    // AbstractDataSet class

    protected ITableIterator createIterator(boolean reversed)
            throws DataSetException {
        if (log.isDebugEnabled())
            log.debug("createIterator(reversed=" + String.valueOf(reversed) + ") - start");

        ITable[] tables = (ITable[]) _tables.orderedValues().toArray(new ITable[0]);
        return new DefaultTableIterator(tables, reversed);
    }
}
