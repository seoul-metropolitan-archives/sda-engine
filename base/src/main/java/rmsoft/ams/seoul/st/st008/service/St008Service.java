package rmsoft.ams.seoul.st.st008.service;

import io.onsemiro.core.api.ApiException;
import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.code.ApiStatus;
import io.onsemiro.core.domain.BaseService;
import io.onsemiro.core.parameter.RequestParams;
import io.onsemiro.utils.DateUtils;
import io.onsemiro.utils.ModelMapperUtils;
import io.onsemiro.utils.SessionUtils;
import io.onsemiro.utils.UUIDUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.ss.util.CellUtil;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rmsoft.ams.seoul.common.domain.*;
import rmsoft.ams.seoul.common.repository.*;
import rmsoft.ams.seoul.st.st008.dao.St008Mapper;
import rmsoft.ams.seoul.st.st008.vo.St00801VO;
import rmsoft.ams.seoul.st.st008.vo.St00802VO;
import rmsoft.ams.seoul.st.st008.vo.St00802pVO;

import javax.inject.Inject;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;


/**
 * The type Cl 003 service.
 */
@Service
public class St008Service extends BaseService {

    @Inject
    private St008Mapper st008Mapper;
    @Autowired
    private StTakeoutRequestRepository stTakeoutRequestRepository;
    @Autowired
    private StTakeoutRecordResultRepository stTakeoutRecordResultRepository;

    private static short BORDER_STYLE = CellStyle.BORDER_THIN;

    /**
     * Gets classified record list.
     *
     * @param pageable      the pageable
     * @param requestParams the request params
     * @return the classified record list
     */
    public Page<St00801VO> getStTakeoutRequest(Pageable pageable, RequestParams<St00801VO> requestParams) {

        St00801VO st00801VO = new St00801VO();

        st00801VO.setRequestName(requestParams.getString("requestName")); // 반출의뢰서
        st00801VO.setTakeoutDateFrom(requestParams.getString("takeoutDateFrom"));
        st00801VO.setTakeoutDateTo(requestParams.getString("takeoutDateTo"));
        st00801VO.setReturnDueDateFrom(requestParams.getString("returnDueDateFrom"));
        st00801VO.setReturnDueDateTo(requestParams.getString("returnDueDateTo"));
        st00801VO.setRequestorUuid(requestParams.getString("requestorUuid")); // 반출자
        st00801VO.setCode(requestParams.getString("code"));
        st00801VO.setTitle(requestParams.getString("title"));
        st00801VO.setCurrentLoginUserUuid(SessionUtils.getCurrentLoginUserUuid());
        System.out.println("LOGIN USER UUID : "+SessionUtils.getCurrentLoginUserUuid());
        return filter(st008Mapper.getStTakeoutRequest(st00801VO), pageable, "", St00801VO.class);
    }


    public Page<St00802VO> getStTakeoutRecordResult(Pageable pageable, RequestParams<St00802VO> requestParams) {

        St00802VO vo = new St00802VO();
        vo.setTakeoutRequestUuid(requestParams.getString("takeoutRequestUuid"));
        vo.setCode(requestParams.getString("code"));
        vo.setTitle(requestParams.getString("title"));

        List<St00802VO> aStTakeOutRecordResult = st008Mapper.getStTakeoutRecordResult(vo);
        return filter(aStTakeOutRecordResult, pageable, "", St00802VO.class);
    }

    @Transactional
    public ApiResponse saveStTakeoutRequestList(List<St00801VO> list) {
        // 삭제만 가능

        List<StTakeoutRequest> stTakeoutRequestList = ModelMapperUtils.mapList(list, StTakeoutRequest.class);

        for (StTakeoutRequest stTakeoutRequest : stTakeoutRequestList) {
            if (stTakeoutRequest.isDeleted()) {
                St00802VO vo = new St00802VO();
                vo.setTakeoutRequestUuid(stTakeoutRequest.getTakeoutRequestUuid());
                // 하위에 연결된 레코드가 없어야 삭제가능.
                List<St00802VO> aStTakeOutRecordResult = st008Mapper.getStTakeoutRecordResult(vo);
                if( aStTakeOutRecordResult.size() == 0){
                    // 하위 레코드 ( 반출 신청 대상 ) 이 없음
                    stTakeoutRequestRepository.delete(stTakeoutRequest);
                }else{
                    // 하위 레코드 ( 반출 신청 대상 ) 이 있음
                    throw new ApiException(ApiStatus.SYSTEM_ERROR, "해당 반출서 하위에 있는 '반출신청대상'을 먼저 삭제 해 주세요.");
                }

            }
        }
        return ApiResponse.of(ApiStatus.SUCCESS, "SUCCESS");

    }

