package rmsoft.ams.seoul.st.st021.service;

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

import rmsoft.ams.seoul.st.st021.dao.St021Mapper;
import rmsoft.ams.seoul.st.st021.vo.St02101VO;


import javax.inject.Inject;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class St021Service extends BaseService {
    @Inject
    private St021Mapper st021Mapper;

    public Page<St02101VO> getStWithoutNoticeInoutHistStatistic(Pageable pageable, RequestParams<St02101VO> requestParams) {
        St02101VO St02101VO = new St02101VO();
        //St02101VO.setRequestName(requestParams.getString("requestName"));
        //검색조건 추가시

        return filter(st021Mapper.getStWithoutNoticeInoutHistStatistic(St02101VO), pageable, "", St02101VO.class);
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
        return filter(st021Mapper.getStExceptRecordResult(st01102VO), pageable, "", St01102VO.class);

    }*/


}
