package rmsoft.ams.seoul.ad.ad005.controller;

import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.api.response.Responses;
import io.onsemiro.core.parameter.RequestParams;
import io.onsemiro.utils.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rmsoft.ams.seoul.ad.ad005.service.Ad005Service;
import rmsoft.ams.seoul.ad.ad005.vo.Ad00501VO;
import rmsoft.ams.seoul.common.controller.MessageBaseController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Ad 005 controller.
 */
@RestController
@RequestMapping("/api/v1/ad/ad005")
public class Ad005Controller extends MessageBaseController {

    @Autowired
    private Ad005Service service;

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
    @RequestMapping("/searchGlossary")
    public Responses.ListResponse searchGlossary(RequestParams<Ad00501VO> param) {
        return Responses.ListResponse.of(service.searchGlossary(param));
    }

    /**
     * Gets entity column list.
     *
     * @param param the param
     * @return the entity column list
     */
    @RequestMapping("/getEntityColumnList")
    public Responses.ListResponse getEntityColumnList(RequestParams<Ad00501VO> param) {
        return Responses.ListResponse.of(service.getEntityColumnList(param));
    }

    /**
     * Save entity type api response.
     *
     * @param ad00501VOList the ad 00501 vo list
     * @return the api response
     */
    @RequestMapping("/saveGlossary")
    public ApiResponse saveEntityType(@RequestBody List<Ad00501VO> ad00501VOList) {
        return service.saveGlossary(ad00501VOList);
    }

}
