package rmsoft.ams.seoul.lt.lt002.controller;

import io.onsemiro.controller.BaseController;
import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.api.response.Responses;
import io.onsemiro.core.parameter.RequestParams;
import org.springframework.web.bind.annotation.*;
import rmsoft.ams.seoul.lt.lt002.service.Lt002Service;
import rmsoft.ams.seoul.lt.lt002.vo.Lt00201VO;

import javax.inject.Inject;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/lt/lt002")
public class Lt002Controller extends BaseController {

    @Inject
    private Lt002Service lt002Service;

    /**
     * Search entity type responses . list response.
     *
     * @param param the param
     * @return the responses . list response
     */
    @GetMapping("/searchList")
    public Responses.ListResponse searchList(RequestParams<Lt00201VO> param) {
        return Responses.ListResponse.of(lt002Service.searchList(param));
    }

    /**
     * Save entity type api response.
     *
     * @param itemList the ad 00501 vo list
     * @return the api response
     */
    @PutMapping(value = "/saveItems")
    @PostMapping
    public ApiResponse saveItems(@RequestBody List<Lt00201VO> itemList) {
        return lt002Service.saveItems(itemList);
    }

}