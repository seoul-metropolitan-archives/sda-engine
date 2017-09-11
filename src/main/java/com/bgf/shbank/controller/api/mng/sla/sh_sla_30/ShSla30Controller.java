package com.bgf.shbank.controller.api.mng.sla.sh_sla_30;

import com.bgf.shbank.domain.mng.sla.sh_sla_30.ShSla30;
import com.bgf.shbank.domain.mng.sla.sh_sla_30.ShSla30Service;
import com.bgf.shbank.domain.mng.sla.sh_sla_30.ShSla30VO;
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
@RequestMapping(value = "/api/v1/mng/sla/sh_sla_30")
public class ShSla30Controller extends BaseController {
    @Inject
    private ShSla30Service shSla30Service;

    @GetMapping
    public Responses.PageResponse list(Pageable pageable, RequestParams<ShSla30VO> requestParams) {
        Page<ShSla30> pages = shSla30Service.find(pageable, requestParams);
        return Responses.PageResponse.of(ShSla30VO.of(pages.getContent()), pages);
    }

    @PutMapping
    @PostMapping
    public ApiResponse save(@RequestBody List<ShSla30VO> requestParams) {
        List<ShSla30> request = Lists.newArrayList();
        ShSla30 shSla30 = null;

        for (ShSla30VO shSla30VO : requestParams) {
            shSla30 = ModelMapperUtils.map(shSla30VO, ShSla30.class);

            request.add(shSla30);
        }
        shSla30Service.save(request);
        return ok();
    }
}