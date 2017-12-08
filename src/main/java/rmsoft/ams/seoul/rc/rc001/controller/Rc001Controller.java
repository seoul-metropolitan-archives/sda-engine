package rmsoft.ams.seoul.rc.rc001.controller;

import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.api.response.Responses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import rmsoft.ams.seoul.common.controller.MessageBaseController;
import rmsoft.ams.seoul.common.vo.PageInfoVO;
import rmsoft.ams.seoul.rc.rc001.service.Rc001Service;
import rmsoft.ams.seoul.rc.rc001.vo.Rc00101VO;
import rmsoft.ams.seoul.rc.rc001.vo.Rc00104VO;

import java.util.List;
import java.util.Map;

/**
 * The type Rc 001 controller.
 */
@RestController
@RequestMapping("/rc/rc001")
public class Rc001Controller extends MessageBaseController
{
    @Autowired
    private Rc001Service rc001Service;

    /**
     * Gets all node.
     *
     * @param param the param
     * @return the all node
     */
    @RequestMapping("/getAllNodes")
    public Responses.ListResponse getAllNode(Rc00101VO param)
    {
        return Responses.ListResponse.of(rc001Service.getAllNode(param));
    }

    /**
     * Gets all node for paging.
     *
     * @param param the param
     * @return the all node for paging
     */
    @RequestMapping("/getAllNodeForPaging")
    public PageInfoVO getAllNodeForPaging(Rc00104VO param)
    {
        return rc001Service.getAllNode(param);
    }

    /**
     * Gets aggregation info.
     *
     * @param param the param
     * @return the aggregation info
     */
    @RequestMapping("/getAggregationInfo")
    @ResponseBody
    public Object getAggregationInfo(Rc00101VO param)
    {
        return rc001Service.getAggregationInfo(param);
    }

    /**
     * Gets item info.
     *
     * @param param the param
     * @return the item info
     */
    @RequestMapping("/getItemInfo")
    @ResponseBody
    public Object getItemInfo(Rc00101VO param)
    {
        return rc001Service.getItemInfo(param);
    }

    /**
     * Gets grid data.
     *
     * @param param the param
     * @return the grid data
     */
    @RequestMapping("/getGridData")
    public Responses.ListResponse getGridData(Rc00101VO param)
    {
        return Responses.ListResponse.of(rc001Service.getGridData(param));
    }

    /**
     * Save api response.
     *
     * @param list the list
     * @return the api response
     */
    @RequestMapping("/save")
    public ApiResponse save(@RequestBody List<Map<String,String>> list)
    {
        return rc001Service.save(list);
    }

    /**
     * Gets menu.
     *
     * @param param the param
     * @return the menu
     */
    @RequestMapping("/getMenuInfo")
    @ResponseBody
    public Object getMenu(@RequestBody Map<String,String> param)
    {
        return rc001Service.getMenu(param);
    }


}
