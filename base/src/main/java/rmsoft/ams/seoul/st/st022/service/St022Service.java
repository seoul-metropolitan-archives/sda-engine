package rmsoft.ams.seoul.st.st022.service;

import io.onsemiro.core.domain.BaseService;
import io.onsemiro.core.parameter.RequestParams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rmsoft.ams.seoul.st.st022.dao.St022Mapper;
import rmsoft.ams.seoul.st.st022.vo.St02201VO;

import javax.inject.Inject;

@Service
public class St022Service extends BaseService {

    @Inject
    private St022Mapper st022Mapper;

    public Page<St02201VO> getInventoryPlanList(Pageable pageable, RequestParams<St02201VO> requestParams){
        St02201VO st02201VO = new St02201VO();
        st02201VO.setRepositoryUuid(requestParams.getString("repositoryUuid"));
        st02201VO.setShelfUuid(requestParams.getString("shelfUuid"));
        st02201VO.setLocationUuid(requestParams.getString("locationUuid"));
        st02201VO.setContainerUuid(requestParams.getString("containerUuid"));

        return filter(st022Mapper.getInventoryPlanList(st02201VO), pageable, "", St02201VO.class);
    }


}
