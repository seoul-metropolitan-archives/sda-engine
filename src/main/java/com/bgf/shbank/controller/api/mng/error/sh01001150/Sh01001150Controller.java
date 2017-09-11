package com.bgf.shbank.controller.api.mng.error.sh01001150;

import com.bgf.shbank.domain.mng.error.sh01001150.Sh01001150;
import com.bgf.shbank.domain.mng.error.sh01001150.Sh01001150Service;
import com.bgf.shbank.domain.mng.error.sh01001150.Sh01001150VO;
import io.onsemiro.controller.BaseController;
import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.api.response.Responses;
import io.onsemiro.core.parameter.RequestParams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/mng/error/sh01001150")
public class Sh01001150Controller extends BaseController {

    @Inject
    private Sh01001150Service sh01001150Service;

    @GetMapping
    public Responses.PageResponse list(Pageable pageable, RequestParams<Sh01001150VO> requestParams) {
        Page<Sh01001150> pages = sh01001150Service.find(pageable, requestParams);
        return Responses.PageResponse.of(Sh01001150VO.of(pages.getContent()), pages);
    }

    @GetMapping(params = "details")
    public Sh01001150VO details(RequestParams<Sh01001150VO> requestParams) {
        return sh01001150Service.findOne(requestParams);
    }

    @PutMapping
    @PostMapping
    public ApiResponse save(@RequestBody List<Sh01001150> request) {
        sh01001150Service.save(request);
        return ok();
    }
}