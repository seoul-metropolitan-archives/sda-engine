package rmsoft.ams.seoul.ad.ad008.controller;

import io.onsemiro.controller.BaseController;
import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.api.response.Responses;
import io.onsemiro.core.parameter.RequestParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rmsoft.ams.seoul.ad.ad008.service.Ad008Service;
import rmsoft.ams.seoul.ad.ad008.vo.Ad008;
import rmsoft.ams.seoul.ad.ad008.vo.Ad00801VO;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/ad/ad008")
public class Ad008Controller extends BaseController {

    @Autowired
    private Ad008Service service;

    /**
     * Search entity type responses . list response.
     *
     * @param param the param
     * @return the responses . list response
     */
    @RequestMapping("/searchList")
    public Responses.ListResponse searchList(RequestParams<Ad00801VO> param) {
        return Responses.ListResponse.of(service.searchList(param));
    }

    @RequestMapping(value = "")
    public ApiResponse save(@RequestBody List<Ad008> request) {
        service.save(request);
        return ok();
    }
}