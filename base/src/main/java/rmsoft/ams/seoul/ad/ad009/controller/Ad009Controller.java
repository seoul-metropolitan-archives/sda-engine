package rmsoft.ams.seoul.ad.ad009.controller;

import io.onsemiro.controller.BaseController;
import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.api.response.Responses;
import io.onsemiro.core.parameter.RequestParams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import rmsoft.ams.seoul.ad.ad009.service.Ad009Service;
import rmsoft.ams.seoul.ad.ad009.vo.Ad009;
import rmsoft.ams.seoul.ad.ad009.vo.Ad009VO;

import javax.inject.Inject;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/ad/ad009")
public class Ad009Controller extends BaseController {

    @Inject
    private Ad009Service ad009Service;

    @GetMapping
    public Responses.PageResponse list(Pageable pageable, RequestParams<Ad009> requestParams) {
        Page<Ad009> pages = ad009Service.find(pageable, requestParams.getString("filter", ""));
        return Responses.PageResponse.of(Ad009VO.of(pages.getContent()), pages);
    }

    @PutMapping
    @PostMapping
    public ApiResponse save(@RequestBody List<Ad009> request) {
        ad009Service.save(request);
        return ok();
    }
}