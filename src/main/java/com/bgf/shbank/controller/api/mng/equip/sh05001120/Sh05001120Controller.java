package com.bgf.shbank.controller.api.mng.equip.sh05001120;

import com.bgf.shbank.domain.mng.equip.sh05001120.Sh05001120;
import com.bgf.shbank.domain.mng.equip.sh05001120.Sh05001120Service;
import com.bgf.shbank.domain.mng.equip.sh05001120.Sh05001120VO;
import com.bgf.shbank.utils.ModelMapperUtils;
import io.onsemiro.controller.BaseController;
import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.api.response.Responses;
import io.onsemiro.core.parameter.RequestParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

@RestController
@RequestMapping(value = "/api/v1/mng/equip/sh05001120")
public class Sh05001120Controller extends BaseController {

    @Inject
    private Sh05001120Service sh05001120Service;

    @Value("${server.url}")
    private String url;

    @Value("${server.port}")
    private String port;

    public String buildUrl() {
        return "http://" + url + ":" + port;
    }

    @GetMapping
    public Responses.PageResponse list(Pageable pageable, RequestParams<Sh05001120> requestParams) {
        Page<Sh05001120> pages = sh05001120Service.find(pageable, requestParams.getString("filter", ""));
        return Responses.PageResponse.of(Sh05001120VO.of(pages.getContent()), pages);
    }

    @GetMapping(params = "details")
    public Sh05001120VO details(RequestParams<Sh05001120VO> requestParams) {
        return sh05001120Service.findOne(requestParams);
    }

    @PutMapping
    @PostMapping
    public ApiResponse save(@RequestBody Sh05001120VO sh05001120VO) {
        Sh05001120 sh05001120 = ModelMapperUtils.map(sh05001120VO, Sh05001120.class);
        sh05001120.setPhotoUrl(buildUrl());
        sh05001120Service.save(sh05001120);
        return ok();
    }
}