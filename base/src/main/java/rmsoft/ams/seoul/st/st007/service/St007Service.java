package rmsoft.ams.seoul.st.st007.service;

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

import rmsoft.ams.seoul.st.st007.dao.St007Mapper;
import rmsoft.ams.seoul.st.st007.vo.St00701VO;
import rmsoft.ams.seoul.st.st007.vo.St00702VO;

import javax.inject.Inject;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class St007Service extends BaseService {
    @Inject
    private St007Mapper st007Mapper;

    public Page<St00701VO> getDisposalList(Pageable pageable, RequestParams<St00701VO> requestParams) {
        St00701VO St00701VO = new St00701VO();
        St00701VO.setCode(requestParams.getString("code"));
        St00701VO.setTitle(requestParams.getString("title"));
        St00701VO.setRepositoryUuid(requestParams.getString("repositoryUuid"));
        St00701VO.setShelfUuid(requestParams.getString("shelfUuid"));
        St00701VO.setLocationUuid(requestParams.getString("locationUuid"));
        St00701VO.setContainerTypeUuid(requestParams.getString("containerTypeUuid"));


        //검색조건 추가시

        return filter(st007Mapper.getDisposalList(St00701VO), pageable, "", St00701VO.class);
    }
    public Page<St00702VO> getDisposalItem(Pageable pageable, RequestParams<St00702VO> requestParams) {

        St00702VO st00702VO = new St00702VO();
        st00702VO.setDisposalDueDateStart(requestParams.getTimestamp("disposalDueDateStart"));
        st00702VO.setDisposalDueDateEnd(requestParams.getTimestamp("disposalDueDateEnd"));

        //st00702VO.setAggregationUuid(requestParams.getString("aggregationUuid"));
        //검색조건 추가시

        return filter(st007Mapper.getDisposalItem(st00702VO), pageable, "", St00702VO.class);
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
        return filter(st007Mapper.getStExceptRecordResult(st01102VO), pageable, "", St01102VO.class);

    }*/


}
