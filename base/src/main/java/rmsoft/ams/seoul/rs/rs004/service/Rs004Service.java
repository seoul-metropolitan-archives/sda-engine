package rmsoft.ams.seoul.rs.rs004.service;

import com.querydsl.core.types.Predicate;
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
import org.springframework.transaction.annotation.Transactional;
import rmsoft.ams.seoul.common.domain.*;
import rmsoft.ams.seoul.common.repository.*;
import rmsoft.ams.seoul.rs.rs003.vo.Rs00301VO;
import rmsoft.ams.seoul.rs.rs004.dao.Rs004Mapper;
import rmsoft.ams.seoul.rs.rs004.vo.Rs00401VO;
import rmsoft.ams.seoul.rs.rs004.vo.Rs00402VO;
import rmsoft.ams.seoul.utils.CommonCodeUtils;

import javax.inject.Inject;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class Rs004Service extends BaseService {
    @Inject
    private Rs004Mapper rs004Mapper;
    @Autowired
    private RsRecordScheduleResultRepository rsRecordScheduleResultRepository;
    @Autowired
    private RcAggregationConRepository rcAggregationConRepository;
    @Autowired
    private RcItemRepository rcItemRepository;
    @Autowired
    private RsRecordScheduleRepository rsRecordScheduleRepository;
    @Autowired
    private RsTriggerRepository rsTriggerRepository;
    @Autowired
    private RcItemConRepository rcItemConRepository;

    private Calendar cal = Calendar.getInstance();
    private SimpleDateFormat format;
    private Date date;

    public Page<Rs00401VO> getRecordScheduleResultList(Pageable pageable, RequestParams<Rs00401VO> requestParams) {
        Rs00401VO rs00401VO = new Rs00401VO();
        rs00401VO.setStatusUuid(requestParams.getString("statusUuid"));
        rs00401VO.setRetentionPeriodUuid(requestParams.getString("retentionPeriodUuid"));
        rs00401VO.setDisposalTypeUuid(requestParams.getString("disposalTypeUuid"));
        rs00401VO.setRsName(requestParams.getString("rsName"));
        rs00401VO.setRsCode(requestParams.getString("rsCode"));
        rs00401VO.setRecordScheduleUuid(requestParams.getString("recordScheduleUuid"));
        rs00401VO.setDisposalFromDueDate(requestParams.getString("disposalFromDueDate"));
        rs00401VO.setDisposalToDueDate(requestParams.getString("disposalToDueDate"));
        rs00401VO.setDisposalFreeze(requestParams.getString("disposalFreeze"));
        return filter(rs004Mapper.getRecordScheduleResultList(rs00401VO), pageable, "", Rs00401VO.class);
    }

    public Page<Rs00301VO> getRecordScheduleList(Pageable pageable, RequestParams<Rs00301VO> requestParams) {
        return filter(rs004Mapper.getRecordScheduleList(), pageable, "", Rs00301VO.class);
    }

    public Page<Rs00402VO> getRecordScheduleAggregationList(Pageable pageable, RequestParams<Rs00402VO> requestParams) {
        Rs00402VO rs00402VO = new Rs00402VO();
        rs00402VO.setRecordScheduleUuid(requestParams.getString("recordScheduleUuid"));
        return filter(rs004Mapper.getRecordScheduleAggregationList(rs00402VO), pageable, "", Rs00402VO.class);
    }

    @Transactional
    public ApiResponse updateRecordScheduleResultList(List<Rs00401VO> list) {
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

    @Transactional
    public ApiResponse saveRecordScheduleResultList(List<Rs00402VO> list) throws Exception {
        List<RcAggregationCon> rcAggregationConList = ModelMapperUtils.mapList(list,RcAggregationCon.class);
        RcAggregationCon orgRcAggregationCon = null;
        RsRecordSchedule rsRecordSchedule = null;
        Predicate predicate = null;
        RsRecordScheduleResult rsRecordScheduleResult = null;
        RcItemCon rcItemCon = null;
        QRcItem qRcItem = null;
        QRcItemCon qRcItemCon = null;
        QRsRecordScheduleResult qRsRecordScheduleResult = null;
        String period = "";
        Calendar cal = null;

        int index =0;
        String initDate = null;
        Boolean delMode = false;
        Boolean trgInitDay = false;
        Iterable<RcItem> updateItemList = null;

        if(rcAggregationConList.size() > 0){
            rsRecordSchedule = new RsRecordSchedule();
            rsRecordSchedule.setRecordScheduleUuid(rcAggregationConList.get(0).getRecordScheduleUuid());
            rsRecordSchedule = rsRecordScheduleRepository.findOne(rsRecordSchedule.getId());
            period = CommonCodeUtils.getCodeByUuid("CD133",rsRecordSchedule.getRetentionPeriodUuid());

            if(null != rsRecordSchedule.getTriggerUuid() && "" != rsRecordSchedule.getTriggerUuid()){
                QRsTrigger qRsTrigger = QRsTrigger.rsTrigger;
                predicate = qRsTrigger.triggerUuid.eq(rsRecordSchedule.getTriggerUuid());
                RsTrigger rsTrigger = rsTriggerRepository.findOne(predicate);
                initDate = rsTrigger.getTriggerDate().replace("-","").substring(0,8);
                trgInitDay = true;
            }
        }

        for(RcAggregationCon rcAggregationCon : rcAggregationConList){
            delMode = list.get(index).isDeleted();
            orgRcAggregationCon = rcAggregationConRepository.findOne(rcAggregationCon.getId());
            //RC_AGGREGATION_CON 에 RS_RECORD_SCHEDULE_UUID 정보 업데이트
            orgRcAggregationCon = orgRcAggregationCon == null ? rcAggregationCon : orgRcAggregationCon;

            if(!delMode){
                orgRcAggregationCon.setRecordScheduleUuid(rcAggregationCon.getRecordScheduleUuid());
                rcAggregationConRepository.save(orgRcAggregationCon);
                //AGGREGATION 을 참조하는 ITEM 목록
                qRcItem = QRcItem.rcItem;
                predicate = qRcItem.aggregationUuid.eq(orgRcAggregationCon.getAggregationUuid());
                updateItemList = rcItemRepository.findAll(predicate);

                //rs에 trigger 가 등록되어있다면 기준일을 trigger Date 로 하고
                //trigger가 등록되어 있지 않았다면 기준을일 Item Creation End Date, Item Creation Start Date 순으로 한다.
                for(RcItem rcItem : updateItemList){
                    rsRecordScheduleResult = new RsRecordScheduleResult();
                    rsRecordScheduleResult.setRecordScheduleResultUuid(UUIDUtils.getUUID());
                    rsRecordScheduleResult.setRecordScheduleUuid(rcAggregationCon.getRecordScheduleUuid());
                    rsRecordScheduleResult.setItemUuid(rcItem.getItemUuid());
                    rsRecordScheduleResult.setDisposalTypeUuid(rsRecordSchedule.getDisposalTypeUuid());
                    rsRecordScheduleResult.setStatusUuid(CommonCodeUtils.getCodeDetailUuid("CD134","Confirm"));
                    rsRecordScheduleResult.setDisposalStatus(CommonCodeUtils.getCodeDetailUuid("CD137","Draft"));

                    qRcItemCon = QRcItemCon.rcItemCon;
                    predicate = qRcItemCon.itemUuid.eq(rcItem.getItemUuid());
                    rcItemCon = rcItemConRepository .findOne(predicate);

                    if(!trgInitDay){
                        if(null != rcItemCon.getCreationEndDate() && "" != rcItemCon.getCreationEndDate())
                            initDate = rcItemCon.getCreationEndDate();
                        else if(null != rcItemCon.getCreationStartDate() && "" != rcItemCon.getCreationStartDate())
                            initDate = rcItemCon.getCreationStartDate();
                    }

                    initDate = calinitailDate(initDate);

                    rsRecordScheduleResult.setInitialDate(initDate);
                    rsRecordScheduleResult.setDisposalDueDate(calDueToDate(period,initDate));
                    rsRecordScheduleResultRepository.save(rsRecordScheduleResult);
                }
            }else{
                orgRcAggregationCon.setRecordScheduleUuid(null);
                rcAggregationConRepository.save(orgRcAggregationCon);

                qRcItem = QRcItem.rcItem;
                predicate = qRcItem.aggregationUuid.eq(orgRcAggregationCon.getAggregationUuid());
                updateItemList = rcItemRepository.findAll(predicate);

                for(RcItem rcItem : updateItemList){
                    qRsRecordScheduleResult = QRsRecordScheduleResult.rsRecordScheduleResult;
                    predicate = qRsRecordScheduleResult.itemUuid.eq(rcItem.getItemUuid());
                    rsRecordScheduleResultRepository.delete(rsRecordScheduleResultRepository.findOne(predicate));
                }
            }
        }

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