package com.bgf.shbank.controller.api.mng.sla.sh_sla_50;

import com.bgf.shbank.domain.mng.sla.sh_sla_50.ShSla50;
import com.bgf.shbank.domain.mng.sla.sh_sla_50.ShSla50Service;
import com.bgf.shbank.domain.mng.sla.sh_sla_50.ShSla50VO;
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
@RequestMapping(value = "/api/v1/mng/sla/sh_sla_50")
public class ShSla50Controller extends BaseController {
    @Inject
    private ShSla50Service shSla50Service;

    @GetMapping
    public Responses.PageResponse list(Pageable pageable, RequestParams<ShSla50VO> requestParams) {
        Page<ShSla50> pages = shSla50Service.find(pageable, requestParams);
        return Responses.PageResponse.of(ShSla50VO.of(pages.getContent()), pages);
    }

}