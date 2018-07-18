package rmsoft.ams.seoul.ad.ad006.controller;

import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.api.response.Responses;
import io.onsemiro.core.parameter.RequestParams;
import io.onsemiro.utils.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rmsoft.ams.seoul.ad.ad006.service.Ad006Service;
import rmsoft.ams.seoul.ad.ad006.vo.Ad00601VO;
import rmsoft.ams.seoul.ad.ad006.vo.Ad00602VO;
import rmsoft.ams.seoul.common.controller.MessageBaseController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Ad 006 controller.
 */
@RestController
@RequestMapping("/api/v1/ad/ad006")
public class Ad006Controller extends MessageBaseController {

    @Autowired
    private Ad006Service service;

    /**
     * Gets uuid.
     *
     * @return the uuid
     */
    @RequestMapping("/getUUID")
    public Responses.MapResponse getUUID() {
        Map<String, Object> response = new HashMap<String, Object>();
        response.put("uuid", UUIDUtils.getUUID());
        return Responses.MapResponse.of(response);
    }

    /**
     * Search entity type responses . list response.
     *
     * @param param the param
     * @return the responses . list response
     */
    @RequestMapping("/searchEntityType")
    public Responses.ListResponse searchEntityType(RequestParams<Ad00601VO> param) {
        return Responses.ListResponse.of(service.searchEntityType(param));
    }

    /**
     * Gets entity column list.
     *
     * @param param the param
     * @return the entity column list
     */
    @RequestMapping("/getEntityColumnList")
    public Responses.ListResponse getEntityColumnList(RequestParams<Ad00601VO> param) {
        return Responses.ListResponse.of(service.getEntityColumnList(param));
    }

    /**
     * Save entity type api response.
     *
     * @param ad00601VOList the ad 00601 vo list
     * @return the api response
     */
    @RequestMapping("/saveEntityType")
    public ApiResponse saveEntityType(@RequestBody List<Ad00601VO> ad00601VOList) {
        return service.saveEntityType(ad00601VOList);
    }

    /**
     * Save entity column api response.
     *
     * @param ad00602VOList the ad 00602 vo list
     * @return the api response
     */
    @RequestMapping("/saveEntityColumn")
    public ApiResponse saveEntityColumn(@RequestBody List<Ad00602VO> ad00602VOList) {
        return service.saveEntityColumn(ad00602VOList);
    }

}
