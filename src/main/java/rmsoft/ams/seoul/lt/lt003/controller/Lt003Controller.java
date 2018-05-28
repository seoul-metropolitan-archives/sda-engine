package rmsoft.ams.seoul.lt.lt003.controller;

import io.onsemiro.controller.BaseController;
import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.api.response.Responses;
import io.onsemiro.core.parameter.RequestParams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import rmsoft.ams.seoul.lt.lt003.service.Lt003Service;
import rmsoft.ams.seoul.lt.lt003.vo.Lt003;
import rmsoft.ams.seoul.lt.lt003.vo.Lt003VO;

import javax.inject.Inject;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/lt/lt003")
public class Lt003Controller extends BaseController {

    @Inject
    private Lt003Service lt003Service;

    @GetMapping
    public Responses.PageResponse list(Pageable pageable, RequestParams<Lt003> requestParams) {
        Page<Lt003> pages = lt003Service.find(pageable, requestParams.getString("filter", ""));
        return Responses.PageResponse.of(Lt003VO.of(pages.getContent()), pages);
    }

    @PutMapping
    @PostMapping
    public ApiResponse save(@RequestBody List<Lt003> request) {
        lt003Service.save(request);
        return ok();
    }
}