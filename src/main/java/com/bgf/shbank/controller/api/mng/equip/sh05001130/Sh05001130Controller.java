package com.bgf.shbank.controller.api.mng.equip.sh05001130;

import com.bgf.shbank.domain.mng.equip.sh05001130.Sh05001130;
import com.bgf.shbank.domain.mng.equip.sh05001130.Sh05001130Service;
import com.bgf.shbank.domain.mng.equip.sh05001130.Sh05001130VO;
import com.bgf.shbank.utils.ModelMapperUtils;
import io.onsemiro.controller.BaseController;
import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.api.response.Responses;
import io.onsemiro.core.parameter.RequestParams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

@RestController
@RequestMapping(value = "/api/v1/mng/equip/sh05001130")
public class Sh05001130Controller extends BaseController {

    @Inject
    private Sh05001130Service sh05001130Service;

    @GetMapping
    public Responses.PageResponse list(Pageable pageable, RequestParams<Sh05001130> requestParams) {
        Page<Sh05001130> pages = sh05001130Service.find(pageable, requestParams.getString("filter", ""));
        return Responses.PageResponse.of(Sh05001130VO.of(pages.getContent()), pages);
    }

    @GetMapping(params = "details")
    public Sh05001130VO details(RequestParams<Sh05001130VO> requestParams) {
        return sh05001130Service.findOne(requestParams);
    }

    @PutMapping
    @PostMapping
    public ApiResponse save(@RequestBody Sh05001130VO sh05001130VO) {
        Sh05001130 sh05001130 = ModelMapperUtils.map(sh05001130VO, Sh05001130.class);
        sh05001130Service.save(sh05001130);
        return ok();
    }
}