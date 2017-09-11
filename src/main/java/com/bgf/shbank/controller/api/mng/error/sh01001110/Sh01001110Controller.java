package com.bgf.shbank.controller.api.mng.error.sh01001110;

import com.bgf.shbank.domain.mng.error.sh01001110.Sh01001110;
import com.bgf.shbank.domain.mng.error.sh01001110.Sh01001110Service;
import com.bgf.shbank.domain.mng.error.sh01001110.Sh01001110VO;
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
@RequestMapping(value = "/api/v1/mng/error/sh01001110")
public class Sh01001110Controller extends BaseController {

    @Inject
    private Sh01001110Service sh01001110Service;

    @GetMapping
    public Responses.PageResponse list(Pageable pageable, RequestParams<Sh01001110VO> requestParams) {
        Page<Sh01001110> pages = sh01001110Service.find(pageable, requestParams);
        return Responses.PageResponse.of(Sh01001110VO.of(pages.getContent()), pages);
    }

    @GetMapping(params = "details")
    public Sh01001110VO details(RequestParams<Sh01001110VO> requestParams) {
        return sh01001110Service.findOne(requestParams);
    }

    @GetMapping(params = "resentlyDetails")
    public Sh01001110VO resentlyDetails(RequestParams<Sh01001110VO> requestParams) {
        return sh01001110Service.findOneByResentlyErrorDatetime(requestParams);
    }

    @PutMapping
    @PostMapping
    public ApiResponse save(@RequestBody List<Sh01001110> request) {
        sh01001110Service.save(request);
        return ok();
    }
}
