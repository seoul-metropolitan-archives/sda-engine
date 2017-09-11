package com.bgf.shbank.controller.api.mng.equip.sh05001110;

import com.bgf.shbank.domain.mng.equip.sh05001110.Sh05001110;
import com.bgf.shbank.domain.mng.equip.sh05001110.Sh05001110Service;
import com.bgf.shbank.domain.mng.equip.sh05001110.Sh05001110VO;
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
@RequestMapping(value = "/api/v1/mng/equip/sh05001110")
public class Sh05001110Controller extends BaseController {

    @Inject
    private Sh05001110Service sh05001110Service;

    @GetMapping
    public Responses.PageResponse list(Pageable pageable, RequestParams<Sh05001110> requestParams) {
        Page<Sh05001110> pages = sh05001110Service.find(pageable, requestParams.getString("filter", ""));
        return Responses.PageResponse.of(Sh05001110VO.of(pages.getContent()), pages);
    }

    @GetMapping(params = "details")
    public Sh05001110VO details(RequestParams<Sh05001110VO> requestParams) {
        return sh05001110Service.findOne(requestParams);
    }

    @PutMapping
    @PostMapping
    public ApiResponse save(@RequestBody Sh05001110VO sh05001110VO) {
        Sh05001110 sh05001110 = ModelMapperUtils.map(sh05001110VO, Sh05001110.class);
        sh05001110Service.save(sh05001110);
        return ok();
    }
}