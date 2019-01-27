package rmsoft.ams.seoul.st.st010.service;

import io.onsemiro.core.api.ApiException;
import io.onsemiro.core.code.ApiStatus;
import io.onsemiro.core.domain.BaseService;
import io.onsemiro.core.parameter.RequestParams;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import rmsoft.ams.seoul.st.st001.dao.St001Mapper;
import rmsoft.ams.seoul.st.st001.vo.St001;
import rmsoft.ams.seoul.st.st001.vo.St00101VO;
import rmsoft.ams.seoul.st.st001.vo.St00102VO;
import rmsoft.ams.seoul.st.st010.dao.St010Mapper;
import rmsoft.ams.seoul.st.st010.vo.St01004VO;
import rmsoft.ams.seoul.st.st010.vo.St010Excel03VO;
import rmsoft.ams.seoul.st.st010.vo.St010ExcelVO;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.util.List;

@Service
public class St010Service extends BaseService {

    @Inject
    private St010Mapper st010Mapper;

    @Autowired
    ResourceLoader loader;

    public Page<St01004VO> getAggregation(Pageable pageable, RequestParams<St01004VO> requestParams) {
        St01004VO st01004VO = new St01004VO();
        st01004VO.setLocationUuid(requestParams.getString("locationUuid"));
        return filter(st010Mapper.getAggregation(st01004VO), pageable, "", St01004VO.class);
    }


    public ByteArrayInputStream getExcelDown(RequestParams<St010ExcelVO> params) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        InputStream is = null;


        try {
            is = St010Service.class.getClassLoader().getResourceAsStream("excelTemp/st010_excel_shelf.xlsx");

            XSSFWorkbook workBook = new XSSFWorkbook(is);
            XSSFSheet sheet = null;
            XSSFRow row = null;
            XSSFCell cell = null;

            sheet = workBook.cloneSheet(0);
            workBook.setSheetName(workBook.getSheetIndex(sheet), "테스트 시트");

            St010ExcelVO st010ExcelVO = new St010ExcelVO();
            st010ExcelVO.setRepositoryUuid(params.getString("repositoryUuid"));

            List<St010ExcelVO> list = st010Mapper.getExcelList01(st010ExcelVO);

            int aggregationTotalCount = 0;
            int inCountTotal = 0;
            int outCountTotal = 0;
            int shelfCount = list.size();

            for(int i = 0; i < list.size();i++){
                int rNum = 5+i;
                row = sheet.createRow(rNum);
                cell = row.createCell(0); //repository Code;
                cell.setCellValue(list.get(i).getRepositoryCode());
                cell = row.createCell(1); //repository Name;
                cell.setCellValue(list.get(i).getRepositoryName());
                cell = row.createCell(2); //Shelf Code;
                cell.setCellValue(list.get(i).getShelfCode());
                cell = row.createCell(3); //Shelf Name;
                cell.setCellValue(list.get(i).getShelfName());
                cell = row.createCell(4); //Aggregation Count;
                cell.setCellValue(list.get(i).getAggregationCount());
                cell = row.createCell(5); //반입수량;
                cell.setCellValue(list.get(i).getInCount());
                cell = row.createCell(6); //반출수량;
                cell.setCellValue(list.get(i).getOutCount());
                aggregationTotalCount = aggregationTotalCount + Integer.parseInt(list.get(i).getAggregationCount());
                inCountTotal = inCountTotal + Integer.parseInt(list.get(i).getInCount());
                outCountTotal = outCountTotal + Integer.parseInt(list.get(i).getOutCount());
            }


            //상단셋팅
            row = sheet.getRow(0);
            cell = row.getCell(2);
            cell.setCellValue(params.getString("repositoryCode"));
            cell = row.getCell(4);
            cell.setCellValue(shelfCount);

            row = sheet.getRow(1);
            cell = row.getCell(2);
            cell.setCellValue(aggregationTotalCount);
            cell = row.getCell(4);
            cell.setCellValue(inCountTotal);
            cell = row.getCell(6);
            cell.setCellValue(outCountTotal);

            //하단셋팅
            row = sheet.createRow(list.size() + 5);
            cell = row.createCell(5);
            cell.setCellValue("반입완료상태");
            cell = row.createCell(6);
            cell.setCellValue("반출완료상태");

            //기존 0 1새로운시트
            workBook.removeSheetAt(0);
            workBook.write(out);

            return new ByteArrayInputStream(out.toByteArray());
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }


    public ByteArrayInputStream getExcelDown02(RequestParams<St010ExcelVO> params) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        InputStream is = null;


