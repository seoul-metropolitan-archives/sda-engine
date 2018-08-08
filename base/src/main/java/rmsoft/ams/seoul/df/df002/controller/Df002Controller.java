package rmsoft.ams.seoul.df.df002.controller;

import io.onsemiro.controller.BaseController;
import io.onsemiro.core.api.ApiException;
import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.api.response.Responses;
import io.onsemiro.core.code.ApiStatus;
import io.onsemiro.core.parameter.RequestParams;
import org.springframework.web.bind.annotation.*;
import rmsoft.ams.seoul.df.df002.service.Df002Service;
import rmsoft.ams.seoul.df.df002.vo.Df00201VO;

import javax.inject.Inject;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/df/df002")
public class Df002Controller extends BaseController {

    @Inject
    private Df002Service df002Service;

    /**
     * Search entity type responses . list response.
     *
     * @param param the param
     * @return the responses . list response
     */
    @GetMapping("/searchList")
    public Responses.ListResponse searchList(RequestParams<Df00201VO> param) {
        return Responses.ListResponse.of(df002Service.searchList(param));
    }

    /**
     * Save entity type api response.
     *
     * @param itemList the ad 00501 vo list
     * @return the api response
     */
    @PutMapping(value = "/saveItems")
    @PostMapping
    public ApiResponse saveItems(@RequestBody List<Df00201VO> itemList) {
        return df002Service.saveItems(itemList);
    }

    /**
     * Update status api response.
     *
     * @param requestParams the request params
     * @return the api response
     */
    @PutMapping(value = "/updateStatus")
    @PostMapping
    public ApiResponse updateStatus(@RequestBody List<Df00201VO> requestParams) {
        ApiResponse apiResponse = df002Service.updateStatus(requestParams);
        if (apiResponse.getStatus() == -1) {
            throw new ApiException(ApiStatus.SYSTEM_ERROR, apiResponse.getMessage());
        }
        return apiResponse;
    }

}