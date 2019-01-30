package rmsoft.ams.seoul.st.st015.service;

import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.code.ApiStatus;
import io.onsemiro.core.domain.BaseService;
import io.onsemiro.core.parameter.RequestParams;
import io.onsemiro.utils.ModelMapperUtils;
import io.onsemiro.utils.UUIDUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import rmsoft.ams.seoul.common.domain.StInventoryContainerResult;
import rmsoft.ams.seoul.common.domain.StInventoryPlan;
import rmsoft.ams.seoul.common.domain.StInventoryRecordResult;
import rmsoft.ams.seoul.common.domain.StProgram;
import rmsoft.ams.seoul.common.repository.StInventoryContainerResultRepository;
import rmsoft.ams.seoul.common.repository.StInventoryPlanRepository;
import rmsoft.ams.seoul.common.repository.StInventoryRecordResultRepository;
import rmsoft.ams.seoul.common.repository.StProgramRepository;
import rmsoft.ams.seoul.st.st001.vo.St00101VO;
import rmsoft.ams.seoul.st.st010.service.St010Service;
import rmsoft.ams.seoul.st.st015.dao.St015Mapper;
import rmsoft.ams.seoul.st.st015.vo.St01501VO;
import rmsoft.ams.seoul.st.st015.vo.St01502VO;
import rmsoft.ams.seoul.st.st015.vo.St01503VO;
import rmsoft.ams.seoul.st.st028.vo.St02801VO;
import rmsoft.ams.seoul.st.st029.dao.St029Mapper;
import rmsoft.ams.seoul.st.st029.vo.St02901VO;
import rmsoft.ams.seoul.utils.CommonCodeUtils;

