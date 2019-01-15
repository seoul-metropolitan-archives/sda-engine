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

    public Page<St02001VO> getStRfidTag(Pageable pageable, RequestParams<St02001VO> requestParams) {
        St02001VO St02001VO = new St02001VO();
        //St02001VO.setRequestName(requestParams.getString("requestName"));
        //검색조건 추가시
        St02001VO.setRepositoryUuid(requestParams.getString("repositoryUuid"));
        St02001VO.setShelfUuid(requestParams.getString("shelfUuid"));
        St02001VO.setLocationUuid(requestParams.getString("locationUuid"));
        St02001VO.setCode(requestParams.getString("code"));
        St02001VO.setTitle(requestParams.getString("title"));
        return filter(st020Mapper.getStRfidTag(St02001VO), pageable, "", St02001VO.class);
    }


}
