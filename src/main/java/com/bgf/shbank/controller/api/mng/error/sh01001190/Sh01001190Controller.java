package com.bgf.shbank.controller.api.mng.error.sh01001190;

import com.bgf.shbank.domain.mng.error.sh01001190.Sh01001190;
import com.bgf.shbank.domain.mng.error.sh01001190.Sh01001190Service;
import com.bgf.shbank.domain.mng.error.sh01001190.Sh01001190VO;
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
@RequestMapping(value = "/api/v1/mng/error/sh01001190")
public class Sh01001190Controller extends BaseController {

    @Inject
    private Sh01001190Service sh01001190Service;

    @GetMapping
    public Responses.PageResponse list(Pageable pageable, RequestParams<Sh01001190> requestParams) {
        Page<Sh01001190> pages = sh01001190Service.find(pageable, requestParams.getString("filter", ""));
        return Responses.PageResponse.of(Sh01001190VO.of(pages.getContent()), pages);
    }

    @PutMapping
    @PostMapping
    public ApiResponse save(@RequestBody List<Sh01001190> request) {
        sh01001190Service.save(request);
        return ok();
    }
}