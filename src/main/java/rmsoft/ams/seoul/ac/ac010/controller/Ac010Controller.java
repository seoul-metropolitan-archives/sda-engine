package rmsoft.ams.seoul.ac.ac010.controller;

import io.onsemiro.controller.BaseController;
import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.api.response.Responses;
import io.onsemiro.core.parameter.RequestParams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import rmsoft.ams.seoul.ac.ac010.service.Ac010Service;
import rmsoft.ams.seoul.ac.ac010.vo.Ac010;
import rmsoft.ams.seoul.ac.ac010.vo.Ac010VO;

import javax.inject.Inject;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/ac/ac010")
public class Ac010Controller extends BaseController {

    @Inject
    private Ac010Service ac010Service;

    @GetMapping
    public Responses.PageResponse list(Pageable pageable, RequestParams<Ac010> requestParams) {
        Page<Ac010> pages = ac010Service.find(pageable, requestParams.getString("filter", ""));
        return Responses.PageResponse.of(Ac010VO.of(pages.getContent()), pages);
    }

    @PutMapping
    @PostMapping
    public ApiResponse save(@RequestBody List<Ac010> request) {
        ac010Service.save(request);
        return ok();
    }
}