package rmsoft.ams.seoul.ad.ad007.controller;

import io.onsemiro.controller.BaseController;
import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.api.response.Responses;
import io.onsemiro.core.parameter.RequestParams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import rmsoft.ams.seoul.ad.ad007.service.Ad007Service;
import rmsoft.ams.seoul.ad.ad007.vo.Ad007;
import rmsoft.ams.seoul.ad.ad007.vo.Ad007VO;

import javax.inject.Inject;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/ad/ad007")
public class Ad007Controller extends BaseController {

    @Inject
    private Ad007Service ad007Service;

    @GetMapping
    public Responses.PageResponse list(Pageable pageable, RequestParams<Ad007> requestParams) {
        Page<Ad007> pages = ad007Service.find(pageable, requestParams.getString("filter", ""));
        return Responses.PageResponse.of(Ad007VO.of(pages.getContent()), pages);
    }

    @PutMapping
    @PostMapping
    public ApiResponse save(@RequestBody List<Ad007> request) {
        ad007Service.save(request);
        return ok();
    }
}