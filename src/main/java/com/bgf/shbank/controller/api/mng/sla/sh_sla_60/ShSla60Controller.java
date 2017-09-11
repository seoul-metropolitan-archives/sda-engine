package com.bgf.shbank.controller.api.mng.sla.sh_sla_60;

import com.bgf.shbank.domain.mng.sla.sh_sla_60.ShSla60;
import com.bgf.shbank.domain.mng.sla.sh_sla_60.ShSla60Service;
import com.bgf.shbank.domain.mng.sla.sh_sla_60.ShSla60VO;
import io.onsemiro.controller.BaseController;
import io.onsemiro.core.api.response.Responses;
import io.onsemiro.core.parameter.RequestParams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

@RestController
@RequestMapping(value = "/api/v1/mng/sla/sh_sla_60")
public class ShSla60Controller extends BaseController {
    @Inject
    private ShSla60Service shSla60Service;

    @GetMapping
    public Responses.PageResponse list(Pageable pageable, RequestParams<ShSla60VO> requestParams) {
        Page<ShSla60> pages = shSla60Service.find(pageable, requestParams);
        return Responses.PageResponse.of(ShSla60VO.of(pages.getContent()), pages);
    }

}