package com.bgf.shbank.controller.api.mng.sla.sh_sla_e0;

import com.bgf.shbank.domain.mng.sla.sh_sla_e0.ShSlaE0;
import com.bgf.shbank.domain.mng.sla.sh_sla_e0.ShSlaE0Service;
import com.bgf.shbank.domain.mng.sla.sh_sla_e0.ShSlaE0VO;
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
@RequestMapping(value = "/api/v1/mng/sla/sh_sla_e0")
public class ShSlaE0Controller extends BaseController {

    @Inject
    private ShSlaE0Service shSlaE0Service;

    @GetMapping
    public Responses.PageResponse list(Pageable pageable, RequestParams<ShSlaE0VO> requestParams) {
        Page<ShSlaE0> pages = shSlaE0Service.find(pageable, requestParams);
        return Responses.PageResponse.of(ShSlaE0VO.of(pages.getContent()), pages);
    }

    @PutMapping
    @PostMapping
    public ApiResponse save(@RequestBody List<ShSlaE0VO> requestParams) {
        List<ShSlaE0> request = Lists.newArrayList();
        ShSlaE0 shSlaE0 = null;

        for (ShSlaE0VO shSlaE0VO : requestParams) {
            shSlaE0 = ModelMapperUtils.map(shSlaE0VO, ShSlaE0.class);
            request.add(shSlaE0);
        }
        shSlaE0Service.save(request);
        return ok();
    }
}