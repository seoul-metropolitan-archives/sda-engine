package rmsoft.ams.seoul.st.st009.service;

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
import rmsoft.ams.seoul.common.domain.StTakeoutRequest;
import rmsoft.ams.seoul.common.repository.StTakeoutRecordResultRepository;
import rmsoft.ams.seoul.common.repository.StTakeoutRequestRepository;
import rmsoft.ams.seoul.st.st009.dao.St009Mapper;
import rmsoft.ams.seoul.st.st009.vo.St00901VO;
import rmsoft.ams.seoul.st.st009.vo.St00902VO;
import rmsoft.ams.seoul.utils.CommonCodeUtils;

import javax.inject.Inject;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class St009Service extends BaseService {


    @Inject
    private St009Mapper st009Mapper;

    @Autowired
    private StTakeoutRequestRepository stTakeoutRequestRepository;

    @Autowired
    private StTakeoutRecordResultRepository stTakeoutRecordResultRepository;


    public Page<St00901VO> getTakeoutRequest(Pageable pageable, RequestParams<St00901VO> requestParams) {

        St00901VO st00901VO = new St00901VO();
        st00901VO.setRequestName(requestParams.getString("requestName")); // 반출의뢰서
        st00901VO.setTakeoutDateFrom(requestParams.getString("takeoutDateFrom"));
        st00901VO.setTakeoutDateTo(requestParams.getString("takeoutDateTo"));
        st00901VO.setReturnDueDateFrom(requestParams.getString("returnDueDateFrom"));
        st00901VO.setReturnDueDateTo(requestParams.getString("returnDueDateTo"));
        st00901VO.setRequestorUuid(requestParams.getString("requestorUuid")); // 반출자


        return filter(st009Mapper.getTakeoutRequest(st00901VO), pageable, "", St00901VO.class);
    }

    public Page<St00902VO> getTakeoutRecordResult(Pageable pageable, RequestParams<St00902VO> requestParams) {
        St00902VO st00902VO = new St00902VO();
        st00902VO.setTakeoutRequestUuid(requestParams.getString("takeoutRequestUuid"));
        st00902VO.setCode(requestParams.getString("code"));
        st00902VO.setTitle(requestParams.getString("title"));
        return filter(st009Mapper.getTakeoutRecordResult(st00902VO), pageable, "", St00902VO.class);
    }

    public ApiResponse updateTakeoutRequest(List<St00901VO> list) {

        List<StTakeoutRequest> stTakeoutRequestList = ModelMapperUtils.mapList(list,StTakeoutRequest.class);
        StTakeoutRequest orgStTakeoutRequest = null;
        //int index = 0;
        //String changeStatus = "";

        for(StTakeoutRequest stTakeoutRequest : stTakeoutRequestList){
            //changeStatus = list.get(index).getChangeStatus() == "" ? "반출완료" : list.get(index).getChangeStatus();
            orgStTakeoutRequest = stTakeoutRequestRepository.findOne(stTakeoutRequest.getId());
            stTakeoutRequest.setStatusUuid(CommonCodeUtils.getCodeDetailUuid("CD208","반출완료"));
            stTakeoutRequest.setRequestTypeUuid(orgStTakeoutRequest.getRequestTypeUuid());
            stTakeoutRequest.setRequestorUuid(orgStTakeoutRequest.getRequestorUuid());

            stTakeoutRequest.setTakeoutDate(orgStTakeoutRequest.getTakeoutDate());
            stTakeoutRequest.setReturnDueDate(orgStTakeoutRequest.getReturnDueDate());
            stTakeoutRequest.setTakeoutPropose(orgStTakeoutRequest.getTakeoutPropose());
            stTakeoutRequest.setReturnDate(orgStTakeoutRequest.getReturnDate());


            stTakeoutRequest.setInsertDate(orgStTakeoutRequest.getInsertDate());
            stTakeoutRequest.setInsertUuid(orgStTakeoutRequest.getInsertUuid());
            stTakeoutRequestRepository.save(stTakeoutRequest);
            //index++;
        }

        return ApiResponse.of(ApiStatus.SUCCESS, "SUCCESS");
    }

    public ApiResponse updateTakeoutRequest2(List<St00901VO> list) {
        List<StTakeoutRequest> stTakeoutRequestList = ModelMapperUtils.mapList(list,StTakeoutRequest.class);
        StTakeoutRequest orgStTakeoutRequest = null;

        for(StTakeoutRequest stTakeoutRequest : stTakeoutRequestList){
            orgStTakeoutRequest = stTakeoutRequestRepository.findOne(stTakeoutRequest.getId());
            stTakeoutRequest.setStatusUuid(CommonCodeUtils.getCodeDetailUuid("CD208","반출 불가"));
            stTakeoutRequest.setRequestTypeUuid(orgStTakeoutRequest.getRequestTypeUuid());
            stTakeoutRequest.setRequestorUuid(orgStTakeoutRequest.getRequestorUuid());

            stTakeoutRequest.setTakeoutDate(orgStTakeoutRequest.getTakeoutDate());
            stTakeoutRequest.setReturnDueDate(orgStTakeoutRequest.getReturnDueDate());
            stTakeoutRequest.setTakeoutPropose(orgStTakeoutRequest.getTakeoutPropose());
            stTakeoutRequest.setReturnDate(orgStTakeoutRequest.getReturnDate());


            stTakeoutRequest.setInsertDate(orgStTakeoutRequest.getInsertDate());
            stTakeoutRequest.setInsertUuid(orgStTakeoutRequest.getInsertUuid());
            stTakeoutRequestRepository.save(stTakeoutRequest);
            //index++;
        }

        return ApiResponse.of(ApiStatus.SUCCESS, "SUCCESS");
    }

    public ApiResponse saveStTakeoutRequest(St00901VO vo) {

        //vo.setRequestorUuid(SessionUtils.getCurrentLoginUserUuid());
        StTakeoutRequest stTakeoutRequest = new StTakeoutRequest();
        stTakeoutRequest.setTakeoutRequestUuid(vo.getTakeoutRequestUuid());
        StTakeoutRequest orgStTakeoutRequest = stTakeoutRequestRepository.findOne(stTakeoutRequest.getId());


        stTakeoutRequest.setInsertDate(orgStTakeoutRequest.getInsertDate());
        stTakeoutRequest.setInsertUuid(orgStTakeoutRequest.getInsertUuid());
        stTakeoutRequest.setReturnDate(vo.getStartDate());
        stTakeoutRequest.setStatusUuid(CommonCodeUtils.getCodeDetailUuid("CD208","반입서 작성"));
        stTakeoutRequest.setRequestName(orgStTakeoutRequest.getRequestName());
        stTakeoutRequest.setRequestTypeUuid(orgStTakeoutRequest.getRequestTypeUuid());
        stTakeoutRequest.setRequestorUuid(orgStTakeoutRequest.getRequestorUuid());
        stTakeoutRequest.setReturnDueDate(orgStTakeoutRequest.getReturnDueDate());
        stTakeoutRequest.setTakeoutDate(orgStTakeoutRequest.getTakeoutDate());
        stTakeoutRequest.setTakeoutPropose(orgStTakeoutRequest.getTakeoutPropose());
        stTakeoutRequest.setReturnDate(orgStTakeoutRequest.getReturnDate());
        //1이면 직원
        if(vo.getEmployeeYn().equals("Y")){
            stTakeoutRequest.setRequestorUuid(SessionUtils.getCurrentLoginUserUuid());


        }else{//2이면 외부
            stTakeoutRequest.setOutsourcingDepartment(vo.getOutsourcingDepartment());
            stTakeoutRequest.setOutsourcingPersonName(vo.getOutsourcingPersonName());
            stTakeoutRequest.setOutsourcingPosition(vo.getOutsourcingPosition());
            //stTakeoutRequest.setOutsourcingPhone(vo.getOutsourcingPhone());
            stTakeoutRequest.setOutsourcingPhone("-");
        }


        stTakeoutRequestRepository.save(stTakeoutRequest);

        return ApiResponse.of(ApiStatus.SUCCESS, "SUCCESS");
    }


}