import javax.inject.Inject;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class St015Service extends BaseService {

    @Inject
    private St015Mapper st015Mapper;

    @Autowired
    private StInventoryPlanRepository stInventoryPlanRepository;

    @Autowired
    private StInventoryContainerResultRepository stInventoryContainerResultRepository;

    @Autowired
    private StInventoryRecordResultRepository stInventoryRecordResultRepository;



    public Page<St01501VO> getStInventoryPlan(Pageable pageable, RequestParams<St01501VO> requestParams) {
        St01501VO st01501VO = new St01501VO();
        st01501VO.setPlanName(requestParams.getString("planName"));
        st01501VO.setRepositoryUuid(requestParams.getString("repositoryUuid"));
        st01501VO.setShelfUuid(requestParams.getString("shelfUuid"));
        st01501VO.setCode(requestParams.getString("code"));
        st01501VO.setTitle(requestParams.getString("title"));
        st01501VO.setPlannerUuid(requestParams.getString("plannerUuid"));
        st01501VO.setExceptStartDateFrom(requestParams.getString("exceptStartDateFrom"));
        st01501VO.setExceptStartDateTo(requestParams.getString("exceptStartDateTo"));
        st01501VO.setExceptEndDateFrom(requestParams.getString("exceptEndDateFrom"));
        st01501VO.setExceptEndDateTo(requestParams.getString("exceptEndDateTo"));



        return filter(st015Mapper.getStInventoryPlan(st01501VO), pageable, "", St01501VO.class);
    }

    public Page<St01502VO> getStInventoryContainerResult(Pageable pageable, RequestParams<St01502VO> requestParams) {
        St01502VO st01502VO = new St01502VO();
        st01502VO.setInventoryPlanUuid(requestParams.getString("inventoryPlanUuid"));

        //st02901VO.setGateId(requestParams.getString("gateId"));

        return filter(st015Mapper.getStInventoryContainerResult(st01502VO), pageable, "", St01502VO.class);
    }


    public Page<St01503VO> getStInventoryRecordResult(Pageable pageable, RequestParams<St01503VO> requestParams) {
        St01503VO st01503VO = new St01503VO();
        //st02901VO.setGateId(requestParams.getString("gateId"));
        st01503VO.setInventoryPlanUuid(requestParams.getString("inventoryPlanUuid"));
        st01503VO.setContainerUuid(requestParams.getString("containerUuid"));

        return filter(st015Mapper.getStInventoryRecordResult(st01503VO), pageable, "", St01503VO.class);
    }

    @Transactional
    public ApiResponse saveGetStInventoryPlan(List<St01501VO> list) {
        List<StInventoryPlan> stInventoryPlanList = ModelMapperUtils.mapList(list, StInventoryPlan.class);

        StInventoryPlan orgStInventoryPlan = null;

        for(StInventoryPlan stInventoryPlan : stInventoryPlanList){
            if(stInventoryPlan.isDeleted()){
                stInventoryPlanRepository.delete(stInventoryPlan);
            }else{
                if(stInventoryPlan.isCreated()){
                    stInventoryPlan.setStatusUuid(CommonCodeUtils.getCodeDetailUuid("CD216","Draft"));
                }else if(stInventoryPlan.isModified()){
                    orgStInventoryPlan = stInventoryPlanRepository.findOne(stInventoryPlan.getId());
                    stInventoryPlan.setInsertDate(orgStInventoryPlan.getInsertDate());
                    stInventoryPlan.setInsertUuid(orgStInventoryPlan.getInsertUuid());
                }

                stInventoryPlanRepository.save(stInventoryPlan);
            }
        }

        return ApiResponse.of(ApiStatus.SUCCESS, "SUCCESS");
    }

    public static String ListToString(List<Map<String,Object>> list){
        List<String> resultList = new ArrayList();

        for(int i = 0 ; i < list.size(); i++){
            resultList.add("'"+String.valueOf(list.get(i).get("locationUuid"))+"'");
        }

        return String.join(",", resultList);
    }

    public ApiResponse updateStIventoryPlanStatus(List<St01501VO> list) {
        List<StInventoryPlan> stInventoryPlanList = ModelMapperUtils.mapList(list, StInventoryPlan.class);
        StInventoryPlan orgStInventoryPlan = null;
        int index = 0;
        String changeStatus = "";

        for(StInventoryPlan stInventoryPlan : stInventoryPlanList){
            changeStatus = list.get(index).getChangeStatus() == "" ? "Draft" : list.get(index).getChangeStatus();
            //바뀌는 값이 confirm일때
            if(changeStatus.equals("Confirm")){

                //서고 서가 행렬단에 있는 location이 없을수도 있다.
                //location이 없으면 shelf로 검색해서 모든 location에 있는 애들을 전부 가져 와야 한다.

                String locationUuid = stInventoryPlan.getLocationUuid();
                String shelfUuid = stInventoryPlan.getShelfUuid();

                if(StringUtils.isEmpty(locationUuid)){
                    //location값이 없는경우 서가 밑에 모든 container를 가져와야한다.
                    StringBuilder sb = new StringBuilder();
                    sb.append(" select LOCATION_UUID as locationUuid from ST_LOCATION ");
                    sb.append(" where SHELF_UUID = '"+shelfUuid+"' ");
                    List<Map<String, Object>> locationList = jdbcTemplate.queryForList(sb.toString());
                    String location = ListToString(locationList);

                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(" select CONTAINER_UUID as containerUuid from ST_ARRANGE_CONTAINERS_RESULT ");
                    sb2.append(" where LOCATION_UUID IN ("+location+") ");
                    List<Map<String, Object>> containerList = jdbcTemplate.queryForList(sb2.toString());
                    for(int i = 0 ; i < containerList.size(); i++){
                        StInventoryContainerResult stInventoryContainerResult = new StInventoryContainerResult();
                        stInventoryContainerResult.setInventoryContResultUuid(UUIDUtils.getUUID());
                        stInventoryContainerResult.setInventoryPlanUuid(stInventoryPlan.getInventoryPlanUuid());
                        stInventoryContainerResult.setContainerUuid(String.valueOf(containerList.get(i).get("containerUuid")));

                        stInventoryContainerResultRepository.save(stInventoryContainerResult);
                    }

                    for(int j = 0 ; j < containerList.size(); j++){

                        StringBuilder sb3 = new StringBuilder();
                        sb3.append(" select AGGREGATION_UUID as aggregationUuid from ST_ARRANGE_RECORDS_RESULT ");
                        sb3.append(" where CONTAINER_UUID = '"+String.valueOf(containerList.get(j).get("containerUuid"))+"' ");
                        List<Map<String, Object>> aggregationList = jdbcTemplate.queryForList(sb3.toString());

                        for(int k = 0 ; k < aggregationList.size();k++){
                            StInventoryRecordResult stInventoryRecordResult = new StInventoryRecordResult();
                            stInventoryRecordResult.setInventoryRecordResultUuid(UUIDUtils.getUUID());
                            stInventoryRecordResult.setInventoryPlanUuid(stInventoryPlan.getInventoryPlanUuid());
                            stInventoryRecordResult.setContainerUuid(String.valueOf(containerList.get(j).get("containerUuid")));
                            stInventoryRecordResult.setAggregationUuid(String.valueOf(aggregationList.get(k).get("aggregationUuid")));
                            stInventoryRecordResultRepository.save(stInventoryRecordResult);
                        }

                    }

                }else{
                    //location값이 있는경우
                    StringBuilder sb = new StringBuilder();
                    sb.append(" select CONTAINER_UUID as containerUuid from ST_ARRANGE_CONTAINERS_RESULT ");
                    sb.append(" where LOCATION_UUID = '"+locationUuid+"' ");
                    List<Map<String, Object>> containerList = jdbcTemplate.queryForList(sb.toString());

                    for(int i = 0 ; i < containerList.size(); i++){
                        StInventoryContainerResult stInventoryContainerResult = new StInventoryContainerResult();
                        stInventoryContainerResult.setInventoryContResultUuid(UUIDUtils.getUUID());
                        stInventoryContainerResult.setInventoryPlanUuid(stInventoryPlan.getInventoryPlanUuid());
                        stInventoryContainerResult.setContainerUuid(String.valueOf(containerList.get(i).get("containerUuid")));

                        stInventoryContainerResultRepository.save(stInventoryContainerResult);
                    }

                    //ST_INVENTORY_RECORD_RESULT
                    for(int j = 0 ; j < containerList.size(); j++){

                        StringBuilder sb2 = new StringBuilder();
                        sb2.append(" select AGGREGATION_UUID as aggregationUuid from ST_ARRANGE_RECORDS_RESULT ");
                        sb2.append(" where CONTAINER_UUID = '"+String.valueOf(containerList.get(j).get("containerUuid"))+"' ");
                        List<Map<String, Object>> aggregationList = jdbcTemplate.queryForList(sb2.toString());

                        for(int k = 0 ; k < aggregationList.size();k++){
                            StInventoryRecordResult stInventoryRecordResult = new StInventoryRecordResult();
                            stInventoryRecordResult.setInventoryRecordResultUuid(UUIDUtils.getUUID());
                            stInventoryRecordResult.setInventoryPlanUuid(stInventoryPlan.getInventoryPlanUuid());
                            stInventoryRecordResult.setContainerUuid(String.valueOf(containerList.get(j).get("containerUuid")));
                            stInventoryRecordResult.setAggregationUuid(String.valueOf(aggregationList.get(k).get("aggregationUuid")));
                            stInventoryRecordResultRepository.save(stInventoryRecordResult);
                        }

                    }


                }

            }else{

                StringBuilder sb = new StringBuilder();
                sb.append(" SELECT INVENTORY_CONT_RESULT_UUID as inventoryContResultUuid  FROM ST_INVENTORY_CONTAINER_RESULT ");
                sb.append(" WHERE INVENTORY_PLAN_UUID = '"+stInventoryPlan.getInventoryPlanUuid()+"' ");
                List<Map<String, Object>> inventoryContainter = jdbcTemplate.queryForList(sb.toString());

                for(int i = 0 ; i < inventoryContainter.size(); i++){
                    StInventoryContainerResult stInventoryContainerResult = new StInventoryContainerResult();
                    stInventoryContainerResult.setInventoryContResultUuid(String.valueOf(inventoryContainter.get(i).get("inventoryContResultUuid")));
                    stInventoryContainerResultRepository.delete(stInventoryContainerResult);
                }

                StringBuilder sb2 = new StringBuilder();
                sb2.append(" SELECT INVENTORY_RECORD_RESULT_UUID as inventoryRecordResultUuid  FROM ST_INVENTORY_RECORD_RESULT ");
                sb2.append(" WHERE INVENTORY_PLAN_UUID = '"+stInventoryPlan.getInventoryPlanUuid()+"' ");
                List<Map<String, Object>> inventoryRecord = jdbcTemplate.queryForList(sb2.toString());

                for(int j = 0 ; j < inventoryRecord.size(); j++){
                    StInventoryRecordResult stInventoryRecordResult = new StInventoryRecordResult();
                    stInventoryRecordResult.setInventoryRecordResultUuid(String.valueOf(inventoryRecord.get(j).get("inventoryRecordResultUuid")));
                    stInventoryRecordResultRepository.delete(stInventoryRecordResult);
                }

            }


            orgStInventoryPlan = stInventoryPlanRepository.findOne(stInventoryPlan.getId());
            stInventoryPlan.setStatusUuid(CommonCodeUtils.getCodeDetailUuid("CD216",changeStatus));
            stInventoryPlan.setInsertDate(orgStInventoryPlan.getInsertDate());
            stInventoryPlan.setInsertUuid(orgStInventoryPlan.getInsertUuid());
            stInventoryPlanRepository.save(stInventoryPlan);
            index++;
        }
        return ApiResponse.of(ApiStatus.SUCCESS, "SUCCESS");

    }

    public ByteArrayInputStream getExcelDown() throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        InputStream is = null;

        St01501VO st01501VO = new St01501VO();
        /*st01501VO.setPlanName(requestParams.getString("planName"));
        st01501VO.setRepositoryUuid(requestParams.getString("repositoryUuid"));
        st01501VO.setShelfUuid(requestParams.getString("shelfUuid"));
        st01501VO.setCode(requestParams.getString("code"));
        st01501VO.setTitle(requestParams.getString("title"));
        st01501VO.setPlannerUuid(requestParams.getString("plannerUuid"));
        st01501VO.setExceptStartDate(requestParams.getString("exceptStartDate"));
        st01501VO.setExceptEndDate(requestParams.getString("exceptEndDate"));*/

        List<St01501VO> list = st015Mapper.getStInventoryPlan(st01501VO);


        try {
            is = St010Service.class.getClassLoader().getResourceAsStream("excelTemp/st015_excel.xlsx");

            XSSFWorkbook workBook = new XSSFWorkbook(is);
            XSSFSheet sheet = null;
            XSSFRow row = null;
            XSSFCell cell = null;

            sheet = workBook.cloneSheet(0);
            workBook.setSheetName(workBook.getSheetIndex(sheet), "테스트 시트");

            //CELL STYLE 적용
            XSSFCellStyle cellStyle = workBook.createCellStyle();
            cellStyle.setBorderTop(CellStyle.BORDER_THIN);
            cellStyle.setBorderBottom(CellStyle.BORDER_THIN);
            cellStyle.setBorderLeft(CellStyle.BORDER_THIN);
            cellStyle.setBorderRight(CellStyle.BORDER_THIN);

            for(int i = 0; i < list.size();i++){
                int rNum = 9+i;
                row = sheet.createRow(rNum);
                cell = row.createCell(0); //계획서명
                cell.setCellValue(list.get(i).getPlanName()); cell.setCellStyle(cellStyle);
                cell = row.createCell(1); //담당자
                cell.setCellValue(list.get(i).getPlannerName()); cell.setCellStyle(cellStyle);
                cell = row.createCell(2); //예정일 시작
                cell.setCellValue(list.get(i).getExceptStartDate()); cell.setCellStyle(cellStyle);
                cell = row.createCell(3); //예정일 종료
                cell.setCellValue(list.get(i).getExceptEndDate()); cell.setCellStyle(cellStyle);
                cell = row.createCell(4); //범위 서고
                cell.setCellValue(list.get(i).getRepositoryName()); cell.setCellStyle(cellStyle);
                cell = row.createCell(5); //서가
                cell.setCellValue(list.get(i).getShelfName()); cell.setCellStyle(cellStyle);
                cell = row.createCell(6); //행렬단
                cell.setCellValue(list.get(i).getLocationName()); cell.setCellStyle(cellStyle);
                cell = row.createCell(7); //상태
                cell.setCellValue(list.get(i).getStatusName()); cell.setCellStyle(cellStyle);
                cell = row.createCell(8); //점검결과
                cell.setCellValue(list.get(i).getPlanResultName()); cell.setCellStyle(cellStyle);
                cell = row.createCell(9); //비고
                cell.setCellValue(list.get(i).getDescription()); cell.setCellStyle(cellStyle);
            }

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

    public void deleteInventoryRecordResult(St01503VO obj) {
        StInventoryRecordResult stInventoryRecordResult = new StInventoryRecordResult();
        stInventoryRecordResult.setInventoryRecordResultUuid(obj.getInventoryRecordResultUuid());


            stInventoryRecordResultRepository.delete(stInventoryRecordResult);

    }
}
