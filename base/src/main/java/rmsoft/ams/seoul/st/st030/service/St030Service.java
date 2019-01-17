package rmsoft.ams.seoul.st.st030.service;

import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.code.ApiStatus;
import io.onsemiro.core.domain.BaseService;
import io.onsemiro.core.parameter.RequestParams;
import io.onsemiro.utils.DateUtils;
import io.onsemiro.utils.ModelMapperUtils;
import io.onsemiro.utils.SessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rmsoft.ams.seoul.common.domain.StArrangeRecordsResult;
import rmsoft.ams.seoul.common.domain.StMissArrangeRecordReq;

import rmsoft.ams.seoul.common.repository.AdMenuRepository;
import rmsoft.ams.seoul.common.repository.StArrangeRecordsResultRepository;
import rmsoft.ams.seoul.common.repository.StMissArrangeRecordReqRepository;
import rmsoft.ams.seoul.st.st003.vo.St003VO;
import rmsoft.ams.seoul.st.st030.dao.St030Mapper;
import rmsoft.ams.seoul.st.st030.vo.St03001VO;


import javax.inject.Inject;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class St030Service extends BaseService {
    @Inject
    private St030Mapper st030Mapper;
    @Autowired
    private StMissArrangeRecordReqRepository stMissArrangeRecordReqRepository;
    @Autowired
    private StArrangeRecordsResultRepository stArrangeRecordsResultRepository;

    public Page<St03001VO> getStMissArrangeRequest(Pageable pageable, RequestParams<St03001VO> requestParams) {
        St03001VO St03001VO = new St03001VO();
        //St03001VO.setRequestName(requestParams.getString("requestName"));
        //검색조건 추가시

        St03001VO.setRepositoryUuid(requestParams.getString("repositoryUuid"));
        St03001VO.setShelfUuid(requestParams.getString("shelfUuid"));
        St03001VO.setLocationUuid(requestParams.getString("locationUuid"));
        St03001VO.setCode(requestParams.getString("code"));
        St03001VO.setTitle(requestParams.getString("title"));

        St03001VO.setContainerUuid(requestParams.getString("containerUuid"));
        St03001VO.setRepublishYn(requestParams.getString("republishYn"));
        St03001VO.setRequestDateFrom(requestParams.getString("requestDateFrom"));
        St03001VO.setRequestDateTo(requestParams.getString("requestDateTo"));
        St03001VO.setSourceTypeUuid(requestParams.getString("sourceTypeUuid"));




        return filter(st030Mapper.getStMissArrangeRequest(St03001VO), pageable, "", St03001VO.class);
    }

    @Transactional
    public ApiResponse saveStMissArrangeRecordReq(List<St03001VO> aParameter) {

        for( int i =0  ; i< aParameter.size(); i++) {
            St03001VO vo = aParameter.get(i);
            StMissArrangeRecordReq stMissArrangeRecordReq = ModelMapperUtils.map(vo, StMissArrangeRecordReq.class);
            StMissArrangeRecordReq orgStMissArrangeRecordReq = stMissArrangeRecordReqRepository.findOne(stMissArrangeRecordReq.getId());
            // - 실제 보존상자 기준으로 기록물을 재배치 합니다.

            // - [1]ST_MISS_ARRANGE_RECORD_REQ 에 Arrange yn, Arrange Date를 UPDATE합니다.
            orgStMissArrangeRecordReq.setRepublishYn("Y");
            orgStMissArrangeRecordReq.setRepublishDate(DateUtils.convertToString(LocalDateTime.now(), DateUtils.DATE_TIME_PATTERN));
            orgStMissArrangeRecordReq.setUpdateUuid(SessionUtils.getCurrentLoginUserUuid());
            orgStMissArrangeRecordReq.setUpdateDate(Timestamp.valueOf(DateUtils.convertToString(LocalDateTime.now(), DateUtils.DATE_TIME_PATTERN)));
            orgStMissArrangeRecordReq.setCurrentContainerUuid(orgStMissArrangeRecordReq.getContainerUuid()); // 올바른 콘테이너로 재배치.
            stMissArrangeRecordReqRepository.save(orgStMissArrangeRecordReq);
            // - [0]st_arrange_records_result 에 Container정보를 Update합니다.
            St003VO stArrangeRecordsResultVO = st030Mapper.getStArrangeRecordsResult(vo);
            StArrangeRecordsResult stArrangeRecordsResult = ModelMapperUtils.map(stArrangeRecordsResultVO, StArrangeRecordsResult.class);
            StArrangeRecordsResult orgStArrangeRecordResult = stArrangeRecordsResultRepository.findOne(stArrangeRecordsResult.getId());
            orgStArrangeRecordResult.setContainerUuid(orgStMissArrangeRecordReq.getContainerUuid()); // 콘테이너 재배치

            orgStArrangeRecordResult.setUpdateUuid(SessionUtils.getCurrentLoginUserUuid());
            orgStArrangeRecordResult.setUpdateDate(Timestamp.valueOf(DateUtils.convertToString(LocalDateTime.now(), DateUtils.DATE_TIME_PATTERN)));
            stArrangeRecordsResultRepository.save(orgStArrangeRecordResult);
        }


        return ApiResponse.of(ApiStatus.SUCCESS, "SUCCESS");
    }
    /*

    public Page<St01102VO> getStExceptRecordResult(Pageable pageable, RequestParams<St01102VO> requestParams) {
        St01102VO st01102VO = new St01102VO();
        st01102VO.setInoutExceptUuid(requestParams.getString("inoutExceptUuid"));
        return filter(st030Mapper.getStExceptRecordResult(st01102VO), pageable, "", St01102VO.class);

    }*/


}
