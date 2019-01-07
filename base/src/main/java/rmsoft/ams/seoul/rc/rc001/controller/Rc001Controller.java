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
import rmsoft.ams.seoul.common.vo.ResponseForPaging;
import rmsoft.ams.seoul.rc.rc001.service.Rc001Service;
import rmsoft.ams.seoul.rc.rc001.vo.Rc00101VO;
import rmsoft.ams.seoul.rc.rc001.vo.Rc00104VO;
import rmsoft.ams.seoul.rc.rc001.vo.Rc00105VO;
import rmsoft.ams.seoul.rc.rc001.vo.Rc00107VO;
import rmsoft.ams.seoul.rc.rc005.vo.Rc00501VO;
import rmsoft.ams.seoul.rc.rc005.vo.Rc00502VO;

import java.util.List;
import java.util.Map;

/**
 * The type Rc 001 controller.
 */
@RestController
@RequestMapping("/rc/rc001")
public class Rc001Controller extends MessageBaseController {
    @Autowired
    private Rc001Service rc001Service;

    /**
     * Gets all node.
     *
     * @param param the param
     * @return the all node
     */
    @RequestMapping("/getAllNodes")
    public Responses.ListResponse getAllNode(Rc00101VO param) {
        return Responses.ListResponse.of(rc001Service.getAllNode(param));
    }

    /**
     * Gets all node for paging.
     *
     * @param param the param
     * @return the all node for paging
     */
    @RequestMapping("/getAllNodeForPaging")
    public PageInfoVO getAllNodeForPaging(Rc00104VO param) {
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
    public Object getAggregationInfo(Rc00101VO param) {
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
    public Object getItemInfo(Rc00101VO param) {
        return rc001Service.getItemInfo(param);
    }

    /**
     * Gets grid data.
     *
     * @param param the param
     * @return the grid data
     */
    @RequestMapping("/getGridData")
    public Responses.ListResponse getGridData(Rc00101VO param) {
        return Responses.ListResponse.of(rc001Service.getGridData(param));
    }

    /**
     * Gets grid data for paging.
     *
     * @param param the param
     * @return the grid data for paging
     */
    @RequestMapping("/getGridDataForPaging")
    @ResponseBody
    public Object getGridDataForPaging(Rc00104VO param) {
        return rc001Service.getGridDataForPaging(param);
    }

    /**
     * Save api response.
     *
     * @param list the list
     * @return the api response
     */
    @RequestMapping("/save")
    public ApiResponse save(@RequestBody List<Map<String, String>> list) {
        return rc001Service.save(list);
    }

    /**
     * Save records api response.
     *
     * @param list the list
     * @return the api response
     */
    @RequestMapping("/saveRecords")
    public ApiResponse saveRecords(@RequestBody List<Map<String, String>> list) {
        return rc001Service.saveRecords(list);
    }

    /**
     * Save records grid api response.
     *
     * @param list the list
     * @return the api response
     */
    @RequestMapping("/saveRecordsGrid")
    public ApiResponse saveRecordsGrid(@RequestBody List<Map<String, Object>> list) {
        return rc001Service.saveRecordsGrid(list);
    }

    /**
     * Gets menu.
     *
     * @param param the param
     * @return the menu
     */
    @RequestMapping("/getMenuInfo")
    @ResponseBody
    public Object getMenu(@RequestBody Map<String, String> param) {
        return rc001Service.getMenu(param);
    }

    /**
     * Gets navi data.
     *
     * @param param the param
     * @return the navi data
     */
    @RequestMapping("/getNaviData")
    @ResponseBody
    public Responses.ListResponse getNaviData(Rc00101VO param) {
        return Responses.ListResponse.of(rc001Service.getNaviData(param));
    }

    /**
     * Move api response.
     *
     * @param data the data
     * @return the api response
     */
    @RequestMapping("/move")
    public ApiResponse move(@RequestBody Rc00105VO data) {
        return rc001Service.move(data);
    }

    /**
     * Update state api response.
     *
     * @param list the list
     * @return the api response
     */
    @RequestMapping("/updateState")
    public ApiResponse updateState(@RequestBody List<Map<String, String>> list) {
        return rc001Service.updateState(list);
    }

    /**
     * Delete aggregation api response.
     *
     * @param list the list
     * @return the api response
     */
    @RequestMapping("/deleteAggregation")
    public ApiResponse deleteAggregation(@RequestBody List<Rc00101VO> list) {
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
    public ApiResponse moveComponent(@RequestBody List<Rc00502VO> param) {
        return rc001Service.moveComponent(param);
    }

    /**
     * Del item and move component api response.
     *
     * @param param the request params
     * @return the api response
     */
    @PutMapping(value = "/delItemAndMoveComponent")
    @PostMapping
    public ApiResponse delItemAndMoveComponent(@RequestBody List<Rc00502VO> param) {
        return rc001Service.delItemAndMoveComponent(param);
    }

    /**
     * Cre item and move component api response.
     *
     * @param params the request params
     * @return the api response
     */
    @PutMapping(value = "/creItemAndMoveComponent")
    @PostMapping
    public ApiResponse creItemAndMoveComponent(@RequestBody Rc00501VO params) {
        return rc001Service.creItemAndMoveComponent(params);
    }

    /**
     * Update aggregation type responses . map response.
     *
     * @param params the request params
     * @return the api response
     */
    @RequestMapping("/updateAggregationType")
    @ResponseBody
    public Responses.MapResponse updateAggregationType(Rc00101VO params) {
        return rc001Service.updateAggregationType(params);
    }

    /**
     * Gets Search data for paging.
     *
     * @param param the param
     * @return the grid data for paging
     */
    @RequestMapping("/search")
    @ResponseBody
    public ResponseForPaging<Rc00107VO> getSearchDataForPaging(Rc00107VO param) {

        return rc001Service.getSearchDataForPaging(param);
    }
}
