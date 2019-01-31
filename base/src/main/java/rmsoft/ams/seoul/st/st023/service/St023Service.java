package rmsoft.ams.seoul.st.st023.service;

import io.onsemiro.core.domain.BaseService;
import io.onsemiro.core.parameter.RequestParams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rmsoft.ams.seoul.st.st023.dao.St023Mapper;
import rmsoft.ams.seoul.st.st023.vo.St02301VO;

import javax.inject.Inject;

@Service
public class St023Service extends BaseService {

    @Inject
    private St023Mapper st023Mapper;


    public Page<St02301VO> getInoutList(Pageable pageable, RequestParams<St02301VO> requestParams) {
        St02301VO st02301VO = new St02301VO();
        st02301VO.setRepositoryUuid(requestParams.getString("repositoryUuid"));
        st02301VO.setShelfUuid(requestParams.getString("shelfUuid"));
        st02301VO.setLocationUuid(requestParams.getString("locationUuid"));

        return filter(st023Mapper.getInoutList(st02301VO), pageable, "", St02301VO.class);
    }
}
