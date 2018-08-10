package rmsoft.ams.seoul.lt.lt001.controller;

import io.onsemiro.controller.BaseController;
import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.api.response.Responses;
import io.onsemiro.core.parameter.RequestParams;
import org.springframework.web.bind.annotation.*;
import rmsoft.ams.seoul.lt.lt001.service.Lt001Service;
import rmsoft.ams.seoul.lt.lt001.vo.Lt00101VO;

import javax.inject.Inject;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/lt/lt001")
public class Lt001Controller extends BaseController {

    @Inject
    private Lt001Service lt001Service;

    /**
     * Search entity type responses . list response.
     *
     * @param param the param
     * @return the responses . list response
     */
    @GetMapping("/searchList")
    public Responses.ListResponse searchList(RequestParams<Lt00101VO> param) {
        return Responses.ListResponse.of(lt001Service.searchList(param));
    }

    /**
     * Save entity type api response.
     *
     * @param itemList the ad 00501 vo list
     * @return the api response
     */
    @PutMapping(value = "/saveItems")
    @PostMapping
    public ApiResponse saveItems(@RequestBody List<Lt00101VO> itemList) {
        return lt001Service.saveItems(itemList);
    }

}