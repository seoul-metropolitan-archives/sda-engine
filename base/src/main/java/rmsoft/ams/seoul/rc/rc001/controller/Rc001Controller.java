package rmsoft.ams.seoul.rc.rc001.controller;

import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.api.response.Responses;
import io.onsemiro.core.parameter.RequestParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import rmsoft.ams.seoul.common.controller.MessageBaseController;
import rmsoft.ams.seoul.common.vo.PageInfoVO;
import rmsoft.ams.seoul.rc.rc001.service.Rc001Service;
import rmsoft.ams.seoul.rc.rc001.vo.Rc00101VO;
import rmsoft.ams.seoul.rc.rc001.vo.Rc00104VO;
import rmsoft.ams.seoul.rc.rc001.vo.Rc00105VO;
import rmsoft.ams.seoul.rc.rc005.vo.Rc00501VO;
import rmsoft.ams.seoul.rc.rc005.vo.Rc00502VO;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rc/rc001")
public class Rc001Controller extends MessageBaseController
{
    @Autowired
    private Rc001Service rc001Service;

    @RequestMapping("/getAllNodes")
    public Responses.ListResponse getAllNode(Rc00101VO param)
    {
        return Responses.ListResponse.of(rc001Service.getAllNode(param));
    }
    @RequestMapping("/getAllNodeForPaging")
    public PageInfoVO getAllNodeForPaging(Rc00104VO param)
    {
        return rc001Service.getAllNode(param);
    }

    @RequestMapping("/getAggregationInfo")
    @ResponseBody
    public Object getAggregationInfo(Rc00101VO param)
    {
        return rc001Service.getAggregationInfo(param);
    }
    @RequestMapping("/getItemInfo")
    @ResponseBody
    public Object getItemInfo(Rc00101VO param)
    {
        return rc001Service.getItemInfo(param);
    }

    @RequestMapping("/getGridData")
    public Responses.ListResponse getGridData(Rc00101VO param)
    {
        return Responses.ListResponse.of(rc001Service.getGridData(param));
    }

    @RequestMapping("/getGridDataForPaging")
    @ResponseBody
    public Object getGridDataForPaging(Rc00104VO param)
    {
        return rc001Service.getGridDataForPaging(param);
    }

    @RequestMapping("/save")
    public ApiResponse save(@RequestBody List<Map<String,String>> list)
    {
        return rc001Service.save(list);
    }

    @RequestMapping("/getMenuInfo")
    @ResponseBody
    public Object getMenu(@RequestBody Map<String,String> param)
    {
        return rc001Service.getMenu(param);
    }

    @RequestMapping("/getNaviData")
    @ResponseBody
    public Responses.ListResponse getNaviData(Rc00101VO param)
    {
        return Responses.ListResponse.of(rc001Service.getNaviData(param));
    }

    @RequestMapping("/move")
    public ApiResponse move(@RequestBody Rc00105VO data)
    {
        return rc001Service.move(data);
    }

    @RequestMapping("/updateState")
    public ApiResponse updateState(@RequestBody List<Map<String,String>> list)
    {
        return rc001Service.updateState(list);
    }

    @RequestMapping("/deleteAggregation")
    public ApiResponse deleteAggregation(@RequestBody List<Rc00101VO> list)
    {
        return rc001Service.deleteAggregation(list);
    }

    /**
     * Update status api response.
     *
     * @param param the request params
     * @return the api response
     */
    @PutMapping(value = "/moveComponent")
    @PostMapping
    public ApiResponse moveComponent(@RequestBody List<Rc00502VO> param){
        return rc001Service.moveComponent(param);
    }

    /**
     *
     * @param param the request params
     * @return the api response
     */
    @PutMapping(value = "/delItemAndMoveComponent")
    @PostMapping
    public ApiResponse delItemAndMoveComponent(@RequestBody List<Rc00502VO> param){
        return rc001Service.delItemAndMoveComponent(param);
    }

    /**
     *
     * @param params the request params
     * @return the api response
     */
    @PutMapping(value = "/creItemAndMoveComponent")
    @PostMapping
    public ApiResponse creItemAndMoveComponent(@RequestBody Rc00501VO params){
        return rc001Service.creItemAndMoveComponent(params);
    }
    /**
     *
     * @param params the request params
     * @return the api response
     */
    @RequestMapping("/updateAggregationType")
    @ResponseBody
    public Responses.MapResponse updateAggregationType(Rc00101VO params){
        return rc001Service.updateAggregationType(params);
    }

}
