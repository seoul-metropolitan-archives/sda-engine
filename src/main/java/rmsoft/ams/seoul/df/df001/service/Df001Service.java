package rmsoft.ams.seoul.df.df001.service;

import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.code.ApiStatus;
import io.onsemiro.core.domain.BaseService;
import io.onsemiro.core.parameter.RequestParams;
import io.onsemiro.utils.DateUtils;
import io.onsemiro.utils.ModelMapperUtils;
import io.onsemiro.utils.SessionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rmsoft.ams.seoul.common.domain.DfEvent;
import rmsoft.ams.seoul.common.repository.DfEventRepository;
import rmsoft.ams.seoul.df.df001.dao.Df001Mapper;
import rmsoft.ams.seoul.df.df001.vo.Df00101VO;
import rmsoft.ams.seoul.df.df001.vo.Df00102VO;
import rmsoft.ams.seoul.utils.CommonCodeUtils;
import rmsoft.ams.seoul.utils.CommonMessageUtils;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class Df001Service extends BaseService {

    @Autowired
    private Df001Mapper mapper;

    @Autowired
    private DfEventRepository repository;

    //@Autowired
    //private AdGlossaryRepository addGlossaryRepository;

    /**
     * Search entity type list.
     *
     * @param param the param
     * @return the list
     */
    public List<Df00101VO> searchList(RequestParams<Df00101VO> param) {
        Df00101VO df00101VO = new Df00101VO();

        df00101VO.setStatusUuid(param.getString("statusUuid"));
        df00101VO.setEventCode(param.getString("eventCode"));
        df00101VO.setEventName(param.getString("eventName"));
        df00101VO.setEventTypeUuid(param.getString("eventTypeUuid"));
        df00101VO.setReviewDate(param.getString("reviewDate"));
        df00101VO.setReviewDateTo(param.getString("reviewDateTo"));
        df00101VO.setEndYN(param.getString("endYN"));

        return mapper.searchList(df00101VO);
    }

    /**
     * Gets classification scheme detail.
     *
     * @param requestParams the request params
     * @return the classification scheme detail
     */
    //Disposal Freeze Event 상세 정보
    public Df00102VO getDetail(RequestParams<Df00102VO> requestParams) {
        Df00102VO df00102VO = new Df00102VO();
        df00102VO.setDisposalFreezeEventUuid(requestParams.getString("disposalFreezeEventUuid"));
        return mapper.getDetail(df00102VO);
    }

    /**
     * Save entity type api response.
     *
     * @param  itemList vo list
     * @return the api response
     */
    @Transactional
    public ApiResponse saveItems(List<Df00101VO> itemList) {

        List<DfEvent> dfEventList = ModelMapperUtils.mapList(itemList, DfEvent.class);

        DfEvent orgDfEvent = null;
        String eventCode = "";

        for (DfEvent dfEvent : dfEventList) {
            if(dfEvent.isCreated()){ //disposalFreezeEventUuid 없을때
                eventCode = jdbcTemplate.queryForObject("select FC_DF_EVENT_CODE('') from dual", String.class);
                dfEvent.setEventCode(eventCode);
                dfEvent.setStatusUuid(CommonCodeUtils.getCodeDetailUuid("CD115","Draft"));
            }

            if (dfEvent.isCreated() || dfEvent.isModified()) {
                if(dfEvent.isModified()) {
                    orgDfEvent = repository.findOne(dfEvent.getId());
                    dfEvent.setInsertDate(orgDfEvent.getInsertDate());
                    dfEvent.setInsertUuid(orgDfEvent.getInsertUuid());

                    if(dfEvent.getEndYn().equals("Y")){
                        dfEvent.setTerminatorUuid(SessionUtils.getCurrentLoginUserUuid());
                        dfEvent.setEndDate(Timestamp.valueOf(DateUtils.convertToString(LocalDateTime.now(), DateUtils.DATE_TIME_PATTERN)));
                    }else{
                        dfEvent.setTerminatorUuid("");
                        dfEvent.setEndDate(null);
                    }
                }

                repository.save(dfEvent);
            } else if (dfEvent.isDeleted()) {
                if(CommonCodeUtils.getDetailCode("CD115", dfEvent.getStatusUuid()).equals("CONFIRM")){
                    return ApiResponse.error(ApiStatus.SYSTEM_ERROR, CommonMessageUtils.getMessage("DF001_01"));
                }else if (mapper.checkDegree(dfEvent.getDisposalFreezeEventUuid()) > 0) {
                    return ApiResponse.error(ApiStatus.SYSTEM_ERROR, CommonMessageUtils.getMessage("DF001_02"));
                } else {
                    repository.delete(dfEvent);
                }
            }

        }
        return ApiResponse.of(ApiStatus.SUCCESS, "SUCCESS");
    }

    /**
     * Update status api response.
     *
     * @param list the list
     * @return the api response
     */
    public ApiResponse updateStatus(List<Df00101VO> list){
        List<DfEvent> dfEventList = ModelMapperUtils.mapList(list,DfEvent.class);
        DfEvent orgDfEvent = null;
        int index = 0;
        String changeStatus = "";
        for (DfEvent dfEvent : dfEventList) {
            changeStatus = list.get(index).getChangeStatus() == "" ?  "Draft" : list.get(index).getChangeStatus();
            orgDfEvent = repository.findOne(dfEvent.getId());
            dfEvent.setStatusUuid(CommonCodeUtils.getCodeDetailUuid("CD115",changeStatus));
            dfEvent.setInsertDate(orgDfEvent.getInsertDate());
            dfEvent.setInsertUuid(orgDfEvent.getInsertUuid());
            repository.save(dfEvent);
            index++;
        }
        return ApiResponse.of(ApiStatus.SUCCESS, "SUCCESS");
    }

    /**
     * Update classification scheme con detail.
     *
     * @param requestParams the request params
     */
    public void saveDetailItem(RequestParams<Df00101VO> requestParams) {
        DfEvent dfEvent = new DfEvent();

        if(StringUtils.isEmpty(requestParams.getString("disposalFreezeEventUuid"))){
            return;
        }

        dfEvent.setDisposalFreezeEventUuid(requestParams.getString("disposalFreezeEventUuid"));

        DfEvent orgDfEvent = null;

        orgDfEvent = repository.findOne(dfEvent.getId());

        if(orgDfEvent != null){
            dfEvent = orgDfEvent;
            dfEvent.setReason(requestParams.getString("reason"));
            dfEvent.setInsertDate(orgDfEvent.getInsertDate());
            dfEvent.setInsertUuid(orgDfEvent.getInsertUuid());
        }
        repository.save(dfEvent);
    }

}