    @Transactional
    public ApiResponse saveStTakeoutRecordResultList(List<St00802VO> list) {
        // 삭제만 가능
        List<StTakeoutRecordResult> stTakeoutRequestList = ModelMapperUtils.mapList(list, StTakeoutRecordResult.class);

        for (StTakeoutRecordResult stTakeoutRecordResult : stTakeoutRequestList) {
            if (stTakeoutRecordResult.isDeleted()) {
                stTakeoutRecordResultRepository.delete(stTakeoutRecordResult);
            }
        }
        return ApiResponse.of(ApiStatus.SUCCESS, "SUCCESS");
    }

    @Transactional
    public ApiResponse saveStTakeoutRequest(St00801VO vo) {

        vo.setRequestorUuid(SessionUtils.getCurrentLoginUserUuid());
        // 투두 : return date 가 not null 이므로 duedate 를 일단 넣어주자. 나중에 물어볼것. return date 가 뭔지.
        //vo.setReturnDate(vo.getReturnDueDate());
        boolean isCreateOrModify = false;
        String uuid = vo.getTakeoutRequestUuid();
        if (uuid == null) {

            // 새로 생성
            isCreateOrModify = true;
            // String orgCodeSeq = jdbcTemplate.queryForObject("SELECT ST_TAKEOUT_REQUEST_SEQ.NEXTVAL FROM dual", String.class);
            // 오늘자 들어가있는애들의 갯수를 가져와 시퀀스 생성
            int count = jdbcTemplate.queryForObject("SELECT count(*) FROM ST_TAKEOUT_REQUEST WHERE to_char(INSERT_DATE, 'YYYY/MM/DD') = to_char(SYSDATE, 'YYYY/MM/DD')", Integer.class);
            String todaySequence = Integer.toString(count + 1); // 하나 늘려주자
            String refinedCodeSeq = todaySequence;

            // 년월일-시퀀스 두자리. ex)20181121-01. 하루에 99개밖에 못만들게 돼있음.
            for (int cnt = 0; cnt < Math.abs(todaySequence.length() - 2); cnt++) {
                refinedCodeSeq = "0" + refinedCodeSeq;
            }
            String requestName = DateUtils.convertToString(LocalDateTime.now(), "yyyyMMdd") + "-" + refinedCodeSeq;
            vo.setTakeoutRequestUuid(UUIDUtils.getUUID());
            vo.setRequestName(requestName);

            vo.setInsertDate(Timestamp.valueOf(DateUtils.convertToString(LocalDateTime.now(), DateUtils.DATE_TIME_PATTERN)));
            vo.setInsertUuid(UUIDUtils.getUUID());
            vo.setUpdateDate(Timestamp.valueOf(DateUtils.convertToString(LocalDateTime.now(), DateUtils.DATE_TIME_PATTERN)));
            vo.setUpdateUuid(UUIDUtils.getUUID());
            vo.setStatusUuid("6B1C7487-99F3-4F04-B449-891AD4679E00"); // 반출서 작성
        }


        StTakeoutRequest stTakeoutRequest = ModelMapperUtils.map(vo, StTakeoutRequest.class);
        StTakeoutRequest orgClClass = stTakeoutRequestRepository.findOne(stTakeoutRequest.getId());
        if (isCreateOrModify == true) {
            // 새로 생성
            // do nothing
        } else {
            // 수정

            orgClClass.setUpdateDate(Timestamp.valueOf(DateUtils.convertToString(LocalDateTime.now(), DateUtils.DATE_TIME_PATTERN)));
            orgClClass.setTakeoutDate(stTakeoutRequest.getTakeoutDate());
            orgClClass.setReturnDueDate(stTakeoutRequest.getReturnDueDate());
            orgClClass.setTakeoutPropose(stTakeoutRequest.getTakeoutPropose());
            orgClClass.setRequestTypeUuid(stTakeoutRequest.getRequestTypeUuid());
            orgClClass.setStatusUuid(stTakeoutRequest.getStatusUuid());
            orgClClass.setOutsourcingDepartment(stTakeoutRequest.getOutsourcingDepartment());
            orgClClass.setOutsourcingPosition(stTakeoutRequest.getOutsourcingPosition());
            orgClClass.setOutsourcingPersonName(stTakeoutRequest.getOutsourcingPersonName());
            orgClClass.setOutsourcingPhone(stTakeoutRequest.getOutsourcingPhone());

            // clClass.getRequestorName(); // 반출자
            // clClass.getUser(); //  소속
            // clClass.getUser(); //  직위
            stTakeoutRequest = orgClClass;
        }

        stTakeoutRequestRepository.save(stTakeoutRequest);
        return ApiResponse.of(ApiStatus.SUCCESS, "SUCCESS");
    }

