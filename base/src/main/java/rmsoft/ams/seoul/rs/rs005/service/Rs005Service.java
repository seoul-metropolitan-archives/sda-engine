package rmsoft.ams.seoul.rs.rs005.service;

import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.code.ApiStatus;
import io.onsemiro.core.domain.BaseService;
import io.onsemiro.core.parameter.RequestParams;
import io.onsemiro.utils.DateUtils;
import io.onsemiro.utils.ModelMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rmsoft.ams.seoul.common.domain.RsRecordScheduleResult;
import rmsoft.ams.seoul.common.repository.RsRecordScheduleResultRepository;
import rmsoft.ams.seoul.rs.rs005.dao.Rs005Mapper;
import rmsoft.ams.seoul.rs.rs005.vo.Rs00501VO;
import rmsoft.ams.seoul.utils.CommonCodeUtils;

import javax.inject.Inject;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class Rs005Service extends BaseService {
    @Inject
    private Rs005Mapper rs005Mapper;
    @Autowired
    private RsRecordScheduleResultRepository rsRecordScheduleResultRepository;

    public Page<Rs00501VO> getDisposalRecordList(Pageable pageable, RequestParams<Rs00501VO> requestParams) {
        Rs00501VO rs00501VO = new Rs00501VO();
        rs00501VO.setDisposalStatus(requestParams.getString("disposalStatus"));
        rs00501VO.setItemTitle(requestParams.getString("itemTitle"));
        rs00501VO.setDisposalTypeUuid(requestParams.getString("disposalTypeUuid"));

        rs00501VO.setDisposalFromDueDate(requestParams.getString("disposalFromDueDate"));
        rs00501VO.setDisposalToDueDate(requestParams.getString("disposalToDueDate"));
        rs00501VO.setDisposalFromConfirmDate(requestParams.getString("disposalFromConfirmDate"));
        rs00501VO.setDisposalToConfirmDate(requestParams.getString("disposalToConfirmDate"));
        rs00501VO.setDisposalFromCompleteDate(requestParams.getString("disposalFromCompleteDate"));
        rs00501VO.setDisposalToCompleteDate(requestParams.getString("disposalToCompleteDate"));

        return filter(rs005Mapper.getDisposalRecordList(rs00501VO), pageable, "", Rs00501VO.class);
    }
    public ApiResponse updateRecordScheduleResultList(List<Rs00501VO> list) {
        List<RsRecordScheduleResult> rsRecordScheduleResultList = ModelMapperUtils.mapList(list,RsRecordScheduleResult.class);
        RsRecordScheduleResult orgRsRecordScheduleResult = null;
        int index = 0;
        String changeStatus = "";
        for(RsRecordScheduleResult rsRecordScheduleResult : rsRecordScheduleResultList) {
            changeStatus = list.get(index).getChangeStatus() == "" ? "Draft" : list.get(index).getChangeStatus();
            orgRsRecordScheduleResult = rsRecordScheduleResultRepository.findOne(rsRecordScheduleResult.getId());
            rsRecordScheduleResult.setStatusUuid(orgRsRecordScheduleResult.getStatusUuid());
            rsRecordScheduleResult.setDisposalStatus(CommonCodeUtils.getCodeDetailUuid("CD137",changeStatus));
            if("Complete".equals(changeStatus)) {
                rsRecordScheduleResult.setDisposalCompleteDate(Timestamp.valueOf(DateUtils.convertToString(LocalDateTime.now(), DateUtils.DATE_TIME_PATTERN)));
            }else if("Confirm".equals(changeStatus)){
                rsRecordScheduleResult.setDisposalConfirmDate(DateUtils.convertToString(LocalDateTime.now(), DateUtils.DATE_PATTERN_DASH));
            }else{
                rsRecordScheduleResult.setDisposalConfirmDate("");
                rsRecordScheduleResult.setDisposalConfirmReason("");
            }
            rsRecordScheduleResult.setInsertDate(orgRsRecordScheduleResult.getInsertDate());
            rsRecordScheduleResult.setInsertUuid(orgRsRecordScheduleResult.getInsertUuid());
            rsRecordScheduleResultRepository.save(rsRecordScheduleResult);
            index++;
        }
        return ApiResponse.of(ApiStatus.SUCCESS, "SUCCESS");
    }
    @Transactional
    public ApiResponse saveRecordScheduleResultList(List<Rs00501VO> list) {
        List<RsRecordScheduleResult> rsRecordScheduleResultList = ModelMapperUtils.mapList(list,RsRecordScheduleResult.class);
        RsRecordScheduleResult orgRsRecordScheduleResult = null;
        for(RsRecordScheduleResult rsRecordScheduleResult : rsRecordScheduleResultList) {
            if(rsRecordScheduleResult.isDeleted()){

            }else{
                if(rsRecordScheduleResult.isModified()){
                    orgRsRecordScheduleResult = rsRecordScheduleResultRepository.findOne(rsRecordScheduleResult.getId());
                    rsRecordScheduleResult.setInsertDate(orgRsRecordScheduleResult.getInsertDate());
                    rsRecordScheduleResult.setInsertUuid(orgRsRecordScheduleResult.getInsertUuid());
                    rsRecordScheduleResultRepository.save(rsRecordScheduleResult);
                }
            }

        }
        return ApiResponse.of(ApiStatus.SUCCESS, "SUCCESS");
    }

}