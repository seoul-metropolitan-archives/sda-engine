package com.bgf.shbank.controller.api.mng.sla.sh_sla_80;

import com.bgf.shbank.domain.mng.sla.sh_sla_80.ShSla80;
import com.bgf.shbank.domain.mng.sla.sh_sla_80.ShSla80Service;
import com.bgf.shbank.domain.mng.sla.sh_sla_80.ShSla80VO;
import com.bgf.shbank.utils.ModelMapperUtils;
import com.google.common.collect.Lists;
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
@RequestMapping(value = "/api/v1/mng/sla/sh_sla_80")
public class ShSla80Controller extends BaseController {
    @Inject
    private ShSla80Service shSla80Service;

    @GetMapping
    public Responses.PageResponse list(Pageable pageable, RequestParams<ShSla80VO> requestParams) {
        Page<ShSla80> pages = shSla80Service.find(pageable, requestParams);
        return Responses.PageResponse.of(ShSla80VO.of(pages.getContent()), pages);
    }

    @PutMapping
    @PostMapping
    public ApiResponse save(@RequestBody List<ShSla80VO> requestParams) {
        List<ShSla80> request = Lists.newArrayList();
        ShSla80 shSla80 = null;

        for (ShSla80VO shSla80VO : requestParams) {
            shSla80 = ModelMapperUtils.map(shSla80VO, ShSla80.class);
            request.add(shSla80);
        }
        shSla80Service.save(request);
        return ok();
    }
}