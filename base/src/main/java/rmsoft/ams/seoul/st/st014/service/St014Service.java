package rmsoft.ams.seoul.st.st014.service;

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
import rmsoft.ams.seoul.common.repository.StExceptRecordResultRepository;
import rmsoft.ams.seoul.common.repository.StInoutExceptRepository;

import rmsoft.ams.seoul.st.st014.dao.St014Mapper;
import rmsoft.ams.seoul.st.st014.vo.St01401VO;
import rmsoft.ams.seoul.st.st014.vo.St01402VO;

import javax.inject.Inject;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class St014Service extends BaseService {
    @Inject
    private St014Mapper st014Mapper;

    public Page<St01401VO> getStWithoutNoticeInoutRecord(Pageable pageable, RequestParams<St01401VO> requestParams) {
        St01401VO St01401VO = new St01401VO();
        //St01401VO.setRequestName(requestParams.getString("requestName"));
        //검색조건 추가시

        return filter(st014Mapper.getStWithoutNoticeInoutRecord(St01401VO), pageable, "", St01401VO.class);
    }
    public Page<St01402VO> getStWithoutNoticeInoutHist(Pageable pageable, RequestParams<St01402VO> requestParams) {
        St01402VO st01402VO = new St01402VO();
        //St01402VO.setRequestName(requestParams.getString("requestName"));
        //검색조건 추가시

        return filter(st014Mapper.getStWithoutNoticeInoutHist(st01402VO), pageable, "", St01402VO.class);
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
        return filter(st014Mapper.getStExceptRecordResult(st01102VO), pageable, "", St01102VO.class);

    }*/


}
