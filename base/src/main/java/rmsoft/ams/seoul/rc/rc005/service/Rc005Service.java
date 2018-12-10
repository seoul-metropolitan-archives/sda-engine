package rmsoft.ams.seoul.rc.rc005.service;

import io.onsemiro.core.domain.BaseService;
import io.onsemiro.core.parameter.RequestParams;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rmsoft.ams.seoul.rc.rc005.dao.Rc005Mapper;
import rmsoft.ams.seoul.rc.rc005.vo.Rc00501VO;
import rmsoft.ams.seoul.rc.rc005.vo.Rc00502VO;

import javax.inject.Inject;
import java.util.List;

/**
 * The type Rc 005 service.
 */
@Service
public class Rc005Service extends BaseService{
    @Inject
    private Rc005Mapper rc005Mapper;

    /**
     * Get record item list page.
     *
     * @param pageable      the pageable
     * @param requestParams the request params
     * @return the page
     */
    public Page<Rc00501VO> getRecordItemList(Pageable pageable, RequestParams<Rc00501VO> requestParams){
        Rc00501VO rc00501VO = new Rc00501VO();
        Rc00502VO rc00502VO;
        rc00501VO.setRiAggregationUuid(requestParams.getString("aggregationUuid"));
        rc00501VO.setRiItemUuid(requestParams.getString("itemUuid"));

        List<Rc00501VO>  rc00501VOList =  rc005Mapper.getRecordItemList(rc00501VO);
        for (Rc00501VO rc00501VO1 : rc00501VOList){
            if(StringUtils.isNotEmpty(rc00501VO1.getRiItemUuid())) {
                if (StringUtils.isNotEmpty(rc00501VO1.getRiAggregationUuid())) {
                    rc00502VO = new Rc00502VO();
                    rc00502VO.setItemUuid(rc00501VO1.getRiItemUuid());
                    rc00501VO1.setRc00502VoList(rc005Mapper.getRecordComponentList(rc00502VO));
                    rc00501VO1.setCreatorList(rc005Mapper.getCreatorList(requestParams.getString("itemUuid")));
                    rc00501VO1.setRelatedAuthorityList(rc005Mapper.getRelatedAuthorityList(requestParams.getString("itemUuid")));
                }
            }
        }
        return filter(rc00501VOList, pageable, "", Rc00501VO.class);
    }
}
