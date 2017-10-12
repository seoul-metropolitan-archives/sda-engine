package rmsoft.ams.seoul.ac.ac003.controller;

import io.onsemiro.controller.BaseController;
import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.api.response.Responses;
import io.onsemiro.core.parameter.RequestParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import rmsoft.ams.seoul.ac.ac003.service.Ac003Service;
import rmsoft.ams.seoul.ac.ac003.vo.Ac003VO_01;
import rmsoft.ams.seoul.common.domain.AcUser;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/ac003/")
public class Ac003Controller extends BaseController {

    @Autowired
    private Ac003Service ac003Service;

    @GetMapping("/01/list")
    public Responses.PageResponse list(Pageable pageable, RequestParams<AcUser> requestParams) {
        Page<AcUser> pages = ac003Service.find(pageable, requestParams.getString("filter", ""));
        return Responses.PageResponse.of(Ac003VO_01.of(pages.getContent()), pages);
    }

    @GetMapping("/01/details")
    public Ac003VO_01 details(RequestParams<Ac003VO_01> requestParams) {
        return ac003Service.findOne(requestParams);
    }

    @PutMapping
    @PostMapping
    public ApiResponse save(@RequestBody List<AcUser> request) {
        ac003Service.save(request);
        return ok();
    }
}