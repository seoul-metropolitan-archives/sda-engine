package rmsoft.ams.seoul.df.df001.controller;

import io.onsemiro.controller.BaseController;
import io.onsemiro.core.api.ApiException;
import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.api.response.Responses;
import io.onsemiro.core.code.ApiStatus;
import io.onsemiro.core.parameter.RequestParams;
import org.springframework.web.bind.annotation.*;
import rmsoft.ams.seoul.df.df001.service.Df001Service;
import rmsoft.ams.seoul.df.df001.vo.Df00101VO;
import rmsoft.ams.seoul.df.df001.vo.Df00102VO;

import javax.inject.Inject;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/df/df001")
public class Df001Controller extends BaseController {

    @Inject
    private Df001Service df001Service;

    /**
     * Search entity type responses . list response.
     *
     * @param param the param
     * @return the responses . list response
     */
    @GetMapping("/searchList")
    public Responses.ListResponse searchList(RequestParams<Df00101VO> param) {
        return Responses.ListResponse.of(df001Service.searchList(param));
    }

    /**
     * Gets Disposal Freeze detail.
     *
     * @param requestParams the request params
     * @return the classification scheme detail
     */
    @GetMapping("/detail")
    public Df00102VO getDetail(RequestParams<Df00102VO> requestParams) {
        return df001Service.getDetail(requestParams);
    }

    /**
     * Save entity type api response.
     *
     * @param itemList the ad 00501 vo list
     * @return the api response
     */
    @PutMapping(value = "/saveItems")
    @PostMapping
    public ApiResponse saveItems(@RequestBody List<Df00101VO> itemList) {
        return df001Service.saveItems(itemList);
    }

    /**
     * Update status api response.
     *
     * @param requestParams the request params
     * @return the api response
     */
    @PutMapping(value = "/updateStatus")
    @PostMapping
    public ApiResponse updateStatus(@RequestBody List<Df00101VO> requestParams) {
        ApiResponse apiResponse = df001Service.updateStatus(requestParams);
        if (apiResponse.getStatus() == -1) {
            throw new ApiException(ApiStatus.SYSTEM_ERROR, apiResponse.getMessage());
        }
        return apiResponse;
    }

    /**
     * Update Disposal Freeze con detail.
     *
     * @param requestParams the request params
     */
    @GetMapping("/saveDetailItem")
    public void saveDetailItem(RequestParams<Df00101VO> requestParams) {
        df001Service.saveDetailItem(requestParams);
    }
}