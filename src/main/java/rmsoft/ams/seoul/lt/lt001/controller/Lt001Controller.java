package rmsoft.ams.seoul.lt.lt001.controller;

import io.onsemiro.controller.BaseController;
import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.api.response.Responses;
import io.onsemiro.core.parameter.RequestParams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import rmsoft.ams.seoul.lt.lt001.service.Lt001Service;
import rmsoft.ams.seoul.lt.lt001.vo.Lt001;
import rmsoft.ams.seoul.lt.lt001.vo.Lt001VO;

import javax.inject.Inject;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/lt/lt001")
public class Lt001Controller extends BaseController {

    @Inject
    private Lt001Service lt001Service;

    @GetMapping
    public Responses.PageResponse list(Pageable pageable, RequestParams<Lt001> requestParams) {
        Page<Lt001> pages = lt001Service.find(pageable, requestParams.getString("filter", ""));
        return Responses.PageResponse.of(Lt001VO.of(pages.getContent()), pages);
    }

    @PutMapping
    @PostMapping
    public ApiResponse save(@RequestBody List<Lt001> request) {
        lt001Service.save(request);
        return ok();
    }
}