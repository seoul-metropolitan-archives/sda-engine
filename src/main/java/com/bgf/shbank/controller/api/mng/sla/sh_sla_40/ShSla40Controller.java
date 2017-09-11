package com.bgf.shbank.controller.api.mng.sla.sh_sla_40;

import com.bgf.shbank.domain.mng.sla.sh_sla_40.ShSla40;
import com.bgf.shbank.domain.mng.sla.sh_sla_40.ShSla40Service;
import com.bgf.shbank.domain.mng.sla.sh_sla_40.ShSla40VO;
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
@RequestMapping(value = "/api/v1/mng/sla/sh_sla_40")
public class ShSla40Controller extends BaseController {

    @Inject
    private ShSla40Service shSla40Service;

    @GetMapping
    public Responses.PageResponse list(Pageable pageable, RequestParams<ShSla40VO> requestParams) {
        Page<ShSla40> pages = shSla40Service.find(pageable, requestParams);
        return Responses.PageResponse.of(ShSla40VO.of(pages.getContent()), pages);
    }
}