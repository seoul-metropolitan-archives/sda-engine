package rmsoft.ams.seoul.st.st021.service;

import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.code.ApiStatus;
import io.onsemiro.core.domain.BaseService;
import io.onsemiro.core.parameter.RequestParams;
import io.onsemiro.utils.DateUtils;
import io.onsemiro.utils.ModelMapperUtils;
import io.onsemiro.utils.SessionUtils;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import rmsoft.ams.seoul.st.st003.vo.St003;
import rmsoft.ams.seoul.st.st008.service.St008Service;
import rmsoft.ams.seoul.st.st021.dao.St021Mapper;
import rmsoft.ams.seoul.st.st021.vo.St02101VO;


import javax.inject.Inject;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import static rmsoft.ams.seoul.st.st008.service.St008Service.createCellStyle;

@Service
public class St021Service extends BaseService {
    @Inject
    private St021Mapper st021Mapper;

    public Page<St02101VO> getStWithoutNoticeInoutHistStatistic(Pageable pageable, RequestParams<St02101VO> requestParams) {
        St02101VO St02101VO = new St02101VO();
        //St02101VO.setRequestName(requestParams.getString("requestName"));
        //검색조건 추가시
        St02101VO.setRepositoryUuid(requestParams.getString("repositoryUuid"));
        St02101VO.setShelfUuid(requestParams.getString("shelfUuid"));
        St02101VO.setLocationUuid(requestParams.getString("locationUuid"));
        St02101VO.setCode(requestParams.getString("code"));
        St02101VO.setTitle(requestParams.getString("title"));
        St02101VO.setZoneUuid(requestParams.getString("zoneUuid"));
        St02101VO.setInoutDateTimeFrom(requestParams.getString("inoutDateTimeFrom"));
        St02101VO.setInoutDateTimeTo(requestParams.getString("inoutDateTimeTo"));


        return filter(st021Mapper.getStWithoutNoticeInoutHistStatistic(St02101VO), pageable, "", St02101VO.class);
    }

    public ByteArrayInputStream getExcelDown(RequestParams<St02101VO> parameter) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        InputStream is = null;


        //St02101VO voFromParameter = ModelMapperUtils.map(parameter.getMap(), St02101VO.class);
        // 전체 다 이므로 파라미터 없음.
        List<St02101VO> list = st021Mapper.getStWithoutNoticeInoutHistStatistic(new St02101VO());
        try {
            is = St021Service.class.getClassLoader().getResourceAsStream("excelTemp/st021_excel.xlsx");

            XSSFWorkbook workBook = new XSSFWorkbook(is);
            XSSFSheet sheet = null;
            XSSFRow row = null;
            XSSFCell cell = null;

            sheet = workBook.cloneSheet(0);
            workBook.setSheetName(workBook.getSheetIndex(sheet), "Sheet");

            XSSFCellStyle cellStyleCenter = createCellStyle(workBook, "CENTER");
            XSSFCellStyle cellStyleLeft = createCellStyle(workBook, "LEFT");
            XSSFCellStyle cellStyleRight = createCellStyle(workBook, "RIGHT");
            XSSFCellStyle cellStyleNoBorder = createCellStyle(workBook, "NO_BORDER");

            int rowStart = 4; // excel 에서 시작하는 row number
            for (int i = 0; i < list.size(); i++) {

                int rNum = rowStart - 1 + i; // rowStart -1 을 해야 맞음.
                int columnIndex = 0;
                row = sheet.createRow(rNum);
                St02101VO eachObj = list.get(i);

//                cell = row.createCell(columnIndex++); // 일련번호
//                cell.setCellValue(rNum + 1);
//                cell.setCellStyle(cellStyleRight);


                cell = row.createCell(columnIndex++); // Code
                cell.setCellValue(eachObj.getCode());
                cell.setCellStyle(cellStyleLeft);

                cell = row.createCell(columnIndex++); //Title
                cell.setCellValue(eachObj.getTitle());
                cell.setCellStyle(cellStyleLeft);
//                St008Service.mergeCell(workBook, sheet, rNum, columnIndex);
//                columnIndex++; // title 은 두칸이므로 한번더 ++


                cell = row.createCell(columnIndex++); //Level
                cell.setCellValue(eachObj.getLevel());
                cell.setCellStyle(cellStyleCenter);

                cell = row.createCell(columnIndex++); //Type
                cell.setCellValue(eachObj.getType());
                cell.setCellStyle(cellStyleLeft);

//                cell = row.createCell(columnIndex++); //Published Status
//                cell.setCellValue(eachObj.getPublishedStatus());
//                cell.setCellStyle(cellStyleLeft);

                cell = row.createCell(columnIndex++); //Author
                cell.setCellValue(eachObj.getAuthor());
                cell.setCellStyle(cellStyleLeft);

                cell = row.createCell(columnIndex++); //Start Date
                cell.setCellValue(eachObj.getDescStrDate());
                cell.setCellStyle(cellStyleLeft);

                cell = row.createCell(columnIndex++); //End date
                cell.setCellValue(eachObj.getDescEdDate());
                cell.setCellStyle(cellStyleLeft);

                cell = row.createCell(columnIndex++); //서고
                cell.setCellValue(eachObj.getRepositoryName());
                cell.setCellStyle(cellStyleLeft);

                cell = row.createCell(columnIndex++); //서가
                cell.setCellValue(eachObj.getShelfName());
                cell.setCellStyle(cellStyleLeft);

                cell = row.createCell(columnIndex++); //행렬단
                cell.setCellValue(eachObj.getLocationName());
                cell.setCellStyle(cellStyleLeft);

                cell = row.createCell(columnIndex++); //Container
                cell.setCellValue(eachObj.getContainerName());
                cell.setCellStyle(cellStyleLeft);

                cell = row.createCell(columnIndex++); //입출구분
                cell.setCellValue(eachObj.getInoutName());
                cell.setCellStyle(cellStyleLeft);

                cell = row.createCell(columnIndex++); //출입일시
                cell.setCellValue(eachObj.getInoutDateTime());
                cell.setCellStyle(cellStyleLeft);

                cell = row.createCell(columnIndex++); //존
                cell.setCellValue(eachObj.getZoneName());
                cell.setCellStyle(cellStyleLeft);


            }

            //기존 0 1새로운시트
            workBook.removeSheetAt(0);
            workBook.write(out);

            return new ByteArrayInputStream(out.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


}
