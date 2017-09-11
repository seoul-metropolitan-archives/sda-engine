package com.bgf.shbank.controller.api.mng.sla.sh_sla_a0;

import com.bgf.shbank.domain.mng.sla.sh_sla_a0.ShSlaA0;
import com.bgf.shbank.domain.mng.sla.sh_sla_a0.ShSlaA0Service;
import com.bgf.shbank.domain.mng.sla.sh_sla_a0.ShSlaA0VO;
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
@RequestMapping(value = "/api/v1/mng/sla/sh_sla_a0")
public class ShSlaA0Controller extends BaseController {
    @Inject
    private ShSlaA0Service shSlaA0Service;

    @GetMapping
    public Responses.PageResponse list(Pageable pageable, RequestParams<ShSlaA0VO> requestParams) {
        Page<ShSlaA0> pages = shSlaA0Service.find(pageable, requestParams);
        return Responses.PageResponse.of(ShSlaA0VO.of(pages.getContent()), pages);
    }

}