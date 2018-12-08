package rmsoft.ams.seoul.lc.lc001.service;

import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.code.ApiStatus;
import io.onsemiro.core.domain.BaseService;
import io.onsemiro.core.parameter.RequestParams;
import io.onsemiro.utils.DateUtils;
import io.onsemiro.utils.ModelMapperUtils;
import io.onsemiro.utils.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import rmsoft.ams.seoul.common.domain.LcLeadCase;
import rmsoft.ams.seoul.common.domain.LcLeadCaseSchedule;
import rmsoft.ams.seoul.common.repository.LcLeadCaseRepository;
import rmsoft.ams.seoul.common.repository.LcLeadCaseScheduleRepository;
import rmsoft.ams.seoul.lc.lc001.dao.Lc001Mapper;
import rmsoft.ams.seoul.lc.lc001.vo.Lc00101VO;
import rmsoft.ams.seoul.lc.lc001.vo.Lc00102VO;

import javax.inject.Inject;
import java.util.List;

@Service
public class Lc001Service extends BaseService{

    @Inject
    private Lc001Mapper lc001Mapper;

    @Autowired
    private LcLeadCaseRepository lcLeadCaseRepository;

    @Autowired
    private LcLeadCaseScheduleRepository lcLeadCaseScheduleRepository;

    public Page<Lc00101VO> getLeadCaseList(Pageable pageable, RequestParams<Lc00101VO> requestParams) {
        Lc00101VO lc00101VO = new Lc00101VO();
        lc00101VO.setLeadCaseNo(requestParams.getString("leadCaseNo"));
        lc00101VO.setOccupation(requestParams.getString("occupation"));
        lc00101VO.setRegion(requestParams.getString("region"));
        lc00101VO.setLeadCaseName(requestParams.getString("leadCaseName"));
        lc00101VO.setMajorClassificationUuid(requestParams.getString("majorClassificationUuid"));
        lc00101VO.setMiddleClassificationUuid(requestParams.getString("middleClassificationUuid"));

        return filter(lc001Mapper.getLeadCaseList(lc00101VO), pageable, "", Lc00101VO.class);
    }

    public Page<Lc00102VO> getLeadCaseScheduleList(Pageable pageable, RequestParams<Lc00102VO> requestParam) {
        Lc00102VO lc00102VO = new Lc00102VO();
        lc00102VO.setLeadCaseUuid(requestParam.getString("leadCaseUuid"));
        return filter(lc001Mapper.getLeadCaseScheduleList(lc00102VO), pageable, "", Lc00101VO.class);
    }

    public ApiResponse updateLeadCaseList(List<Lc00101VO> list) {
        List<LcLeadCase> lcLeadCaseList = ModelMapperUtils.mapList(list,LcLeadCase.class);
        LcLeadCase orgLcLeadCase = null;
        String cnt = null;
        for (LcLeadCase lcLeadCase : lcLeadCaseList) {
            if (lcLeadCase.isDeleted()) {
                lcLeadCaseRepository.delete(lcLeadCase);
            }else{
                if(lcLeadCase.isCreated()){ //ClassificationSchemeUuid가 없을때
                    lcLeadCase.setLeadCaseUuid(UUIDUtils.getUUID());
                    String leadCaseNo = jdbcTemplate.queryForObject("SELECT 'LT'||'-'||TO_CHAR(sysdate,'yyyymmdd')||'-'||NVL(LPAD(TO_NUMBER(SUBSTR(MAX(LEAD_CASE_NO),13)) + 1,2,'0'),'01') as leadCaseNo FROM LC_LEAD_CASE WHERE LEAD_CASE_NO LIKE '%' || TO_CHAR(sysdate,'yyyymmdd') || '%'",String.class);
                    lcLeadCase.setLeadCaseNo(leadCaseNo);
                }
                if(lcLeadCase.isModified()) {
                    orgLcLeadCase = lcLeadCaseRepository.findOne(lcLeadCase.getId());
                    lcLeadCase.setInsertDate(orgLcLeadCase.getInsertDate());
                    lcLeadCase.setInsertUuid(orgLcLeadCase.getInsertUuid());
                }
                lcLeadCaseRepository.save(lcLeadCase);
            }
        }
        return ApiResponse.of(ApiStatus.SUCCESS, "SUCCESS");
    }

    public ApiResponse updateLeadCaseScheduleList(List<Lc00102VO> list) {
        List<LcLeadCaseSchedule> lcLeadCaseScheduleList = ModelMapperUtils.mapList(list,LcLeadCaseSchedule.class);
        LcLeadCaseSchedule orgLcLeadCaseSchedule = null;
        String cnt = null;
        for (LcLeadCaseSchedule lcLeadCaseSchedule : lcLeadCaseScheduleList) {
            if (lcLeadCaseSchedule.isDeleted()) {
                lcLeadCaseScheduleRepository.delete(lcLeadCaseSchedule);
            }else{
                if(lcLeadCaseSchedule.isCreated()){ //ClassificationSchemeUuid가 없을때
                    lcLeadCaseSchedule.setLeadCaseScheduleUuid(UUIDUtils.getUUID());
                    String scheduleNo = jdbcTemplate.queryForObject("select LC_LEAD_CASE_SCHEDULE_SQ.NEXTVAL from dual", String.class);
                    lcLeadCaseSchedule.setScheduleNo(scheduleNo);
//                    lcLeadCaseSchedule.setCollectionDate(DateUtils.convertToDate() lcLeadCaseSchedule.getCollectionDate());
                }
                if(lcLeadCaseSchedule.isModified()) {
                    orgLcLeadCaseSchedule = lcLeadCaseScheduleRepository.findOne(lcLeadCaseSchedule.getId());
                    lcLeadCaseSchedule.setInsertDate(orgLcLeadCaseSchedule.getInsertDate());
                    lcLeadCaseSchedule.setInsertUuid(orgLcLeadCaseSchedule.getInsertUuid());
                }
                lcLeadCaseScheduleRepository.save(lcLeadCaseSchedule);
            }
        }
        return ApiResponse.of(ApiStatus.SUCCESS, "SUCCESS");
    }
}