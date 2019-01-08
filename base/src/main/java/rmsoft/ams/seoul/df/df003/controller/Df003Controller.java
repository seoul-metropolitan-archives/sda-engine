package rmsoft.ams.seoul.df.df003.controller;

import io.onsemiro.controller.BaseController;
import io.onsemiro.core.api.ApiException;
import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.api.response.Responses;
import io.onsemiro.core.code.ApiStatus;
import io.onsemiro.core.parameter.RequestParams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import rmsoft.ams.seoul.df.df003.service.Df003Service;
import rmsoft.ams.seoul.df.df003.vo.Df00301VO;
import rmsoft.ams.seoul.df.df003.vo.Df00302VO;
import rmsoft.ams.seoul.df.df003.vo.Df00303VO;

import javax.inject.Inject;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/df/df003")
public class Df003Controller extends BaseController {

    @Inject
    private Df003Service df003Service;

    /**
     * Search entity type responses . list response.
     *
     * @param param the param
     * @return the responses . list response
     */
    @GetMapping("/searchTree")
    public Responses.ListResponse searchTree(RequestParams<Df00301VO> param) {
        return Responses.ListResponse.of(df003Service.searchTree(param));
    }

    /**
     * Search entity type responses . list response.
     *
     * @param param the param
     * @return the responses . list response
     */
    @GetMapping("/searchList")
    public Responses.ListResponse searchList(RequestParams<Df00302VO> param) {
        return Responses.ListResponse.of(df003Service.searchList(param));
    }

    /**
     * Save entity type api response.
     *
     * @param itemList the ad 00501 vo list
     * @return the api response
     */
    @PutMapping(value = "/saveItems")
    @PostMapping
    public ApiResponse saveItems(@RequestBody List<Df00302VO> itemList) {
        return df003Service.saveItems(itemList);
    }


    /**
     * Update status api response.
     *
     * @param requestParams the request params
     * @return the api response
     */
    @PutMapping(value = "/unfreeze")
    @PostMapping
    public ApiResponse unfreeze(@RequestBody List<Df00302VO> requestParams) {
        ApiResponse apiResponse = df003Service.unfreeze(requestParams);
        if (apiResponse.getStatus() == -1) {
            throw new ApiException(ApiStatus.SYSTEM_ERROR, apiResponse.getMessage());
        }
        return apiResponse;
    }

    /**
     * Update status api response.
     *
     * @param requestParams the request params
     * @return the api response
     */
    @GetMapping(value = "/freeze/search")
    public Responses.PageResponse searchFreeze(Pageable pageable, RequestParams<Df00302VO> requestParams) {
        Page<Df00302VO> pages  = df003Service.search(pageable, requestParams);
        return Responses.PageResponse.of(pages.getContent(), pages);
    }

    /**
     * Update status api response.
     *
     * @param requestParams the request params
     * @return the api response
     */
    @GetMapping(value = "/freeze/aggregationTree")
    public Responses.ListResponse searchAggregationTree(RequestParams<Df00303VO> requestParams) {
        List<Df00303VO> lists  = df003Service.searchAggregationTree(requestParams);
        return Responses.ListResponse.of(lists);
    }

}