        try {
            is = St010Service.class.getClassLoader().getResourceAsStream("excelTemp/st010_excel_location.xlsx");

            XSSFWorkbook workBook = new XSSFWorkbook(is);
            XSSFSheet sheet = null;
            XSSFRow row = null;
            XSSFCell cell = null;

            sheet = workBook.cloneSheet(0);
            workBook.setSheetName(workBook.getSheetIndex(sheet), "테스트 시트");

            St010ExcelVO st010ExcelVO = new St010ExcelVO();
            st010ExcelVO.setRepositoryUuid(params.getString("repositoryUuid"));
            st010ExcelVO.setShelfUuid(params.getString("shelfUuid"));


            List<St010ExcelVO> list = st010Mapper.getExcelList02(st010ExcelVO);

            int aggregationTotalCount = 0;
            int inCountTotal = 0;
            int outCountTotal = 0;
            int shelfCount = 0;
            int rowCount = 0;

            for(int i = 0; i < list.size();i++){
                int rNum = 5+i;
                row = sheet.createRow(rNum);
                cell = row.createCell(0); //repository Code;
                cell.setCellValue(list.get(i).getRepositoryCode());

                cell = row.createCell(1); //repository Name;
                cell.setCellValue(list.get(i).getRepositoryName());

                cell = row.createCell(2); //Shelf Code;
                cell.setCellValue(list.get(i).getShelfCode());

                cell = row.createCell(3); //Shelf Name;
                cell.setCellValue(list.get(i).getShelfName());

                cell = row.createCell(4); //row;
                cell.setCellValue(list.get(i).getRowNo());

                cell = row.createCell(5); //column;
                cell.setCellValue(list.get(i).getColumnNo());

                cell = row.createCell(6); //Aggregation Count;
                cell.setCellValue(list.get(i).getAggregationCount());

                cell = row.createCell(7); //반입수량;
                cell.setCellValue(list.get(i).getInCount());

                cell = row.createCell(8); //반출수량;
                cell.setCellValue(list.get(i).getOutCount());

                aggregationTotalCount = aggregationTotalCount + Integer.parseInt(list.get(i).getAggregationCount());
                inCountTotal = inCountTotal + Integer.parseInt(list.get(i).getInCount());
                outCountTotal = outCountTotal + Integer.parseInt(list.get(i).getOutCount());
            }


            if(list.size() > 0){
                shelfCount = Integer.parseInt(list.get(0).getShelfCount());
                rowCount = Integer.parseInt(list.get(0).getRowCount());
            }



            //상단셋팅
            row = sheet.getRow(0);

            cell = row.getCell(2);
            cell.setCellValue(params.getString("repositoryCode"));

            cell = row.getCell(4);
            cell.setCellValue(shelfCount);

            cell = row.getCell(6);
            cell.setCellValue(rowCount);

            row = sheet.getRow(1);

            cell = row.getCell(2);
            cell.setCellValue(aggregationTotalCount);

            cell = row.getCell(4);
            cell.setCellValue(inCountTotal);

            cell = row.getCell(6);
            cell.setCellValue(outCountTotal);

            //하단셋팅
            row = sheet.createRow(list.size() + 5);
            cell = row.createCell(7);
            cell.setCellValue("반입완료상태");
            cell = row.createCell(8);
            cell.setCellValue("반출완료상태");

            //기존 0 1새로운시트
            workBook.removeSheetAt(0);
            workBook.write(out);

            return new ByteArrayInputStream(out.toByteArray());
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }


    }

    public ByteArrayInputStream getExcelDown03(RequestParams<St010Excel03VO> params) throws IOException {

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        InputStream is = null;


        try {
            is = St010Service.class.getClassLoader().getResourceAsStream("excelTemp/st010_excel_aggregation.xlsx");

            XSSFWorkbook workBook = new XSSFWorkbook(is);
            XSSFSheet sheet = null;
            XSSFRow row = null;
            XSSFCell cell = null;

            sheet = workBook.cloneSheet(0);
            workBook.setSheetName(workBook.getSheetIndex(sheet), "테스트 시트");

            St010Excel03VO st010Excel03VO = new St010Excel03VO();
            st010Excel03VO.setRepositoryUuid(params.getString("repositoryUuid"));
            st010Excel03VO.setShelfUuid(params.getString("shelfUuid"));
            st010Excel03VO.setLocationUuid(params.getString("locationUuid"));


            List<St010Excel03VO> list = st010Mapper.getExcelList03(st010Excel03VO);

            int aggregationTotalCount = list.size();
            int inCountTotal = 0;
            int outCountTotal = 0;
            int shelfCount = 0;
            int rowCount = 0;

            for(int i = 0; i < list.size();i++){
                int rNum = 4+i;
                row = sheet.createRow(rNum);
                cell = row.createCell(0); //code
                cell.setCellValue(list.get(i).getCode());

                cell = row.createCell(1); //title
                cell.setCellValue(list.get(i).getTitle());

                cell = row.createCell(2); //Level
                cell.setCellValue(list.get(i).getLevel());

                cell = row.createCell(3); //Type
                cell.setCellValue(list.get(i).getType());

                cell = row.createCell(4); //서고
                cell.setCellValue(list.get(i).getRepositoryName());

                cell = row.createCell(5); //서가
                cell.setCellValue(list.get(i).getShelfName());

                cell = row.createCell(6); //행렬단
                cell.setCellValue(list.get(i).getLocationName());

                cell = row.createCell(7); //반출서번호
                cell.setCellValue("");

                cell = row.createCell(8); //반입/반출
                cell.setCellValue("");

                cell = row.createCell(8); //반출일
                cell.setCellValue("");

                cell = row.createCell(8); //반입예정일
                cell.setCellValue("");

                cell = row.createCell(8); //반입일
                cell.setCellValue("");

                inCountTotal = inCountTotal + Integer.parseInt(list.get(i).getInCount());
                outCountTotal = outCountTotal + Integer.parseInt(list.get(i).getOutCount());
            }


            if(list.size() > 0){
                shelfCount = Integer.parseInt(list.get(0).getShelfCount());
                rowCount = Integer.parseInt(list.get(0).getRowCount());
            }



            //상단셋팅
            row = sheet.getRow(0);

            cell = row.getCell(2);
            cell.setCellValue(params.getString("repositoryCode"));

            cell = row.getCell(4);
            cell.setCellValue(shelfCount);

            cell = row.getCell(6);
            cell.setCellValue(rowCount);

            row = sheet.getRow(1);

            cell = row.getCell(2);
            cell.setCellValue(aggregationTotalCount);

            cell = row.getCell(4);
            cell.setCellValue(inCountTotal);

            cell = row.getCell(6);
            cell.setCellValue(outCountTotal);


            //기존 0 1새로운시트
            workBook.removeSheetAt(0);
            workBook.write(out);

            return new ByteArrayInputStream(out.toByteArray());
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
