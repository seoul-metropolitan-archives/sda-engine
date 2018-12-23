package rmsoft.ams.seoul.st.st020.service;

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
import rmsoft.ams.seoul.st.st020.dao.St020Mapper;
import rmsoft.ams.seoul.st.st020.vo.St02001VO;
import rmsoft.ams.seoul.st.st020.vo.St02002VO;
import rmsoft.ams.seoul.utils.CommonCodeUtils;

import javax.inject.Inject;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class St020Service extends BaseService {
    @Inject
    private St020Mapper st020Mapper;
    @Autowired
    private StRfidTagRepository stRfidTagRepository;

    public Page<St02001VO> getRcAggregation(Pageable pageable, RequestParams<St02001VO> requestParams) {
        St02001VO St02001VO = new St02001VO();
        //St02001VO.setRequestName(requestParams.getString("requestName"));
        //검색조건 추가시

        return filter(st020Mapper.getRcAggregation(St02001VO), pageable, "", St02001VO.class);
    }
    public Page<St02002VO> getStRfidTag(Pageable pageable, RequestParams<St02002VO> requestParams) {
        St02002VO st02002VO = new St02002VO();
        //St02002VO.setRequestName(requestParams.getString("requestName"));
        //검색조건 추가시

        return filter(st020Mapper.getStRfidTag(st02002VO), pageable, "", St02002VO.class);
    }

    @Transactional
    public ApiResponse saveTagPublish(St02001VO requestParams) {
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
        St02002VO st02002VO = new St02002VO();
        // RC_AGGREGATION 이 StRfidTag 목록을 가지는지 체크하기 위함.
        // aggregation 으로 join 함

        List<St02002VO> aSt02002VO = st020Mapper.getStRfidTag( st02002VO );
        if( aSt02002VO.size() == 0){
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
        return filter(st020Mapper.getStExceptRecordResult(st01102VO), pageable, "", St01102VO.class);

    }*/


}
