package com.bgf.shbank.controller.api.mng.equip.sh05001140;

import com.bgf.shbank.domain.mng.equip.sh05001140.Sh05001140;
import com.bgf.shbank.domain.mng.equip.sh05001140.Sh05001140Service;
import com.bgf.shbank.domain.mng.equip.sh05001140.Sh05001140VO;
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
@RequestMapping(value = "/api/v1/mng/equip/sh05001140")
public class Sh05001140Controller extends BaseController {

    @Inject
    private Sh05001140Service sh05001140Service;

    @Value("${server.url}")
    private String url;

    @Value("${server.port}")
    private String port;

    public String buildUrl() {
        return "http://" + url + ":" + port + "/api/v1/mng/equip/corner_manage/preview?id=";
    }

    @GetMapping
    public Responses.PageResponse list(Pageable pageable, RequestParams<Sh05001140> requestParams) {
        Page<Sh05001140> pages = sh05001140Service.find(pageable, requestParams.getString("filter", ""));
        return Responses.PageResponse.of(Sh05001140VO.of(pages.getContent()), pages);
    }

    @GetMapping(params = "details")
    public Sh05001140VO details(RequestParams<Sh05001140VO> requestParams) {
        return sh05001140Service.findOne(requestParams);
    }

    @PutMapping
    @PostMapping
    public ApiResponse save(@RequestBody Sh05001140VO sh05001140VO) {
        Sh05001140 sh05001140 = ModelMapperUtils.map(sh05001140VO, Sh05001140.class);
        sh05001140.setPhotoUrl(buildUrl());
        sh05001140Service.save(sh05001140);
        return ok();
    }
}