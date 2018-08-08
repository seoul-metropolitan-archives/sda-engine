package rmsoft.ams.seoul.lt.lt003.controller;

import io.onsemiro.controller.BaseController;
import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.api.response.Responses;
import io.onsemiro.core.parameter.RequestParams;
import org.springframework.web.bind.annotation.*;
import rmsoft.ams.seoul.lt.lt003.service.Lt003Service;
import rmsoft.ams.seoul.lt.lt003.vo.Lt00301VO;

import javax.inject.Inject;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/lt/lt003")
public class Lt003Controller extends BaseController {

    @Inject
    private Lt003Service lt003Service;

    /**
     * Search entity type responses . list response.
     *
     * @param param the param
     * @return the responses . list response
     */
    @GetMapping("/searchList")
    public Responses.ListResponse searchList(RequestParams<Lt00301VO> param) {
        return Responses.ListResponse.of(lt003Service.searchList(param));
    }

    /**
     * Save entity type api response.
     *
     * @param itemList the ad 00501 vo list
     * @return the api response
     */
    @PutMapping(value = "/saveItems")
    @PostMapping
    public ApiResponse saveItems(@RequestBody List<Lt00301VO> itemList) {

        return lt003Service.saveItems(itemList);
    }

}