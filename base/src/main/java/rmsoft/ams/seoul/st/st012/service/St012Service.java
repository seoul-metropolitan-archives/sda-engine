package rmsoft.ams.seoul.st.st012.service;

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
import rmsoft.ams.seoul.common.domain.StInoutExcept;
import rmsoft.ams.seoul.common.domain.StWithoutNoticeInoutRecord;
import rmsoft.ams.seoul.common.repository.StExceptRecordResultRepository;
import rmsoft.ams.seoul.common.repository.StInoutExceptRepository;

import rmsoft.ams.seoul.common.repository.StWithoutNoticeInoutRecordRepository;
import rmsoft.ams.seoul.st.st012.dao.St012Mapper;
import rmsoft.ams.seoul.st.st012.vo.St01201VO;
import rmsoft.ams.seoul.st.st012.vo.St01202VO;

import javax.inject.Inject;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class St012Service extends BaseService {
    @Inject
    private St012Mapper st012Mapper;

    @Autowired
    private StWithoutNoticeInoutRecordRepository stWithoutNoticeInoutRecordRepository;


    public Page<St01201VO> getStWithoutNoticeInoutHistResult(Pageable pageable, RequestParams<St01201VO> requestParams) {
        St01201VO st01201VO = new St01201VO();

        st01201VO.setCode(requestParams.getString("code"));
        st01201VO.setTitle(requestParams.getString("title"));
        st01201VO.setRepositoryUuid(requestParams.getString("repositoryUuid"));
        st01201VO.setShelfUuid(requestParams.getString("shelfUuid"));
        st01201VO.setInoutDateTimeFrom(requestParams.getString("inoutDateTimeFrom"));
        st01201VO.setInoutDateTimeTo(requestParams.getString("inoutDateTimeTo"));
        // St01201VO.setStatusUuid (requestParams.getString("statusUuid"));

        return filter(st012Mapper.getStWithoutNoticeInoutHistResult(st01201VO), pageable, "", St01201VO.class);
    }

    public Page<St01201VO> getStInoutExcept(Pageable pageable, RequestParams<St01201VO> requestParams) {
        St01201VO St01201VO = new St01201VO();
        //St01201VO.setRequestName(requestParams.getString("requestName"));
        //검색조건 추가시

        return filter(st012Mapper.getStInoutExcept(St01201VO), pageable, "", St01201VO.class);
    }

    public Page<St01201VO> getStExceptRecordResult(Pageable pageable, RequestParams<St01201VO> requestParams) {
        St01201VO St01201VO = new St01201VO();
        //St01201VO.setRequestName(requestParams.getString("requestName"));
        //검색조건 추가시

        return filter(st012Mapper.getStExceptRecordResult(St01201VO), pageable, "", St01201VO.class);
    }

    @Transactional
    public ApiResponse saveStWithoutNoticeInoutRecordList(List<St01202VO> list) {
        List<StWithoutNoticeInoutRecord> aStWithoutNoticeInoutRecord = ModelMapperUtils.mapList(list, StWithoutNoticeInoutRecord.class);
        StWithoutNoticeInoutRecord orgStWithoutNoticeInoutRecord = null;

        for (StWithoutNoticeInoutRecord stWithoutNoticeInoutRecord : aStWithoutNoticeInoutRecord) {
            if (stWithoutNoticeInoutRecord.isDeleted()) {
                stWithoutNoticeInoutRecordRepository.delete(stWithoutNoticeInoutRecord);

                //여기서 st_except_record_result도 삭제 해야된다.
            } else {

                orgStWithoutNoticeInoutRecord = stWithoutNoticeInoutRecordRepository.findOne(stWithoutNoticeInoutRecord.getId());
                stWithoutNoticeInoutRecord.setDisposerUuid(stWithoutNoticeInoutRecord.getDisposerUuid());
                stWithoutNoticeInoutRecord.setInsertDate(orgStWithoutNoticeInoutRecord.getInsertDate());
                stWithoutNoticeInoutRecord.setInsertUuid(orgStWithoutNoticeInoutRecord.getInsertUuid());
                stWithoutNoticeInoutRecord.setUpdateUuid(SessionUtils.getCurrentLoginUserUuid());
                stWithoutNoticeInoutRecord.setUpdateDate(Timestamp.valueOf(DateUtils.convertToString(LocalDateTime.now(), DateUtils.DATE_TIME_PATTERN)));

                stWithoutNoticeInoutRecordRepository.save(stWithoutNoticeInoutRecord);
            }
        }

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
        return filter(st012Mapper.getStExceptRecordResult(st01102VO), pageable, "", St01102VO.class);

    }*/


}
