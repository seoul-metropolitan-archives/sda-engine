package rmsoft.ams.seoul.st.st010.service;

import io.onsemiro.core.domain.BaseService;
import io.onsemiro.core.parameter.RequestParams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rmsoft.ams.seoul.st.st010.dao.St010Mapper;
import rmsoft.ams.seoul.st.st010.vo.St01004VO;

import javax.inject.Inject;

@Service
public class St010Service extends BaseService {

    @Inject
    private St010Mapper st010Mapper;


    public Page<St01004VO> getAggregation(Pageable pageable, RequestParams<St01004VO> requestParams) {
        St01004VO st01004VO = new St01004VO();
        st01004VO.setLocationUuid(requestParams.getString("locationUuid"));
        return filter(st010Mapper.getAggregation(st01004VO), pageable, "", St01004VO.class);
    }
}
