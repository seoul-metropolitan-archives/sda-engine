package rmsoft.ams.seoul.ad.ad008.controller;

import io.onsemiro.controller.BaseController;
import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.api.response.Responses;
import io.onsemiro.core.parameter.RequestParams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import rmsoft.ams.seoul.ad.ad008.service.Ad008Service;
import rmsoft.ams.seoul.ad.ad008.vo.Ad008;
import rmsoft.ams.seoul.ad.ad008.vo.Ad008VO;

import javax.inject.Inject;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/ad/ad008")
public class Ad008Controller extends BaseController {

    @Inject
    private Ad008Service ad008Service;

    @GetMapping
    public Responses.PageResponse list(Pageable pageable, RequestParams<Ad008> requestParams) {
        Page<Ad008> pages = ad008Service.find(pageable, requestParams.getString("filter", ""));
        return Responses.PageResponse.of(Ad008VO.of(pages.getContent()), pages);
    }

    @PutMapping
    @PostMapping
    public ApiResponse save(@RequestBody List<Ad008> request) {
        ad008Service.save(request);
        return ok();
    }
}