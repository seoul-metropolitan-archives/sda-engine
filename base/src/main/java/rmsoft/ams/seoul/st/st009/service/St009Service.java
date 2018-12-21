package rmsoft.ams.seoul.st.st009.service;

import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.code.ApiStatus;
import io.onsemiro.core.domain.BaseService;
import io.onsemiro.core.parameter.RequestParams;
import io.onsemiro.utils.ModelMapperUtils;
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
        st00901VO.setRequestName(requestParams.getString("requestName"));


        return filter(st009Mapper.getTakeoutRequest(st00901VO), pageable, "", St00901VO.class);
    }

    public Page<St00902VO> getTakeoutRecordResult(Pageable pageable, RequestParams<St00902VO> requestParams) {
        St00902VO st00902VO = new St00902VO();
        st00902VO.setTakeoutRequestUuid(requestParams.getString("takeoutRequestUuid"));
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
}
