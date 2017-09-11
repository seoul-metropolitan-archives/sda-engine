package com.bgf.shbank.controller.api.mng.error.sh01001140;

import com.bgf.shbank.domain.mng.error.sh01001140.Sh01001140;
import com.bgf.shbank.domain.mng.error.sh01001140.Sh01001140Service;
import com.bgf.shbank.domain.mng.error.sh01001140.Sh01001140VO;
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
@RequestMapping(value = "/api/v1/mng/error/sh01001140")
public class Sh01001140Controller extends BaseController {

    @Inject
    private Sh01001140Service sh01001140Service;

    @GetMapping
    public Responses.PageResponse list(Pageable pageable, RequestParams<Sh01001140VO> requestParams) {
        Page<Sh01001140> pages = sh01001140Service.find(pageable, requestParams);
        return Responses.PageResponse.of(Sh01001140VO.of(pages.getContent()), pages);
    }

    @GetMapping(params = "details")
    public Sh01001140VO details(RequestParams<Sh01001140VO> requestParams) {
        return sh01001140Service.findOne(requestParams);
    }

    @PutMapping
    @PostMapping
    public ApiResponse save(@RequestBody List<Sh01001140> request) {
        sh01001140Service.save(request);
        return ok();
    }
}