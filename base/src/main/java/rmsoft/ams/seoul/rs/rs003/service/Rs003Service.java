package rmsoft.ams.seoul.rs.rs003.service;

import com.querydsl.core.types.Predicate;
import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.code.ApiStatus;
import io.onsemiro.core.domain.BaseService;
import io.onsemiro.core.parameter.RequestParams;
import io.onsemiro.utils.ModelMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rmsoft.ams.seoul.common.domain.*;
import rmsoft.ams.seoul.common.repository.RcItemConRepository;
import rmsoft.ams.seoul.common.repository.RsRecordScheduleRepository;
import rmsoft.ams.seoul.common.repository.RsRecordScheduleResultRepository;
import rmsoft.ams.seoul.common.repository.RsTriggerRepository;
import rmsoft.ams.seoul.rs.rs003.dao.Rs003Mapper;
import rmsoft.ams.seoul.rs.rs003.vo.Rs00301VO;
import rmsoft.ams.seoul.utils.CommonCodeUtils;

import javax.inject.Inject;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class Rs003Service extends BaseService {

    @Inject
    private Rs003Mapper rs003Mapper;

    @Autowired
    private RsRecordScheduleRepository rsRecordScheduleRepository;
    @Autowired
    private RsRecordScheduleResultRepository rsRecordScheduleResultRepository;
    @Autowired
    private RcItemConRepository rcItemConRepository;
    @Autowired
    private RsTriggerRepository rsTriggerRepository;

    private Calendar cal = Calendar.getInstance();
    private SimpleDateFormat format;
    private Date date;

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

    @Transactional
    public ApiResponse updateStatus(List<Rs00301VO> list) {
        List<RsRecordSchedule> rsRecordSchedulesList = ModelMapperUtils.mapList(list,RsRecordSchedule.class);
        RsRecordSchedule orgRsRecordSchedule = null;
        int index = 0;
        String changeStatus = "";
        for(RsRecordSchedule rsRecordSchedule : rsRecordSchedulesList) {
            changeStatus = list.get(index).getChangeStatus() == "" ? "Draft" : list.get(index).getChangeStatus();
            orgRsRecordSchedule = rsRecordScheduleRepository.findOne(rsRecordSchedule.getId());
            if( ("" == orgRsRecordSchedule.getRecalculationYn() || null == orgRsRecordSchedule.getRecalculationYn())
                    && changeStatus == "Confirm"){
                rsRecordSchedule.setRecalculationYn("N");
            }else{
                rsRecordSchedule.setRecalculationYn(orgRsRecordSchedule.getRecalculationYn());
            }
            rsRecordSchedule.setStatusUuid(CommonCodeUtils.getCodeDetailUuid("CD134",changeStatus));
            rsRecordSchedule.setInsertDate(orgRsRecordSchedule.getInsertDate());
            rsRecordSchedule.setInsertUuid(orgRsRecordSchedule.getInsertUuid());
            rsRecordScheduleRepository.save(rsRecordSchedule);
            index++;
        }
        return ApiResponse.of(ApiStatus.SUCCESS, "SUCCESS");
    }
    @Transactional
    public ApiResponse updateRsRecordScheduleList(List<Rs00301VO> list) {
        List<RsRecordSchedule> rsRecordScheduleList = ModelMapperUtils.mapList(list,RsRecordSchedule.class);
        RsRecordSchedule orgRsRecordSchedule = null;
        String cnt = null;
        for (RsRecordSchedule rsRecordSchedule : rsRecordScheduleList) {

            if (rsRecordSchedule.isDeleted()) {
                rsRecordScheduleRepository.delete(rsRecordSchedule);
            }else{
                if(rsRecordSchedule.isCreated()){ //ClassificationSchemeUuid가 없을때
                    rsRecordSchedule.setStatusUuid(CommonCodeUtils.getCodeDetailUuid("CD134","Draft"));
                    cnt = jdbcTemplate.queryForObject("SELECT LPAD(TO_NUMBER(SUBSTR(MAX(RS_CODE),4)) + 1,4,'0') FROM RS_RECORD_SCHEDULE", String.class);
                    if(cnt == null){
                        cnt = "0001";
                    }
                    cnt = "RS-" + cnt;
                    rsRecordSchedule.setRsCode(cnt);
                    rsRecordScheduleRepository.save(rsRecordSchedule);
                }
                if(rsRecordSchedule.isModified()) {
                    orgRsRecordSchedule = rsRecordScheduleRepository.findOne(rsRecordSchedule.getId());
                    rsRecordSchedule.setInsertDate(orgRsRecordSchedule.getInsertDate());
                    rsRecordSchedule.setInsertUuid(orgRsRecordSchedule.getInsertUuid());

                    if("N".equals(orgRsRecordSchedule.getRecalculationYn())){
                        if(!orgRsRecordSchedule.getGeneralRecordScheduleUuid().equals(rsRecordSchedule.getGeneralRecordScheduleUuid())
                                || orgRsRecordSchedule.getTriggerUuid().equals(rsRecordSchedule.getTriggerUuid())){
                            rsRecordSchedule.setRecalculationYn("Y");
                        }
                    }
                    if(!"Y".equals(orgRsRecordSchedule.getRecalculationYn())){
                        rsRecordScheduleRepository.save(rsRecordSchedule);
                    }
                }
            }
        }

        return ApiResponse.of(ApiStatus.SUCCESS, "SUCCESS");
    }
    @Transactional
    public ApiResponse dueToUpdateSchedule(List<Rs00301VO> list) throws Exception {
        List<RsRecordSchedule> rsRecordScheduleList = ModelMapperUtils.mapList(list,RsRecordSchedule.class);
        RsRecordSchedule orgRsRecordSchedule = null;
        RsRecordScheduleResult orgRsRecordScheduleResult = null;
        QRsRecordScheduleResult qRsRecordScheduleResult = null;
        QRcItemCon qRcItemCon = null;
        Predicate predicate = null;
        Iterable<RsRecordScheduleResult> uRecordScheduleResultList = null;
        RcItemCon rcItemCon = null;
        QRsTrigger qRsTrigger = null;
        RsTrigger rsTrigger = null;
        String initDate = "";
        String period = "";

        //RsRecordSchedlueResult에서 해당 목록 찾기
        for (RsRecordSchedule rsRecordSchedule : rsRecordScheduleList){
            //없으면 return
            if(null != rsRecordSchedule.getTriggerUuid() && "" != rsRecordSchedule.getTriggerUuid()){
                qRsTrigger = QRsTrigger.rsTrigger;
                predicate = qRsTrigger.triggerUuid.eq(rsRecordSchedule.getTriggerUuid());
                rsTrigger = rsTriggerRepository.findOne(predicate);
                initDate = rsTrigger.getTriggerDate().replace("-","").substring(0,8);
            }

            qRsRecordScheduleResult = QRsRecordScheduleResult.rsRecordScheduleResult;
            predicate = qRsRecordScheduleResult.recordScheduleUuid.eq(rsRecordSchedule.getRecordScheduleUuid());
            uRecordScheduleResultList = rsRecordScheduleResultRepository.findAll(predicate);

            for (RsRecordScheduleResult rsRecordScheduleResult : uRecordScheduleResultList) {
                if( null == rsRecordScheduleResult.getDisposalCompleteDate()){
                    qRcItemCon = QRcItemCon.rcItemCon;
                    predicate = qRcItemCon.itemUuid.eq(rsRecordScheduleResult.getItemUuid());
                    rcItemCon = rcItemConRepository.findOne(predicate);
                    period = CommonCodeUtils.getCodeByUuid("CD133",rsRecordSchedule.getRetentionPeriodUuid());
                    if("" == initDate){
                        if(null != rcItemCon.getCreationEndDate() && "" != rcItemCon.getCreationEndDate())
                            initDate = rcItemCon.getCreationEndDate();
                        else if(null != rcItemCon.getCreationStartDate() && "" != rcItemCon.getCreationStartDate())
                            initDate = rcItemCon.getCreationStartDate();
                    }

                    orgRsRecordScheduleResult = rsRecordScheduleResultRepository.findOne(rsRecordScheduleResult.getId());

                    initDate = calinitailDate(initDate);
                    rsRecordScheduleResult.setInitialDate(initDate);
                    rsRecordScheduleResult.setDisposalTypeUuid(rsRecordSchedule.getDisposalTypeUuid());
                    rsRecordScheduleResult.setDisposalDueDate(calDueToDate(period,initDate));
                    rsRecordScheduleResult.setInsertDate(orgRsRecordScheduleResult.getInsertDate());
                    rsRecordScheduleResult.setInsertUuid(orgRsRecordScheduleResult.getInsertUuid());
                    rsRecordScheduleResultRepository.save(rsRecordScheduleResult);
                }
            }

            orgRsRecordSchedule = rsRecordScheduleRepository.findOne(rsRecordSchedule.getId());
            rsRecordSchedule.setRecalculationYn("N");
            rsRecordSchedule.setInsertDate(orgRsRecordSchedule.getInsertDate());
            rsRecordSchedule.setInsertUuid(orgRsRecordSchedule.getInsertUuid());
            rsRecordScheduleRepository.save(rsRecordSchedule);

        }
        //있으면 날짜 재계산하기 예외: 스케쥴 완료 날짜가 있으면 패스

        //recalculationYn = n 으로 세팅

        return ApiResponse.of(ApiStatus.SUCCESS, "SUCCESS");
    }

    private String calinitailDate(String iDate) throws Exception {
        if(iDate.length() > 6 || "" == iDate) return iDate;

        switch (iDate.length()){
            case 4:
                format = new SimpleDateFormat("yyyy");
                date = format.parse(iDate);
                cal.setTime(date);
                cal.add(Calendar.YEAR,Integer.parseInt("1"));
                break;
            case 6:
                format = new SimpleDateFormat("yyyyMM");
                date = format.parse(iDate);
                cal.setTime(date);
                cal.add(Calendar.MONTH,Integer.parseInt("1"));
                break;
        }

        format = new SimpleDateFormat("yyyyMMdd");
        return format.format(cal.getTime());
    }

    private String calDueToDate(String term, String iDate) throws Exception {
        if("SEMI".equals(term) || "PERMANENCE".equals(term)) return null;
        if(iDate.length() != 8 || "" == iDate) return iDate;

        cal = Calendar.getInstance();
        format = new SimpleDateFormat("yyyyMMdd");
        date = format.parse(iDate);
        cal.setTime(date);
        cal.add(Calendar.YEAR,Integer.parseInt(term));

        return format.format(cal.getTime());
    }
}