package rmsoft.ams.seoul.rs.rs002.service;

import io.onsemiro.core.api.ApiException;
import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.code.ApiStatus;
import io.onsemiro.core.domain.BaseService;
import io.onsemiro.core.parameter.RequestParams;
import io.onsemiro.utils.ModelMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rmsoft.ams.seoul.common.domain.RsTrigger;
import rmsoft.ams.seoul.common.repository.RsTriggerRepository;
import rmsoft.ams.seoul.rs.rs002.dao.Rs002Mapper;
import rmsoft.ams.seoul.rs.rs002.vo.Rs00201VO;
import rmsoft.ams.seoul.utils.CommonCodeUtils;

import javax.inject.Inject;
import java.util.List;

@Service
public class Rs002Service extends BaseService {

    @Inject
    private Rs002Mapper rs002Mapper;

    @Autowired
    private RsTriggerRepository rsTriggerRepository;

    public Page<Rs00201VO> getRsTriggerList(Pageable pageable, RequestParams<Rs00201VO> requestParams) {
        Rs00201VO rs00201VO = new Rs00201VO();
        rs00201VO.setTriggerCode(requestParams.getString("triggerCode"));
        rs00201VO.setTriggerName(requestParams.getString("triggerName"));
        rs00201VO.setStatusUuid (requestParams.getString("statusUuid"));
        rs00201VO.setUseYn(requestParams.getString("useYn"));

        return filter(rs002Mapper.getRsTriggerList(rs00201VO), pageable, "", Rs00201VO.class);
    }

    public ApiResponse updateStatus(List<Rs00201VO> list) {
        List<RsTrigger> rsTriggerList = ModelMapperUtils.mapList(list,RsTrigger.class);
        RsTrigger orgRsTrigger = null;
        int index = 0;
        String changeStatus = "";
        for(RsTrigger rsTrigger : rsTriggerList) {
            changeStatus = list.get(index).getChangeStatus() == "" ? "Draft" : list.get(index).getChangeStatus();
            orgRsTrigger = rsTriggerRepository.findOne(rsTrigger.getId());
            rsTrigger.setStatusUuid(CommonCodeUtils.getCodeDetailUuid("CD134",changeStatus));
            rsTrigger.setInsertDate(orgRsTrigger.getInsertDate());
            rsTrigger.setInsertUuid(orgRsTrigger.getInsertUuid());
            rsTriggerRepository.save(rsTrigger);
            index++;
        }
        return ApiResponse.of(ApiStatus.SUCCESS, "SUCCESS");
    }

    public ApiResponse updateRsTriggerList(List<Rs00201VO> list) {
        List<RsTrigger> rsTriggerList = ModelMapperUtils.mapList(list,RsTrigger.class);
        RsTrigger orgRsTrigger = null;
        String cnt = null;
        for(RsTrigger rsTrigger : rsTriggerList) {
            if (rsTrigger.isDeleted()) {
                if (getRelatedData(rsTrigger.getTriggerUuid()) == 0) {
                    rsTriggerRepository.delete(rsTrigger);
                } else {
                    throw new ApiException("CL", "002_01");
                }
            }else{
                if(rsTrigger.isCreated()){
                    rsTrigger.setStatusUuid(CommonCodeUtils.getCodeDetailUuid("CD134","Draft"));
                    cnt = jdbcTemplate.queryForObject("SELECT LPAD(TO_NUMBER(SUBSTR(MAX(TRIGGER_CODE),6)) + 1,4,'0') FROM RS_TRIGGER", String.class);
                    if(cnt == null){
                        cnt = "0001";
                    }
                    cnt = "RS-T-" + cnt;
                    rsTrigger.setTriggerCode(cnt);
                }
                if(rsTrigger.isModified()) {
                    orgRsTrigger = rsTriggerRepository.findOne(rsTrigger.getId());
                    rsTrigger.setInsertDate(orgRsTrigger.getInsertDate());
                    rsTrigger.setInsertUuid(orgRsTrigger.getInsertUuid());
                }
                rsTriggerRepository.save(rsTrigger);
            }
        }

        return ApiResponse.of(ApiStatus.SUCCESS, "SUCCESS");
    }

    public int getRelatedData(String triggerUuid) {
        return rs002Mapper.getRelatedData(triggerUuid);
    }
}