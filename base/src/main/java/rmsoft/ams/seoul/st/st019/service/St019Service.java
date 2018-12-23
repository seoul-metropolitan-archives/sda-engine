package rmsoft.ams.seoul.st.st019.service;

import com.querydsl.core.types.Predicate;
import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.code.ApiStatus;
import io.onsemiro.core.domain.BaseService;
import io.onsemiro.core.parameter.RequestParams;
import io.onsemiro.utils.DateUtils;
import io.onsemiro.utils.ModelMapperUtils;
import io.onsemiro.utils.SessionUtils;
import io.onsemiro.utils.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rmsoft.ams.seoul.common.domain.StInoutExcept;
import rmsoft.ams.seoul.common.domain.StRfidTag;
import rmsoft.ams.seoul.common.domain.StRfidTagPublishRequest;
import rmsoft.ams.seoul.common.repository.StExceptRecordResultRepository;
import rmsoft.ams.seoul.common.repository.StInoutExceptRepository;

import rmsoft.ams.seoul.common.repository.StRfidTagPublishRequestRepository;
import rmsoft.ams.seoul.common.repository.StRfidTagRepository;
import rmsoft.ams.seoul.st.st018.dao.St018Mapper;
import rmsoft.ams.seoul.st.st018.service.St018Service;
import rmsoft.ams.seoul.st.st018.vo.St01801VO;
import rmsoft.ams.seoul.st.st018.vo.St01802VO;
import rmsoft.ams.seoul.st.st019.dao.St019Mapper;
import rmsoft.ams.seoul.st.st019.vo.St01901VO;

import rmsoft.ams.seoul.utils.CommonCodeUtils;

import javax.inject.Inject;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class St019Service extends BaseService {
    
    @Inject
    private St019Mapper st019Mapper;
    @Inject
    private St018Service st018Service;

    @Autowired
    private StRfidTagPublishRequestRepository stRfidTagPublishRequestRepository;

    public Page<St01901VO> getStRfidTagPublishRequest(Pageable pageable, RequestParams<St01901VO> requestParams) {
        St01901VO St01901VO = new St01901VO();
        //St01901VO.setRequestName(requestParams.getString("requestName"));
        //검색조건 추가시

        return filter(st019Mapper.getStRfidTagPublishRequest(St01901VO), pageable, "", St01901VO.class);
    }


    @Transactional
    public ApiResponse saveTagRepublish(St01901VO requestParams) {
        St01801VO st01801VO = ModelMapperUtils.map(requestParams, St01801VO.class);
        // 공통부분은 여기서 처리
        st018Service.saveTagPublish(st01801VO);
        // 나머지 ST_RFID_TAG_PUBLISH_REQUEST 관련 처리를 하자.
        St01901VO st01901VO = ModelMapperUtils.map(requestParams, St01901VO.class);
        StRfidTagPublishRequest stRfidTagPublishRequest = ModelMapperUtils.map(requestParams, StRfidTagPublishRequest.class);
        StRfidTagPublishRequest oldStRfidTagRepublish = stRfidTagPublishRequestRepository.findOne(stRfidTagPublishRequest.getId());

        oldStRfidTagRepublish.setRepublishYn("Y");
        oldStRfidTagRepublish.setRepublishDate(Timestamp.valueOf(DateUtils.convertToString(LocalDateTime.now(), DateUtils.DATE_TIME_PATTERN)));
        oldStRfidTagRepublish.setUpdateDate(new Timestamp(System.currentTimeMillis()));
        oldStRfidTagRepublish.setUpdateUuid(SessionUtils.getCurrentLoginUserUuid());
        stRfidTagPublishRequestRepository.save(oldStRfidTagRepublish);


        return ApiResponse.of(ApiStatus.SUCCESS, "SUCCESS");
    }


    /*public void saveExceptRecordResult(List<St01102VO> list) {
        List<StExceptRecordResult> stExceptRecordResults = ModelMapperUtils.mapList(list, StExceptRecordResult.class);

        for(StExceptRecordResult stExceptRecordResult : stExceptRecordResults){
            stExceptRecordResult.setExceptRecordResultUuid(UUIDUtils.getUUID());
            stExceptRecordResultRepository.save(stExceptRecordResult);
        }


    }

    public Page<St01102VO> getStExceptRecordResult(Pageable pageable, RequestParams<St01102VO> requestParams) {
        St01102VO st01102VO = new St01102VO();
        st01102VO.setInoutExceptUuid(requestParams.getString("inoutExceptUuid"));
        return filter(st019Mapper.getStExceptRecordResult(st01102VO), pageable, "", St01102VO.class);

    }*/


}
