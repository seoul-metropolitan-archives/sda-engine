package rmsoft.ams.seoul.ac.ac012.controller;

import io.onsemiro.controller.BaseController;
import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.api.response.Responses;
import io.onsemiro.core.parameter.RequestParams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import rmsoft.ams.seoul.ac.ac012.service.Ac012Service;
import rmsoft.ams.seoul.ac.ac012.vo.Ac012;
import rmsoft.ams.seoul.ac.ac012.vo.Ac012VO;

import javax.inject.Inject;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/ac/ac012")
public class Ac012Controller extends BaseController {

    @Inject
    private Ac012Service ac012Service;

    @GetMapping
    public Responses.PageResponse list(Pageable pageable, RequestParams<Ac012> requestParams) {
        Page<Ac012> pages = ac012Service.find(pageable, requestParams.getString("filter", ""));
        return Responses.PageResponse.of(Ac012VO.of(pages.getContent()), pages);
    }

    @PutMapping
    @PostMapping
    public ApiResponse save(@RequestBody List<Ac012> request) {
        ac012Service.save(request);
        return ok();
    }
}