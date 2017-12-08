package rmsoft.ams.seoul.rc.rc001.service;

import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.code.ApiStatus;
import io.onsemiro.core.domain.BaseService;
import io.onsemiro.utils.ModelMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rmsoft.ams.seoul.common.vo.ResponseForPaging;
import rmsoft.ams.seoul.rc.rc001.dao.Rc001Mapper;
import rmsoft.ams.seoul.rc.rc001.vo.Rc00101VO;
import rmsoft.ams.seoul.rc.rc001.vo.Rc00102VO;
import rmsoft.ams.seoul.rc.rc001.vo.Rc00103VO;
import rmsoft.ams.seoul.rc.rc001.vo.Rc00104VO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * The type Rc 001 service.
 */
@Service("Rc001Service")
public class Rc001Service extends BaseService
{
    @Autowired
    private Rc001Mapper rc001Mapper;

    /**
     * Gets all node.
     *
     * @param param the param
     * @return the all node
     */
    public List<Rc00101VO> getAllNode(Rc00101VO param)
    {
        ArrayList<Rc00101VO> nodes = new ArrayList<Rc00101VO>();
        nodes.addAll(rc001Mapper.getAggregationNode(param));

        //
        if(null != param.getIsDisplayItem() && param.getIsDisplayItem().equals("true")
                //||(null != param.getUuid() && !param.getUuid().equals(""))
                )
            nodes.addAll(rc001Mapper.getItemNode(param));

        return nodes;
    }

    /**
     * Gets all node.
     *
     * @param param the param
     * @return the all node
     */
    public ResponseForPaging<Rc00101VO> getAllNode(Rc00104VO param)
    {
        ResponseForPaging pageData = ModelMapperUtils.map(param,ResponseForPaging.class);

        pageData.setLimit(rc001Mapper.getTotalIconCnt(param));
        param.setLimit(pageData.getLimit());
        pageData.setList(rc001Mapper.getIconList(param));

        return pageData;
    }

    /**
     * Gets aggregation info.
     *
     * @param param the param
     * @return the aggregation info
     */
    public Rc00102VO getAggregationInfo(Rc00101VO param)
    {
        return rc001Mapper.getAggregationInfo(param);
    }

    /**
     * Gets item info.
     *
     * @param param the param
     * @return the item info
     */
    public Rc00102VO getItemInfo(Rc00101VO param)
    {
        return rc001Mapper.getItemInfo(param);
    }

    /**
     * Gets grid data.
     *
     * @param param the param
     * @return the grid data
     */
    public List<Rc00103VO> getGridData(Rc00101VO param)
    {
        List<Rc00103VO> gridData = new ArrayList<Rc00103VO>();
        gridData.addAll(rc001Mapper.getGridDataInAggregation(param));

        //
        if(null != param.getUuid() && !param.getUuid().equals(""))
            gridData.addAll(rc001Mapper.getGridDataInItem(param));

        return gridData;
    }

    /**
     * Save api response.
     *
     * @param list the list
     * @return the api response
     */
    public ApiResponse save(List<Map<String,String>> list)
    {
        for (Map<String,String> data: list) {
            rc001Mapper.save(data);
        }

        return ApiResponse.of(ApiStatus.SUCCESS,"SUCCESS");
    }

    /**
     * Gets menu.
     *
     * @param param the param
     * @return the menu
     */
    public Object getMenu(Map<String,String> param)
    {
        return rc001Mapper.getMenu(param);
    }

}
