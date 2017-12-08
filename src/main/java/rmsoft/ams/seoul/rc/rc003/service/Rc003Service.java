package rmsoft.ams.seoul.rc.rc003.service;

import io.onsemiro.core.domain.BaseService;
import io.onsemiro.core.parameter.RequestParams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rmsoft.ams.seoul.rc.rc003.dao.Rc003Mapper;
import rmsoft.ams.seoul.rc.rc003.vo.Rc00301VO;

import javax.inject.Inject;

/**
 * The type Rc 003 service.
 */
@Service
public class Rc003Service extends BaseService{
    @Inject
    private Rc003Mapper rc003Mapper;

    /**
     * Get record aggregation list page.
     *
     * @param pageable      the pageable
     * @param requestParams the request params
     * @return the page
     */
    public Page<Rc00301VO> getRecordAggregationList(Pageable pageable, RequestParams<Rc00301VO> requestParams){
        Rc00301VO rc00301VO = new Rc00301VO();
        rc00301VO.setAggregationUuid(requestParams.getString("aggregationUuid"));
        return filter(rc003Mapper.getRecordAggregationList(rc00301VO), pageable, "", Rc00301VO.class);
    }
}
