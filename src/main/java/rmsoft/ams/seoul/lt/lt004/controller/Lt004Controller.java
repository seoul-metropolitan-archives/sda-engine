package rmsoft.ams.seoul.lt.lt004.controller;

import io.onsemiro.controller.BaseController;
import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.api.response.Responses;
import io.onsemiro.core.parameter.RequestParams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import rmsoft.ams.seoul.lt.lt004.service.Lt004Service;
import rmsoft.ams.seoul.lt.lt004.vo.Lt004;
import rmsoft.ams.seoul.lt.lt004.vo.Lt004VO;

import javax.inject.Inject;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/lt/lt004")
public class Lt004Controller extends BaseController {

    @Inject
    private Lt004Service lt004Service;

    @GetMapping
    public Responses.PageResponse list(Pageable pageable, RequestParams<Lt004> requestParams) {
        Page<Lt004> pages = lt004Service.find(pageable, requestParams.getString("filter", ""));
        return Responses.PageResponse.of(Lt004VO.of(pages.getContent()), pages);
    }

    @PutMapping
    @PostMapping
    public ApiResponse save(@RequestBody List<Lt004> request) {
        lt004Service.save(request);
        return ok();
    }
}