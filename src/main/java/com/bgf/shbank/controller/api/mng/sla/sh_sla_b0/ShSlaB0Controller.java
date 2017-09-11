package com.bgf.shbank.controller.api.mng.sla.sh_sla_b0;

import com.bgf.shbank.domain.mng.sla.sh_sla_b0.ShSlaB0;
import com.bgf.shbank.domain.mng.sla.sh_sla_b0.ShSlaB0Service;
import com.bgf.shbank.domain.mng.sla.sh_sla_b0.ShSlaB0VO;
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
@RequestMapping(value = "/api/v1/mng/sla/sh_sla_b0")
public class ShSlaB0Controller extends BaseController {

    @Inject
    private ShSlaB0Service shSlaB0Service;

    @GetMapping
    public Responses.PageResponse list(Pageable pageable, RequestParams<ShSlaB0VO> requestParams) {
        Page<ShSlaB0> pages = shSlaB0Service.find(pageable, requestParams);
        return Responses.PageResponse.of(ShSlaB0VO.of(pages.getContent()), pages);
    }

    @PutMapping
    @PostMapping
    public ApiResponse save(@RequestBody List<ShSlaB0VO> requestParams) {
        List<ShSlaB0> request = Lists.newArrayList();
        ShSlaB0 shSlaB0 = null;

        for (ShSlaB0VO shSlaB0VO : requestParams) {
            shSlaB0 = ModelMapperUtils.map(shSlaB0VO, ShSlaB0.class);
            request.add(shSlaB0);
        }
        shSlaB0Service.save(request);
        return ok();
    }
}