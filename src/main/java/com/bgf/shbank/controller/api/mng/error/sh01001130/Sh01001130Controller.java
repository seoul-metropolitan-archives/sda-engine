package com.bgf.shbank.controller.api.mng.error.sh01001130;

import com.bgf.shbank.domain.mng.error.sh01001130.Sh01001130;
import com.bgf.shbank.domain.mng.error.sh01001130.Sh01001130Service;
import com.bgf.shbank.domain.mng.error.sh01001130.Sh01001130VO;
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
@RequestMapping(value = "/api/v1/mng/error/sh01001130")
public class Sh01001130Controller extends BaseController {

    @Inject
    private Sh01001130Service sh01001130Service;

    @GetMapping
    public Responses.PageResponse list(Pageable pageable, RequestParams<Sh01001130VO> requestParams) {
        Page<Sh01001130> pages = sh01001130Service.find(pageable, requestParams);
        return Responses.PageResponse.of(Sh01001130VO.of(pages.getContent()), pages);
    }

    @GetMapping(params = "details")
    public Sh01001130VO details(RequestParams<Sh01001130VO> requestParams) {
        return sh01001130Service.findOne(requestParams);
    }

    @PutMapping
    @PostMapping
    public ApiResponse save(@RequestBody List<Sh01001130> request) {
        sh01001130Service.save(request);
        return ok();
    }
}