    @Transactional
    public ApiResponse saveStTakeoutAdd(St00802pVO st00802pVO) {
        List<St00802VO> st00802VOList = st00802pVO.getSt00802VOList();
        List<StTakeoutRecordResult> clClassifiedRecordsList = ModelMapperUtils.mapList(st00802VOList, StTakeoutRecordResult.class);
        // StTakeoutRecordResult orgClClassifyRecordsResult = null;
        int i = 0;
        for (StTakeoutRecordResult stTakeoutRecordResult : clClassifiedRecordsList) {
            if (st00802pVO.getTakeoutRequestUuid() == null) { //삭제
                stTakeoutRecordResultRepository.delete(stTakeoutRecordResult);
            }
//            else if(null != clClassifyRecordsResult.getClassifyRecordsUuid() && !"".equals(clClassifyRecordsResult.getClassifyRecordsUuid())){
//                //수정
//                orgClClassifyRecordsResult = clClassifyRecordResultRepository.findOne(clClassifyRecordsResult.getId());
//                orgClClassifyRecordsResult.setChoiceYn(clClassifyRecordsResult.getChoiceYn());
//                orgClClassifyRecordsResult.setStatusUuid(CommonCodeUtils.getCodeDetailUuid("CD111","Confirm"));
//                clClassifyRecordResultRepository.save(orgClClassifyRecordsResult);
//            }
            else { //신규
                stTakeoutRecordResult.setTakeoutRecordResultUuid(UUIDUtils.getUUID());
                stTakeoutRecordResult.setTakeoutRequestUuid(st00802pVO.getTakeoutRequestUuid());
                //stTakeoutRecordResult.setAggregationUuid(st00802pVO.getuu);
                stTakeoutRecordResultRepository.save(stTakeoutRecordResult);
            }
        }
        return ApiResponse.of(ApiStatus.SUCCESS, "SUCCESS");
    }

    public static XSSFCellStyle createCellStyle(XSSFWorkbook workBook, String type) {
        XSSFCellStyle cellStyle = workBook.createCellStyle();
        if (type.equals("NO_BORDER")) {
            // border 적용 안함
        } else {
            // border 적용
            //CELL STYLE 적용
            cellStyle.setBorderTop(BORDER_STYLE);
            cellStyle.setBorderBottom(BORDER_STYLE);
            cellStyle.setBorderLeft(BORDER_STYLE);
            cellStyle.setBorderRight(BORDER_STYLE);
        }

        if (type.equals("LEFT")) {
            cellStyle.setAlignment(HorizontalAlignment.LEFT);
        } else if (type.equals("RIGHT")) {
            cellStyle.setAlignment(HorizontalAlignment.RIGHT);
        } else {
            // 나머지는 모두 center
            cellStyle.setAlignment(HorizontalAlignment.CENTER);
        }
        // vertical 은 모두 center
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        return cellStyle;
    }

    public static void mergeCell(XSSFWorkbook workBook, XSSFSheet sheet, int rNum, int columnIndex) {
        CellRangeAddress mergedCell = new CellRangeAddress(
                rNum, //first row (0-based)
                rNum, //last row  (0-based)
                columnIndex - 1, //first column (0-based)
                columnIndex  //last column  (0-based)
        );
        sheet.addMergedRegion(mergedCell);
        RegionUtil.setBorderTop(BORDER_STYLE, mergedCell, sheet, workBook);
        RegionUtil.setBorderBottom(BORDER_STYLE, mergedCell, sheet, workBook);
        RegionUtil.setBorderLeft(BORDER_STYLE, mergedCell, sheet, workBook);
        RegionUtil.setBorderRight(BORDER_STYLE, mergedCell, sheet, workBook);
    }

