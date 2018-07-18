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
    @GetMapping("/searchList")
    public Responses.ListResponse searchList(RequestParams<Ad00701VO> param) {
        return Responses.ListResponse.of(ad007Service.searchList(param));
    }

    /**
     * Save entity type api response.
     *
     * @param itemList the ad 00501 vo list
     * @return the api response
     */
    @PutMapping(value = "/saveItems")
    @PostMapping
    public ApiResponse saveItems(@RequestBody List<Ad00701VO> itemList) {
        return ad007Service.saveItems(itemList);
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