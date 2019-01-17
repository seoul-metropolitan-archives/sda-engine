package rmsoft.ams.seoul.st.st008.service;

import com.querydsl.core.types.Predicate;
import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.code.ApiStatus;
import io.onsemiro.core.domain.BaseService;
import io.onsemiro.core.parameter.RequestParams;
import io.onsemiro.utils.DateUtils;
import io.onsemiro.utils.ModelMapperUtils;
import io.onsemiro.utils.SessionUtils;
import io.onsemiro.utils.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rmsoft.ams.seoul.common.domain.*;
import rmsoft.ams.seoul.common.repository.*;
import rmsoft.ams.seoul.st.st003.vo.St00301VO;
import rmsoft.ams.seoul.st.st008.dao.St008Mapper;
import rmsoft.ams.seoul.st.st008.vo.St00801VO;
import rmsoft.ams.seoul.st.st008.vo.St00802VO;
import rmsoft.ams.seoul.st.st008.vo.St00802pVO;
import rmsoft.ams.seoul.utils.CommonCodeUtils;

import javax.inject.Inject;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static rmsoft.ams.seoul.common.domain.QStShelf.stShelf;


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
        List<StTakeoutRequest> stTakeoutRequestList = ModelMapperUtils.mapList(list,StTakeoutRequest.class);

        for(StTakeoutRequest stTakeoutRequest : stTakeoutRequestList) {
            if(stTakeoutRequest.isDeleted()){
                stTakeoutRequestRepository.delete(stTakeoutRequest);
            }
        }
        return ApiResponse.of(ApiStatus.SUCCESS, "SUCCESS");
    }

    @Transactional
    public ApiResponse saveStTakeoutRecordResultList(List<St00802VO> list) {
        // 삭제만 가능
        List<StTakeoutRecordResult> stTakeoutRequestList = ModelMapperUtils.mapList(list,StTakeoutRecordResult.class);

        for(StTakeoutRecordResult stTakeoutRecordResult : stTakeoutRequestList) {
            if(stTakeoutRecordResult.isDeleted()){
                stTakeoutRecordResultRepository.delete(stTakeoutRecordResult);
            }
        }
        return ApiResponse.of(ApiStatus.SUCCESS, "SUCCESS");
    }

    @Transactional
    public ApiResponse saveStTakeoutRequest(St00801VO vo) {

        vo.setRequestorUuid(SessionUtils.getCurrentLoginUserUuid());
        // 투두 : return date 가 not null 이므로 duedate 를 일단 넣어주자. 나중에 물어볼것. return date 가 뭔지.
        vo.setReturnDate(vo.getReturnDueDate());
        boolean isCreateOrModify = false;
        String uuid = vo.getTakeoutRequestUuid();
        if( uuid == null) {

            // 새로 생성
            isCreateOrModify = true;
            String orgCodeSeq = jdbcTemplate.queryForObject("SELECT ST_SHELF_CODE_SEQ.NEXTVAL FROM dual", String.class);
            String refinedCodeSeq = orgCodeSeq;
            int cnt = 0;
            for ( ; cnt < Math.abs(refinedCodeSeq.length() - 2); cnt++) {
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
        if (isCreateOrModify ==  true) {
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
        List<StTakeoutRecordResult> clClassifiedRecordsList = ModelMapperUtils.mapList(st00802VOList,StTakeoutRecordResult.class);
        // StTakeoutRecordResult orgClClassifyRecordsResult = null;
        int i = 0;
        for(StTakeoutRecordResult stTakeoutRecordResult : clClassifiedRecordsList) {
            if(st00802pVO.getTakeoutRequestUuid()== null){ //삭제
                stTakeoutRecordResultRepository.delete(stTakeoutRecordResult);
            }
//            else if(null != clClassifyRecordsResult.getClassifyRecordsUuid() && !"".equals(clClassifyRecordsResult.getClassifyRecordsUuid())){
//                //수정
//                orgClClassifyRecordsResult = clClassifyRecordResultRepository.findOne(clClassifyRecordsResult.getId());
//                orgClClassifyRecordsResult.setChoiceYn(clClassifyRecordsResult.getChoiceYn());
//                orgClClassifyRecordsResult.setStatusUuid(CommonCodeUtils.getCodeDetailUuid("CD111","Confirm"));
//                clClassifyRecordResultRepository.save(orgClClassifyRecordsResult);
//            }
            else{ //신규
                stTakeoutRecordResult.setTakeoutRecordResultUuid(UUIDUtils.getUUID());
                stTakeoutRecordResult.setTakeoutRequestUuid(st00802pVO.getTakeoutRequestUuid());
                //stTakeoutRecordResult.setAggregationUuid(st00802pVO.getuu);
                stTakeoutRecordResultRepository.save(stTakeoutRecordResult);
            }
        }
        return ApiResponse.of(ApiStatus.SUCCESS, "SUCCESS");
    }

    /*public ApiResponse updateStatus(List<St00801VO> list) {
        List<ClClassifyRecordsResult> clClassifiedRecordsList = ModelMapperUtils.mapList(list,ClClassifyRecordsResult.class);
        ClClassifyRecordsResult orgClClassifyRecordsResult = null;
        int index = 0;
        String changeStatus = "";
        for(ClClassifyRecordsResult clClassifyRecordsResult : clClassifiedRecordsList) {
            changeStatus = list.get(index).getChangeStatus() == "" ? "Draft" : list.get(index).getChangeStatus();
            orgClClassifyRecordsResult = clClassifyRecordResultRepository.findOne(clClassifyRecordsResult.getId());
            clClassifyRecordsResult.setStatusUuid(CommonCodeUtils.getCodeDetailUuid("CD111",changeStatus));
            clClassifyRecordsResult.setInsertDate(orgClClassifyRecordsResult.getInsertDate());
            clClassifyRecordsResult.setInsertUuid(orgClClassifyRecordsResult.getInsertUuid());
            clClassifyRecordResultRepository.save(clClassifyRecordsResult);
            index++;
        }
        return ApiResponse.of(ApiStatus.SUCCESS, "SUCCESS");
    }
    @Transactional
    public ApiResponse saveClassifiedRecordList(St00802VO st00802VO) {
        List<St00801VO> st00801VOList = st00802VO.getst00801VOList();
        List<ClClassifyRecordsResult> clClassifiedRecordsList = ModelMapperUtils.mapList(st00801VOList,ClClassifyRecordsResult.class);
        ClClassifyRecordsResult orgClClassifyRecordsResult = null;
        int i = 0;
        for(ClClassifyRecordsResult clClassifyRecordsResult : clClassifiedRecordsList) {
            if(st00802VO.getClassUuid()== null){ //삭제
                clClassifyRecordResultRepository.delete(clClassifyRecordsResult);
            }else if(null != clClassifyRecordsResult.getClassifyRecordsUuid() && !"".equals(clClassifyRecordsResult.getClassifyRecordsUuid())){ //수정
                orgClClassifyRecordsResult = clClassifyRecordResultRepository.findOne(clClassifyRecordsResult.getId());
                orgClClassifyRecordsResult.setChoiceYn(clClassifyRecordsResult.getChoiceYn());
                orgClClassifyRecordsResult.setStatusUuid(CommonCodeUtils.getCodeDetailUuid("CD111","Confirm"));
                clClassifyRecordResultRepository.save(orgClClassifyRecordsResult);
            }else{ //신규
                clClassifyRecordsResult.setClassifyRecordsUuid(UUIDUtils.getUUID());
                clClassifyRecordsResult.setClassUuid(st00802VO.getClassUuid());
                clClassifyRecordsResult.setStatusUuid(CommonCodeUtils.getCodeDetailUuid("CD111","Confirm"));
                clClassifyRecordsResult.setClassifiedDate(Timestamp.valueOf(DateUtils.convertToString(LocalDateTime.now(), DateUtils.DATE_TIME_PATTERN)));
                clClassifyRecordResultRepository.save(clClassifyRecordsResult);
            }
        }
        return ApiResponse.of(ApiStatus.SUCCESS, "SUCCESS");
    }
    public Page<St00303VO> getStTakeoutRecordResult(Pageable pageable, RequestParams<St00303VO> requestParams) {

        St00303VO st00303VO = new St00303VO();
        st00303VO.setAggregationUuid(requestParams.getString("aggregationUuid"));
        st00303VO.setClassUuid(requestParams.getString("classUuid"));

        return filter(st008Mapper.getStTakeoutRecordResult(st00303VO), pageable, "", St00303VO.class);
    }
    public Page<St00303VO> getStTakeoutRecordResultSchedule(Pageable pageable, RequestParams<St00303VO> requestParams) {

        St00303VO st00303VO = new St00303VO();
        st00303VO.setAggregationUuid(requestParams.getString("aggregationUuid"));
        st00303VO.setRecordScheduleUuid(requestParams.getString("recordScheduleUuid"));

        return filter(st008Mapper.getStTakeoutRecordResultSchedule(st00303VO), pageable, "", St00303VO.class);
    }
    public Cl00201VO getClassInfo(Pageable pageable, RequestParams<Cl00201VO> params) {
        Cl00201VO cl00201VO = new Cl00201VO();
        cl00201VO.setClassUuid(params.getString("classUuid"));
        ClClass clClass = ModelMapperUtils.map(cl00201VO, ClClass.class);
        clClass = clClassRepository.findOne(clClass.getId());
        cl00201VO = ModelMapperUtils.map(clClass,Cl00201VO.class);

        ClClassCon clClassCon = ModelMapperUtils.map(cl00201VO, ClClassCon.class);
        clClassCon = clClassConRepository.findOne(clClassCon.getId());

        cl00201VO.setScopeContent(clClassCon.getScopeContent());
        cl00201VO.setRulesConversionUuid(clClassCon.getRulesConversionUuid());
        return cl00201VO;
    }
    public List<Rc00101VO> getAllNode(Rc00101VO param)
    {
        ArrayList<Rc00101VO> nodes = new ArrayList<Rc00101VO>();
        nodes.addAll(st008Mapper.getAggregationNode(param));
        return nodes;
    }
    public List<Rc00101VO> getAllNodeSchedule(Rc00101VO param)
    {
        ArrayList<Rc00101VO> nodes = new ArrayList<Rc00101VO>();
        nodes.addAll(st008Mapper.getAggregationNodeSchedule(param));
        return nodes;
    }
    @Transactional
    public ApiResponse saveClassDescription(Cl00201VO cl00201VO){
        ClClass clClass = ModelMapperUtils.map(cl00201VO, ClClass.class);
        ClClass orgClClass = clClassRepository.findOne(clClass.getId());
        if(orgClClass != null){
            clClass = orgClClass;
            clClass.setDescription(cl00201VO.getDescription());
            clClass.setStatusDescription(cl00201VO.getStatusDescription());
            clClass.setLevelOfDetailUuid(cl00201VO.getLevelOfDetailUuid());
            clClassRepository.save(clClass);
        }
        ClClassCon clClassCon = ModelMapperUtils.map(cl00201VO, ClClassCon.class);
        ClClassCon orgClClassCon = clClassConRepository.findOne(clClassCon.getId());

        if(orgClClassCon != null){
            orgClClassCon.setScopeContent(clClassCon.getScopeContent());
            orgClClassCon.setRulesConversionUuid(clClassCon.getRulesConversionUuid());
            orgClClassCon.setUpdateDate(null);
            orgClClassCon.setUpdateUuid(null);
            clClassConRepository.save(orgClClassCon);
        }else{
            clClassConRepository.save(clClassCon);
        }

        return ApiResponse.of(ApiStatus.SUCCESS, "SUCCESS");
    }*/
}