    public static void setTextToCell(XSSFSheet sheet, int rowNumber, String columnName, String textToSet, XSSFCellStyle cellStyle) {
        int colIdx = CellReference.convertColStringToIndex(columnName.trim().toUpperCase());
        // excel에서 보이는 row number 에서 -1 을 해야 됨.
        Cell cell = CellUtil.getCell(sheet.getRow(rowNumber - 1), colIdx);
        cell.setCellStyle(cellStyle);
        cell.setCellValue(textToSet);
    }

    public static String getNowWithoutTime() {
        return DateUtils.convertToString(LocalDateTime.now(), DateUtils.DATE_TIME_PATTERN).substring(0, 10);
    }

    public ByteArrayInputStream getExcelDown(RequestParams<St00801VO> parameter) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        InputStream is = null;
        String requestTypeUuid = parameter.getString("requestTypeUuid");
        boolean isInOrOutPerson = false;// 내부 : true,  외부 : false
        if (requestTypeUuid == null || requestTypeUuid.equals("AF04136D-3508-4E1C-A85B-F1A1FEDDB607")) {
            // 내부직원
            isInOrOutPerson = true;
        } else {
            // 외부직원
            isInOrOutPerson = false;
        }
        St00802VO resultVO = ModelMapperUtils.map(parameter.getMap(), St00802VO.class);

        List<St00802VO> list = st008Mapper.getStTakeoutRecordResult(resultVO);
        try {
            is = St008Service.class.getClassLoader().getResourceAsStream("excelTemp/st008_excel.xlsx");

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
            // 출력일자
            setTextToCell(sheet, 5, "O", getNowWithoutTime(), cellStyleNoBorder);

            setTextToCell(sheet, 8, "D", parameter.getString("takeoutDate"), cellStyleLeft); // 반출일자
            setTextToCell(sheet, 9, "D", parameter.getString("returnDueDate"), cellStyleLeft); // 반입예정일

            if (isInOrOutPerson == true) {
                // 내부
                setTextToCell(sheet, 8, "E", parameter.getString("userGroupName"), cellStyleCenter); // 소속
                // 내부직원은 직급 없음
                setTextToCell(sheet, 8, "G", "", cellStyleCenter); // 직급
                setTextToCell(sheet, 8, "H", parameter.getString("requestorName"), cellStyleCenter); // 성명
            } else {
                // 외부
                setTextToCell(sheet, 8, "E", parameter.getString("outsourcingDepartment"), cellStyleCenter); // 소속
                setTextToCell(sheet, 8, "G", parameter.getString("outsourcingPosition"), cellStyleCenter); // 직급
                setTextToCell(sheet, 8, "H", parameter.getString("outsourcingPersonName"), cellStyleCenter); // 성명
            }

            setTextToCell(sheet, 10, "D", parameter.getString("takeoutPropose"), cellStyleCenter); // 반출 목적

            int rowStart = 14; // excel 에서 시작하는 row number
            for (int i = 0; i < list.size(); i++) {

                int rNum = rowStart - 1 + i; // rowStart -1 을 해야 맞음.
                int columnIndex = 1; // 앞에 한칸이 비어있으므로 1부터 시작
                row = sheet.createRow(rNum);
                St00802VO eachObj = list.get(i);

                cell = row.createCell(columnIndex++); // 일련번호
                cell.setCellValue(rNum + 1);
                cell.setCellStyle(cellStyleRight);


                cell = row.createCell(columnIndex++); // Code
                cell.setCellValue(eachObj.getCode());
                cell.setCellStyle(cellStyleLeft);

                cell = row.createCell(columnIndex++); //Title
                cell.setCellValue(eachObj.getTitle());
                cell.setCellStyle(cellStyleLeft);
                mergeCell(workBook, sheet, rNum, columnIndex);
                columnIndex++; // title 은 두칸이므로 한번더 ++


                cell = row.createCell(columnIndex++); //Level
                cell.setCellValue(eachObj.getLevel());
                cell.setCellStyle(cellStyleCenter);

                cell = row.createCell(columnIndex++); //Type
                cell.setCellValue(eachObj.getType());
                cell.setCellStyle(cellStyleLeft);

                cell = row.createCell(columnIndex++); //Published Status
                cell.setCellValue(eachObj.getPublishedStatus());
                cell.setCellStyle(cellStyleLeft);

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
