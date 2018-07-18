package rmsoft.ams.seoul.rs.rs004.service;

import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.code.ApiStatus;
import io.onsemiro.core.parameter.RequestParams;
import io.onsemiro.utils.DateUtils;
import io.onsemiro.utils.ModelMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.onsemiro.core.domain.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rmsoft.ams.seoul.common.domain.RsRecordScheduleResult;
import rmsoft.ams.seoul.common.repository.RsRecordScheduleResultRepository;
import rmsoft.ams.seoul.rs.rs004.dao.Rs004Mapper;
import rmsoft.ams.seoul.rs.rs004.vo.Rs004;
import rmsoft.ams.seoul.rs.rs005.vo.Rs00501VO;
import rmsoft.ams.seoul.utils.CommonCodeUtils;

import javax.inject.Inject;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class Rs004Service extends BaseService {
    @Inject
    private Rs004Mapper rs005Mapper;
    @Autowired
    private RsRecordScheduleResultRepository rsRecordScheduleResultRepository;

    public Page<Rs00501VO> getRecordScheduleResultList(Pageable pageable, RequestParams<Rs00501VO> requestParams) {
        Rs00501VO rs00501VO = new Rs00501VO();
        rs00501VO.setStatusUuid(requestParams.getString("statusUuid"));
        rs00501VO.setRetentionPeriodUuid(requestParams.getString("retentionPeriodUuid"));
        rs00501VO.setDisposalTypeUuid(requestParams.getString("disposalTypeUuid"));
        rs00501VO.setRsName(requestParams.getString("rsName"));
        rs00501VO.setRsCode(requestParams.getString("rsCode"));
        rs00501VO.setRecordScheduleUuid(requestParams.getString("recordScheduleUuid"));
        rs00501VO.setDisposalFromDueDate(requestParams.getString("disposalFromDueDate"));
        rs00501VO.setDisposalToDueDate(requestParams.getString("disposalToDueDate"));
        rs00501VO.setDisposalFreeze(requestParams.getString("disposalFreeze"));
        return filter(rs005Mapper.getRecordScheduleResultList(rs00501VO), pageable, "", Rs00501VO.class);
    }

    public ApiResponse updateRecordScheduleResultList(List<Rs00501VO> list) {
        List<RsRecordScheduleResult> rsRecordScheduleResultList = ModelMapperUtils.mapList(list,RsRecordScheduleResult.class);
        RsRecordScheduleResult orgRsRecordScheduleResult = null;
        int index = 0;
        String changeStatus = "";
        for(RsRecordScheduleResult rsRecordScheduleResult : rsRecordScheduleResultList) {
            changeStatus = list.get(index).getChangeStatus() == "" ? "Draft" : list.get(index).getChangeStatus();
            orgRsRecordScheduleResult = rsRecordScheduleResultRepository.findOne(rsRecordScheduleResult.getId());
            rsRecordScheduleResult.setStatusUuid(CommonCodeUtils.getCodeDetailUuid("CD137",changeStatus));
            rsRecordScheduleResult.setInsertDate(orgRsRecordScheduleResult.getInsertDate());
            rsRecordScheduleResult.setInsertUuid(orgRsRecordScheduleResult.getInsertUuid());
            rsRecordScheduleResultRepository.save(rsRecordScheduleResult);
            index++;
        }
        return ApiResponse.of(ApiStatus.SUCCESS, "SUCCESS");
    }

}