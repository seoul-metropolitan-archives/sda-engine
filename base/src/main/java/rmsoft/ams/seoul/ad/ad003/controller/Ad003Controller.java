package rmsoft.ams.seoul.ad.ad003.controller;

import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.api.response.Responses;
import io.onsemiro.utils.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rmsoft.ams.seoul.ad.ad003.service.Ad003Service;
import rmsoft.ams.seoul.ad.ad003.vo.Ad00301VO;
import rmsoft.ams.seoul.ad.ad003.vo.Ad00302VO;
import rmsoft.ams.seoul.ad.ad003.vo.Ad00303VO;
import rmsoft.ams.seoul.common.controller.MessageBaseController;
import rmsoft.ams.seoul.utils.CommonCodeUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Ad 003 controller.
 */
@RestController
@RequestMapping("/api/v1/ad/ad003/")
public class Ad003Controller extends MessageBaseController {

    @Autowired
    private Ad003Service ad003Service;

    /**
     * Gets uuid.
     *
     * @return the uuid
     */
    @RequestMapping("/getUUID")
    public Responses.MapResponse getUUID() {
        Map<String,Object> response = new HashMap<String,Object>();
        response.put("uuid", UUIDUtils.getUUID());
        return Responses.MapResponse.of(response);
    }

    /**
     * Search code header responses . list response.
     *
     * @param param the param
     * @return the responses . list response
     */
    @RequestMapping("/searchCodeHeader")
    public Responses.ListResponse searchCodeHeader(@RequestBody Ad00301VO param) {
        return Responses.ListResponse.of(ad003Service.searchCodeHeader(param));
    }

    /**
     * Gets code detail list.
     *
     * @param param the param
     * @return the code detail list
     */
    @RequestMapping("/getCodeDetailList")
    public Responses.ListResponse getCodeDetailList(@RequestBody Ad00302VO param) {
        return Responses.ListResponse.of(ad003Service.getCodeDetailList(param));
    }

    /**
     * Save code header api response.
     *
     * @param ad00301VOList the ad 00301 vo list
     * @return the api response
     */
    @RequestMapping("/saveCodeHeader")
    public ApiResponse saveCodeHeader(@RequestBody List<Ad00301VO> ad00301VOList)
    {
        return ad003Service.saveCodeHeader(ad00301VOList);
    }

    /**
     * Save code detail api response.
     *
     * @param ad00302VOList the ad 00302 vo list
     * @return the api response
     */
    @RequestMapping("/saveCodeDetail")
    public ApiResponse saveCodeDetail(@RequestBody List<Ad00302VO> ad00302VOList)
    {
        return ad003Service.saveCodeDetail(ad00302VOList);
    }


    /**
     * Gets all by map.
     *
     * @return the all by map
     */
    @GetMapping("/getAllByMap")
    public Map<String, List<Ad00303VO>> getAllByMap() {
        return CommonCodeUtils.getAllByMap();
    }
}
