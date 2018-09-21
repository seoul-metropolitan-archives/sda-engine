package rmsoft.ams.seoul.rs.rs001.service;

import io.onsemiro.core.api.ApiException;
import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.code.ApiStatus;
import io.onsemiro.core.domain.BaseService;
import io.onsemiro.core.parameter.RequestParams;
import io.onsemiro.utils.ModelMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import rmsoft.ams.seoul.common.domain.RsGeneralRecordSchedule;
import rmsoft.ams.seoul.common.repository.RsGeneralRecordScheduleRepository;
import rmsoft.ams.seoul.rs.rs001.dao.Rs001Mapper;
import rmsoft.ams.seoul.rs.rs001.vo.Rs00101VO;
import rmsoft.ams.seoul.utils.CommonCodeUtils;

import javax.inject.Inject;
import java.util.List;

@Service
public class Rs001Service extends BaseService{
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Inject
    private Rs001Mapper rs001Mapper;

    @Autowired
    private RsGeneralRecordScheduleRepository rsGeneralRecordScheduleRepository;

    public Page<Rs00101VO> getRsGeneralRecordScheduleList(Pageable pageable, RequestParams<Rs00101VO> requestParams) {
        Rs00101VO rs00101VO = new Rs00101VO();
        rs00101VO.setDisposalTypeUuid(requestParams.getString("disposalTypeUuid"));
        rs00101VO.setGrsCode(requestParams.getString("grsCode"));
        rs00101VO.setGrsName(requestParams.getString("grsName"));
        rs00101VO.setRetentionPeriodUuid(requestParams.getString("retentionPeriodUuid"));
        rs00101VO.setStatusUuid (requestParams.getString("statusUuid"));
        rs00101VO.setTriggerYn(requestParams.getString("triggerYn"));
        rs00101VO.setUseYn(requestParams.getString("useYn"));

        return filter(rs001Mapper.getRsGeneralRecordScheduleList(rs00101VO), pageable, "", Rs00101VO.class);
    }

    public ApiResponse updateStatus(List<Rs00101VO> list) {
        List<RsGeneralRecordSchedule> rsGeneralRecordSchedulesList = ModelMapperUtils.mapList(list,RsGeneralRecordSchedule.class);
        RsGeneralRecordSchedule orgRsGeneralRecordSchedule = null;
        int index = 0;
        String changeStatus = "";
        for(RsGeneralRecordSchedule rsGeneralRecordSchedule : rsGeneralRecordSchedulesList) {
            changeStatus = list.get(index).getChangeStatus() == "" ? "Draft" : list.get(index).getChangeStatus();
            orgRsGeneralRecordSchedule = rsGeneralRecordScheduleRepository.findOne(rsGeneralRecordSchedule.getId());
            rsGeneralRecordSchedule.setStatusUuid(CommonCodeUtils.getCodeDetailUuid("CD134",changeStatus));
            if(changeStatus.equals("Confirm")){
                rsGeneralRecordSchedule.setUseYn("Y");
            }
            rsGeneralRecordSchedule.setInsertDate(orgRsGeneralRecordSchedule.getInsertDate());
            rsGeneralRecordSchedule.setInsertUuid(orgRsGeneralRecordSchedule.getInsertUuid());
            rsGeneralRecordScheduleRepository.save(rsGeneralRecordSchedule);
            index++;
        }
        return ApiResponse.of(ApiStatus.SUCCESS, "SUCCESS");
    }

    public ApiResponse updateRsGeneralRecordScheduleList(List<Rs00101VO> list) {
        List<RsGeneralRecordSchedule> rsGeneralRecordScheduleList = ModelMapperUtils.mapList(list,RsGeneralRecordSchedule.class);
        RsGeneralRecordSchedule orgRsGeneralRecordSchedule = null;
        String cnt = null;
        for (RsGeneralRecordSchedule rsGeneralRecordSchedule : rsGeneralRecordScheduleList) {

            if (rsGeneralRecordSchedule.isDeleted()) {
                if (getRelatedData(rsGeneralRecordSchedule.getGeneralRecordScheduleUuid()) == 0) {
                    rsGeneralRecordScheduleRepository.delete(rsGeneralRecordSchedule);
                } else {
                    throw new ApiException("CL", "002_01");
                }
            }else{
                if(rsGeneralRecordSchedule.isCreated()){ //ClassificationSchemeUuid가 없을때
                    rsGeneralRecordSchedule.setStatusUuid(CommonCodeUtils.getCodeDetailUuid("CD134","Draft"));
                    cnt = jdbcTemplate.queryForObject("SELECT LPAD(TO_NUMBER(SUBSTR(MAX(GRS_CODE),5)) + 1,4,'0') FROM RS_GENERAL_RECORD_SCHEDULE", String.class);
                    if(cnt == null){
                        cnt = "0001";
                    }
                    cnt = "GRS-" + cnt;
                    rsGeneralRecordSchedule.setGrsCode(cnt);
                }
                if(rsGeneralRecordSchedule.isModified()) {
                    orgRsGeneralRecordSchedule = rsGeneralRecordScheduleRepository.findOne(rsGeneralRecordSchedule.getId());
                    rsGeneralRecordSchedule.setInsertDate(orgRsGeneralRecordSchedule.getInsertDate());
                    rsGeneralRecordSchedule.setInsertUuid(orgRsGeneralRecordSchedule.getInsertUuid());
                }
                rsGeneralRecordScheduleRepository.save(rsGeneralRecordSchedule);
            }
        }

        return ApiResponse.of(ApiStatus.SUCCESS, "SUCCESS");
    }

    public int getRelatedData(String generalRecordScheduleUuid) {
        return rs001Mapper.getRelatedData(generalRecordScheduleUuid);
    }
}