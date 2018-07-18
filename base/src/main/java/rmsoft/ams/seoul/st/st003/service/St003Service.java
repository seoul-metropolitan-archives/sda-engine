package rmsoft.ams.seoul.st.st003.service;

import io.onsemiro.core.code.ApiStatus;
import io.onsemiro.core.parameter.RequestParams;
import io.onsemiro.utils.DateUtils;
import io.onsemiro.utils.ModelMapperUtils;
import io.onsemiro.utils.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.onsemiro.core.domain.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import rmsoft.ams.seoul.common.domain.StArrangeRecordsResult;
import rmsoft.ams.seoul.common.repository.StArrangeRecordsResultRepository;
import rmsoft.ams.seoul.st.st003.dao.St003Mapper;
import io.onsemiro.core.api.response.ApiResponse;
import rmsoft.ams.seoul.st.st003.vo.St00301VO;
import rmsoft.ams.seoul.st.st003.vo.St00302VO;
import rmsoft.ams.seoul.st.st003.vo.St00303VO;
import rmsoft.ams.seoul.utils.CommonCodeUtils;

import javax.inject.Inject;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class St003Service extends BaseService {

    @Inject
    private St003Mapper st003Mapper;

    @Autowired
    private StArrangeRecordsResultRepository stArrangeRecordsResultRepository;

    public Page<St00301VO> getContainerAggregationList(Pageable pageable, RequestParams<St00301VO> requestParams) {

        St00301VO st00301VO = new St00301VO();
        st00301VO.setStatusUuid(requestParams.getString("statusUuid"));
        st00301VO.setAggregationCode(requestParams.getString("aggregationCode"));
        st00301VO.setTitle(requestParams.getString("title"));
        st00301VO.setTypeUuid(requestParams.getString("typeUuid"));
        st00301VO.setArrangedFromDate(requestParams.getString("arrangedFromDate01"));
        st00301VO.setArrangedToDate(requestParams.getString("arrangedToDate01"));
        st00301VO.setContainerUuid(requestParams.getString("containerUuid"));
        return filter(st003Mapper.getContainerAggregationList(st00301VO), pageable, "", St00301VO.class);
    }

    public Page<St00301VO> getContainerItemList(Pageable pageable, RequestParams<St00301VO> requestParams) {

        St00301VO st00301VO = new St00301VO();
        st00301VO.setStatusUuid(requestParams.getString("itemStatusUuid"));
        st00301VO.setTitle(requestParams.getString("itemTitle"));
        st00301VO.setTypeUuid(requestParams.getString("itemTypeUuid"));
        st00301VO.setArrangedFromDate(requestParams.getString("arrangedFromDate02"));
        st00301VO.setArrangedToDate(requestParams.getString("arrangedToDate02"));
        st00301VO.setContainerUuid(requestParams.getString("containerUuid"));
        st00301VO.setItemUuid(requestParams.getString("itemUuid"));
        st00301VO.setItemCode(requestParams.getString("itemCode"));
        return filter(st003Mapper.getContainerItemList(st00301VO), pageable, "", St00301VO.class);
    }

    public ApiResponse updateArrangeRecordStatus(List<St00301VO> list) {
        List<StArrangeRecordsResult> stArrangeRecordsResultList = ModelMapperUtils.mapList(list,StArrangeRecordsResult.class);
        StArrangeRecordsResult orgStArrangeRecordResult = null;
        int index = 0;
        String changeStatus = "";
        for(StArrangeRecordsResult stArrangeRecordsResult : stArrangeRecordsResultList) {
            changeStatus = list.get(index).getChangeStatus() == "" ? "Draft" : list.get(index).getChangeStatus();
            orgStArrangeRecordResult = stArrangeRecordsResultRepository.findOne(stArrangeRecordsResult.getId());
            stArrangeRecordsResult.setStatusUuid(CommonCodeUtils.getCodeDetailUuid("CD138",changeStatus));
            stArrangeRecordsResult.setInsertDate(orgStArrangeRecordResult.getInsertDate());
            stArrangeRecordsResult.setInsertUuid(orgStArrangeRecordResult.getInsertUuid());
            stArrangeRecordsResultRepository.save(stArrangeRecordsResult);
            index++;
        }
        return ApiResponse.of(ApiStatus.SUCCESS, "SUCCESS");
    }

    @Transactional
    public ApiResponse saveArrangeRecordList(List<St00301VO> list) {
        List<StArrangeRecordsResult> stArrangeRecordsResultList = ModelMapperUtils.mapList(list,StArrangeRecordsResult.class);
        StArrangeRecordsResult orgStArrangeRecordResult = null;
        for(StArrangeRecordsResult stArrangeRecordsResult : stArrangeRecordsResultList) {
            if(stArrangeRecordsResult.isDeleted()){

            }else{
                if(stArrangeRecordsResult.isModified()){
                    orgStArrangeRecordResult = stArrangeRecordsResultRepository.findOne(stArrangeRecordsResult.getId());
                    stArrangeRecordsResult.setInsertDate(orgStArrangeRecordResult.getInsertDate());
                    stArrangeRecordsResult.setInsertUuid(orgStArrangeRecordResult.getInsertUuid());
                }
                else{
                    stArrangeRecordsResult.setArrangeRecordsResultUuid(UUIDUtils.getUUID());
                    stArrangeRecordsResult.setStatusUuid(CommonCodeUtils.getCodeDetailUuid("CD138","Draft"));
                    stArrangeRecordsResult.setArrangedDate(Timestamp.valueOf(DateUtils.convertToString(LocalDateTime.now(), DateUtils.DATE_TIME_PATTERN)));
                    stArrangeRecordsResult.setDescription("");
                    stArrangeRecordsResult.setDescription("");
                }
                stArrangeRecordsResultRepository.save(stArrangeRecordsResult);
            }

        }
        return ApiResponse.of(ApiStatus.SUCCESS, "SUCCESS");
    }

    public Page<St00302VO> getAggregationHierarchyList(Pageable pageable) {
        return filter(st003Mapper.getAggregationHierarchyList(), pageable, "", St00302VO.class);
    }

    public Page<St00303VO> getSelectedItem(Pageable pageable, RequestParams<St00303VO> requestParams) {

        St00303VO st00303VO = new St00303VO();
        st00303VO.setAggregationUuid(requestParams.getString("aggregationUuid"));
        st00303VO.setContainerUuid(requestParams.getString("containerUuid"));

        return filter(st003Mapper.getSelectedItem(st00303VO), pageable, "", St00303VO.class);
    }
}