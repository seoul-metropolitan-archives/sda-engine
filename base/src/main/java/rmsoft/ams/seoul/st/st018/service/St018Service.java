package rmsoft.ams.seoul.st.st018.service;

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
import rmsoft.ams.seoul.common.repository.StExceptRecordResultRepository;
import rmsoft.ams.seoul.common.repository.StInoutExceptRepository;

import rmsoft.ams.seoul.common.repository.StRfidTagRepository;
import rmsoft.ams.seoul.st.st018.dao.St018Mapper;
import rmsoft.ams.seoul.st.st018.vo.St01801VO;
import rmsoft.ams.seoul.st.st018.vo.St01802VO;
import rmsoft.ams.seoul.utils.CommonCodeUtils;

import javax.inject.Inject;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class St018Service extends BaseService {
    @Inject
    private St018Mapper st018Mapper;
    @Autowired
    private StRfidTagRepository stRfidTagRepository;

    public Page<St01801VO> getRcAggregation(Pageable pageable, RequestParams<St01801VO> requestParams) {
        St01801VO st01801VO = new St01801VO();
        //검색조건 추가시
        st01801VO.setRepositoryUuid(requestParams.getString("repositoryUuid"));
        st01801VO.setShelfUuid(requestParams.getString("shelfUuid"));
        st01801VO.setLocationUuid(requestParams.getString("locationUuid"));
        st01801VO.setCode(requestParams.getString("code"));
        st01801VO.setTitle(requestParams.getString("title"));

        st01801VO.setPublishDateFrom(requestParams.getString("publishDateFrom"));
        st01801VO.setPublishDateTo(requestParams.getString("publishDateTo"));
        st01801VO.setPublishStatusUuid(requestParams.getString("publishStatusUuid"));

        return filter(st018Mapper.getRcAggregation(st01801VO), pageable, "", St01801VO.class);
    }
    public Page<St01802VO> getStRfidTag(Pageable pageable, RequestParams<St01802VO> requestParams) {
        St01802VO st01802VO = new St01802VO();
        st01802VO.setAggregationUuid(requestParams.getString("aggregationUuid"));
        //검색조건 추가시

        return filter(st018Mapper.getStRfidTag(st01802VO), pageable, "", St01802VO.class);
    }

    @Transactional
    public ApiResponse saveTagPublish(St01801VO requestParams) {
        StRfidTag stRfidTag = ModelMapperUtils.map(requestParams, StRfidTag.class);

        stRfidTag.setRfidTagUuid(UUIDUtils.getUUID());
        stRfidTag.setAggregationUuid( requestParams.getAggregationUuid() );
        int seq = jdbcTemplate.queryForObject("SELECT ST_READER_MACHINE_SEQ.NEXTVAL FROM dual", int.class);
        stRfidTag.setSeq(seq);
        // CD221
        //        01	SDA
        //        02	정수점검기
        //        03	PDA
        // TODO : 01, 02, 03 중 뭘넣어야 되는지 모르겠다. 일단 '정수점검기 ( 02 )' 를 넣자.
        stRfidTag.setPublishSourceTypeUuid( CommonCodeUtils.getCodeDetailUuidByCode("CD221", "02") );

        // CD220
        //        01	미발행
        //        02	발행
        //        03	재발행
        St01802VO st01802VO = new St01802VO();
        st01802VO.setAggregationUuid( requestParams.getAggregationUuid() );
        // RC_AGGREGATION 이 StRfidTag 목록을 가지는지 체크하기 위함.
        // aggregation 으로 join 함

        List<St01802VO> aSt01802VO = st018Mapper.getStRfidTag( st01802VO );
        if( aSt01802VO.size() == 0){
            // 한번도 발행한 적이 없음
            stRfidTag.setPublishStatusUuid( CommonCodeUtils.getCodeDetailUuidByCode("CD220", "02") );
        }else{
            // 그전에 발행한 적이 있음
            stRfidTag.setPublishStatusUuid( CommonCodeUtils.getCodeDetailUuidByCode("CD220", "03") );
        }

        stRfidTag.setPublishDate(Timestamp.valueOf(DateUtils.convertToString(LocalDateTime.now(), DateUtils.DATE_TIME_PATTERN)));
        // TODO : Tag 발행규칙이 있다고 문서에서 말하는데 뭔지 모르겠음.
        stRfidTag.setTag( UUID.randomUUID().toString() );
        stRfidTagRepository.save(stRfidTag);

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
        return filter(st018Mapper.getStExceptRecordResult(st01102VO), pageable, "", St01102VO.class);

    }*/


}
