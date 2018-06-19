package rmsoft.ams.seoul.lt.lt004.controller;

import io.onsemiro.controller.BaseController;
import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.api.response.Responses;
import io.onsemiro.core.parameter.RequestParams;
import org.springframework.web.bind.annotation.*;
import rmsoft.ams.seoul.lt.lt004.service.Lt004Service;
import rmsoft.ams.seoul.lt.lt004.vo.Lt00401VO;

import javax.inject.Inject;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/lt/lt004")
public class Lt004Controller extends BaseController {

    @Inject
    private Lt004Service lt004Service;

    /**
     * Search entity type responses . list response.
     *
     * @param param the param
     * @return the responses . list response
     */
    @GetMapping("/searchList")
    public Responses.ListResponse searchList(RequestParams<Lt00401VO> param) {
        return Responses.ListResponse.of(lt004Service.searchList(param));
    }

    /**
     * Save entity type api response.
     *
     * @param itemList the lt 00401 vo list
     * @return the api response
     */
    @PutMapping(value = "/saveItems")
    @PostMapping
    public ApiResponse saveItems(@RequestBody List<Lt00401VO> itemList) {

        return lt004Service.saveItems(itemList);
    }

}