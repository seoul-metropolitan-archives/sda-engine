package rmsoft.ams.seoul.ac.ac003.controller;

import io.onsemiro.controller.BaseController;
import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.api.response.Responses;
import io.onsemiro.core.parameter.RequestParams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import rmsoft.ams.seoul.ac.ac003.domain.Ac003;
import rmsoft.ams.seoul.ac.ac003.domain.Ac003VO;
import rmsoft.ams.seoul.ac.ac003.service.Ac003Service;

import javax.inject.Inject;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/ac003/")
public class Ac003Controller extends BaseController {

    @Inject
    private Ac003Service ac003Service;

    @GetMapping("/list")
    public Responses.PageResponse list(Pageable pageable, RequestParams<Ac003> requestParams) {
        Page<Ac003> pages = ac003Service.find(pageable, requestParams.getString("filter", ""));
        return Responses.PageResponse.of(Ac003VO.of(pages.getContent()), pages);
    }

    @GetMapping("/details")
    public Ac003VO details(RequestParams<Ac003VO> requestParams) {
        return ac003Service.findOne(requestParams);
    }

    @PutMapping
    @PostMapping
    public ApiResponse save(@RequestBody List<Ac003> request) {
        ac003Service.save(request);
        return ok();
    }
}