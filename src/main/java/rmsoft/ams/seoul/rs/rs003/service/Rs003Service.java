package rmsoft.ams.seoul.rs.rs003.service;

import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.code.ApiStatus;
import io.onsemiro.core.parameter.RequestParams;
import io.onsemiro.utils.ModelMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.onsemiro.core.domain.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rmsoft.ams.seoul.common.domain.RsGeneralRecordSchedule;
import rmsoft.ams.seoul.common.domain.RsRecordSchedule;
import rmsoft.ams.seoul.common.repository.RsGeneralRecordScheduleRepository;
import rmsoft.ams.seoul.common.repository.RsRecordScheduleRepository;
import rmsoft.ams.seoul.rs.rs003.dao.Rs003Mapper;
import rmsoft.ams.seoul.rs.rs003.vo.Rs003;
import rmsoft.ams.seoul.rs.rs003.vo.Rs00301VO;
import rmsoft.ams.seoul.utils.CommonCodeUtils;

import javax.inject.Inject;
import java.util.List;

@Service
public class Rs003Service extends BaseService {

    @Inject
    private Rs003Mapper rs003Mapper;

    @Autowired
    private RsRecordScheduleRepository rsRecordScheduleRepository;

    public Page<Rs00301VO> getRsRecordScheduleList(Pageable pageable, RequestParams<Rs00301VO> requestParams) {
        Rs00301VO rs00301VO = new Rs00301VO();
        rs00301VO.setDisposalTypeUuid(requestParams.getString("disposalTypeUuid"));
        rs00301VO.setRetentionPeriodUuid(requestParams.getString("retentionPeriodUuid"));
        rs00301VO.setRsCode(requestParams.getString("rsCode"));
        rs00301VO.setRsName(requestParams.getString("rsName"));
        rs00301VO.setStatusUuid (requestParams.getString("statusUuid"));
        rs00301VO.setTriggerYn(requestParams.getString("triggerYn"));
        rs00301VO.setUseYn(requestParams.getString("useYn"));
        rs00301VO.setGrsCode(requestParams.getString("grsCode"));

        return filter(rs003Mapper.getRsRecordScheduleList(rs00301VO), pageable, "", Rs00301VO.class);
    }

    public ApiResponse updateStatus(List<Rs00301VO> list) {
        List<RsRecordSchedule> rsRecordSchedulesList = ModelMapperUtils.mapList(list,RsRecordSchedule.class);
        RsRecordSchedule orgRsRecordSchedule = null;
        int index = 0;
        String changeStatus = "";
        for(RsRecordSchedule rsRecordSchedule : rsRecordSchedulesList) {
            changeStatus = list.get(index).getChangeStatus() == "" ? "Draft" : list.get(index).getChangeStatus();
            orgRsRecordSchedule = rsRecordScheduleRepository.findOne(rsRecordSchedule.getId());
            rsRecordSchedule.setStatusUuid(CommonCodeUtils.getCodeDetailUuid("CD134",changeStatus));
            rsRecordSchedule.setInsertDate(orgRsRecordSchedule.getInsertDate());
            rsRecordSchedule.setInsertUuid(orgRsRecordSchedule.getInsertUuid());
            rsRecordScheduleRepository.save(rsRecordSchedulesList);
            index++;
        }
        return ApiResponse.of(ApiStatus.SUCCESS, "SUCCESS");
    }
}