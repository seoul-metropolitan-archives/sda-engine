package rmsoft.ams.seoul.rs.rs004.service;

import com.querydsl.core.types.Predicate;
import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.code.ApiStatus;
import io.onsemiro.core.domain.BaseService;
import io.onsemiro.core.parameter.RequestParams;
import io.onsemiro.utils.ModelMapperUtils;
import io.onsemiro.utils.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rmsoft.ams.seoul.common.domain.*;
import rmsoft.ams.seoul.common.repository.RcAggregationConRepository;
import rmsoft.ams.seoul.common.repository.RcItemRepository;
import rmsoft.ams.seoul.common.repository.RsRecordScheduleRepository;
import rmsoft.ams.seoul.common.repository.RsRecordScheduleResultRepository;
import rmsoft.ams.seoul.rs.rs003.vo.Rs00301VO;
import rmsoft.ams.seoul.rs.rs004.dao.Rs004Mapper;
import rmsoft.ams.seoul.rs.rs004.vo.Rs00401VO;
import rmsoft.ams.seoul.rs.rs004.vo.Rs00402VO;
import rmsoft.ams.seoul.utils.CommonCodeUtils;

import javax.inject.Inject;
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
    public ApiResponse saveRecordScheduleResultList(List<Rs00402VO> list) {
        List<RcAggregationCon> rcAggregationConList = ModelMapperUtils.mapList(list,RcAggregationCon.class);
        RcAggregationCon orgRcAggregationCon = null;
        QRcItem qRcItem = null;
        Predicate predicate = null;
        RsRecordSchedule rsRecordSchedule = null;
        RsRecordSchedule orgRsRecordSchedule = null;
        RsRecordScheduleResult rsRecordScheduleResult = null;
        int index =0;
        Boolean delMode = false;
        for(RcAggregationCon rcAggregationCon : rcAggregationConList){
            delMode = list.get(index).isDeleted();
            orgRcAggregationCon = rcAggregationConRepository.findOne(rcAggregationCon.getId());
            //RC_AGGREGATION_CON 에 RS_RECORD_SCHEDULE_UUID 정보 업데이트
            orgRcAggregationCon = orgRcAggregationCon == null ? rcAggregationCon : orgRcAggregationCon;

            if(delMode){
                orgRcAggregationCon.setRecordScheduleUuid(null);
            }else{
                orgRcAggregationCon.setRecordScheduleUuid(rcAggregationCon.getRecordScheduleUuid());
            }

            /* 내년에 해야됨
            rcAggregationConRepository.save(orgRcAggregationCon);

            //AGGREGATION 을 참조하는 ITEM 목록
            qRcItem = QRcItem.rcItem;
            predicate = qRcItem.aggregationUuid.eq(orgRcAggregationCon.getAggregationUuid());
            Iterable<RcItem> updateItemList = rcItemRepository.findAll(predicate);


            rsRecordSchedule = new RsRecordSchedule();
            rsRecordSchedule.setRecordScheduleUuid(rcAggregationCon.getRecordScheduleUuid());

            orgRsRecordSchedule = rsRecordScheduleRepository.findOne(rsRecordSchedule.getId());
            //ITEM 정보 + DISPOSAL_TYPE_UUID

            for(RcItem rcItem : updateItemList){
                rsRecordScheduleResult = new RsRecordScheduleResult();
                rsRecordScheduleResult.setRecordScheduleResultUuid(UUIDUtils.getUUID());
                rsRecordScheduleResult.setRecordScheduleUuid(rcAggregationCon.getRecordScheduleUuid());
                rsRecordScheduleResult.setItemUuid(rcItem.getItemUuid());
                rsRecordScheduleResult.setDisposalTypeUuid(orgRsRecordSchedule.getDisposalTypeUuid());
                rsRecordScheduleResult.setStatusUuid(CommonCodeUtils.getCodeDetailUuid("CD137","Draft"));
                rsRecordScheduleResultRepository.save(rsRecordScheduleResult);
            }
            */
        }

        return ApiResponse.of(ApiStatus.SUCCESS, "SUCCESS");
    }

}