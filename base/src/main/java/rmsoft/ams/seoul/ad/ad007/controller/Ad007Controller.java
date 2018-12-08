package rmsoft.ams.seoul.ad.ad007.controller;

import io.onsemiro.controller.BaseController;
import io.onsemiro.core.api.ApiException;
import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.api.response.Responses;
import io.onsemiro.core.code.ApiStatus;
import io.onsemiro.core.parameter.RequestParams;
import org.springframework.web.bind.annotation.*;
import rmsoft.ams.seoul.ad.ad007.service.Ad007Service;
import rmsoft.ams.seoul.ad.ad007.vo.Ad00701VO;
import rmsoft.ams.seoul.ad.ad007.vo.Ad00702VO;

import javax.inject.Inject;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/ad/ad007")
public class Ad007Controller extends BaseController {

    @Inject
    private Ad007Service ad007Service;

    /**
     * Search entity type responses . list response.
     *
     * @param param the param
     * @return the responses . list response
     */
    @GetMapping("/list")
    public Responses.ListResponse searchSetup(RequestParams<Ad00701VO> param) {
        return Responses.ListResponse.of(ad007Service.searchSetup(param));
    }

    /**
     * Search entity type responses . list response.
     *
     * @param param the param
     * @return the responses . list response
     */
    @GetMapping("/listSub")
    public Responses.ListResponse searchSegment(RequestParams<Ad00701VO> param) {
        return Responses.ListResponse.of(ad007Service.searchSegment(param));
    }

    /**
     * Save entity type api response.
     *
     * @param itemList the ad 00501 vo list
     * @return the api response
     */
    @PutMapping(value = "/save")
    @PostMapping
    public ApiResponse saveSetup(@RequestBody List<Ad00701VO> itemList) {
        return ad007Service.saveSetup(itemList);
    }
    /**
     * Save entity type api response.
     *
     * @param itemList the ad 00501 vo list
     * @return the api response
     */
    @PutMapping(value = "/saveSub")
    @PostMapping
    public ApiResponse saveSegment(@RequestBody List<Ad00702VO> itemList) {
        return ad007Service.saveSegment(itemList);
    }

    /**
     * Update status api response.
     *
     * @param requestParams the request params
     * @return the api response
     */
    @PutMapping(value = "/updateStatus")
    @PostMapping
    public ApiResponse updateStatus(@RequestBody List<Ad00701VO> requestParams) {
        ApiResponse apiResponse = ad007Service.updateStatus(requestParams);
        if (apiResponse.getStatus() == -1) {
            throw new ApiException(ApiStatus.SYSTEM_ERROR, apiResponse.getMessage());
        }
        return apiResponse;
    }

}