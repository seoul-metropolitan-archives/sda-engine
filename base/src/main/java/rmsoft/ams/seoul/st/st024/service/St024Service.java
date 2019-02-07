package rmsoft.ams.seoul.st.st024.service;

import io.onsemiro.core.domain.BaseService;
import io.onsemiro.core.parameter.RequestParams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rmsoft.ams.seoul.st.st023.vo.St02301VO;
import rmsoft.ams.seoul.st.st024.dao.St024Mapper;
import rmsoft.ams.seoul.st.st024.vo.St02401VO;
import rmsoft.ams.seoul.st.st024.vo.St02402VO;
import rmsoft.ams.seoul.st.st024.vo.St02403VO;

import javax.inject.Inject;

@Service
public class St024Service extends BaseService {

    @Inject
    private St024Mapper st024Mapper;

    public Page<St02401VO> getTagList(Pageable pageable, RequestParams<St02401VO> requestParams) {
        St02401VO st02401VO = new St02401VO();
        st02401VO.setRepositoryUuid(requestParams.getString("repositoryUuid"));
        st02401VO.setShelfUuid(requestParams.getString("shelfUuid"));
        st02401VO.setLocationUuid(requestParams.getString("locationUuid"));
        st02401VO.setCode(requestParams.getString("code"));
        st02401VO.setTitle(requestParams.getString("title"));

        return filter(st024Mapper.getTagList(st02401VO), pageable, "", St02401VO.class);
    }

    public Page<St02402VO> getTagList02(Pageable pageable, RequestParams<St02402VO> requestParams) {
        St02402VO st02402VO = new St02402VO();
        st02402VO.setUserUuid(requestParams.getString("userUuid"));
        st02402VO.setCreateDateFrom(requestParams.getString("createDateFrom"));
        st02402VO.setCreateDateTo(requestParams.getString("createDateTo"));
        st02402VO.setCode(requestParams.getString("code"));
        st02402VO.setTitle(requestParams.getString("title"));
        return filter(st024Mapper.getTagList02(st02402VO), pageable, "", St02402VO.class);
    }

    public Page<St02403VO> getTagList03(Pageable pageable, RequestParams<St02403VO> requestParams) {
        St02403VO st02403VO = new St02403VO();
        st02403VO.setPublishStatusUuid(requestParams.getString("publishStatusUuid"));
        st02403VO.setCode(requestParams.getString("code"));
        st02403VO.setTitle(requestParams.getString("title"));

        return filter(st024Mapper.getTagList03(st02403VO), pageable, "", St02403VO.class);
    }
}
