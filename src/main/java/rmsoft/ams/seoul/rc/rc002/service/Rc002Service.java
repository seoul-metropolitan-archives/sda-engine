package rmsoft.ams.seoul.rc.rc002.service;

import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.code.ApiStatus;
import io.onsemiro.core.domain.BaseService;
import io.onsemiro.utils.ModelMapperUtils;
import io.onsemiro.utils.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rmsoft.ams.seoul.common.domain.RcAggregation;
import rmsoft.ams.seoul.common.domain.RcAggregationCon;
import rmsoft.ams.seoul.common.domain.RcItem;
import rmsoft.ams.seoul.common.domain.RcItemCon;
import rmsoft.ams.seoul.common.repository.*;
import rmsoft.ams.seoul.rc.rc002.vo.Rc00205VO;
import rmsoft.ams.seoul.rc.rc002.vo.Rc002VO;

import java.util.List;

/**
 * The type Rc 002 service.
 */
@Service("Rc002Service")
public class Rc002Service extends BaseService
{
    @Autowired
    private RcLevelRepository rcLevelRepository;

    @Autowired
    private RcAggregationRepository rcAggregationRepository;

    @Autowired
    private RcAggregationConRepository rcAggregationConRepository;

    @Autowired
    private RcItemRepository rcItemRepository;

    @Autowired
    private RcItemConRepository rcItemConRepository;


    /**
     * Get level list.
     *
     * @return the list
     */
    public List<Rc00205VO> getLevel(){
        return ModelMapperUtils.mapList(rcLevelRepository.findAll(),Rc00205VO.class);
    }

    /**
     * Save api response.
     *
     * @param data the data
     * @return the api response
     */
    public ApiResponse save(Rc002VO data)
    {

        String uuid = "";
        boolean isCreate = false;
        RcAggregation rcAggregation = null;
        RcAggregationCon rcAggregationCon = null;
        RcItem rcItem = null;
        RcItemCon rcItemCon = null;
        List<RcAggregation> childrenAggregation = null;
        List<RcAggregation> referenceAggregation = null;
        List<RcItem> referenceItem = null;

        rcAggregation = ModelMapperUtils.map(data.getSystemMeta(),RcAggregation.class);
        rcAggregationCon = ModelMapperUtils.map(data.getContextualMeta(),RcAggregationCon.class);

        childrenAggregation = ModelMapperUtils.mapList(data.getChildrenAggregationList(),RcAggregation.class);
        referenceAggregation = ModelMapperUtils.mapList(data.getReferenceAggregationList(),RcAggregation.class);
        referenceItem = ModelMapperUtils.mapList(data.getReferenceItemList(),RcItem.class);
        if(null == rcAggregation.getAggregationUuid() || rcAggregation.getAggregationUuid().equals(""))
        {
            uuid = UUIDUtils.getUUID();
            rcAggregation.setAggregationUuid(uuid);
            isCreate = true;
        }
        else
            uuid = rcAggregation.getAggregationUuid();

        if(isCreate)
        {
            rcAggregationCon.setAggregationUuid(uuid);

            if(null != childrenAggregation)
            {
                for(RcAggregation child : childrenAggregation)
                {
                    child.setParentsAggregationUuid(uuid);
                }
            }
            if(null != referenceAggregation)
            {
                for(RcAggregation child : referenceAggregation)
                {
                    child.setParentsAggregationUuid(uuid);
                }
            }
            if(null != referenceItem)
            {
                for(RcItem child : referenceItem)
                {
                    child.setAggregationUuid(uuid);
                }
            }

        }

        return ApiResponse.of(ApiStatus.SUCCESS,"SUCCESS");
    }
}
