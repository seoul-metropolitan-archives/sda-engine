package com.bgf.shbank.controller.api.mng.cash.sh03001210;

import com.bgf.shbank.domain.mng.cash.sh03001210.Sh03001210;
import com.bgf.shbank.domain.mng.cash.sh03001210.Sh03001210Service;
import com.bgf.shbank.domain.mng.cash.sh03001210.Sh03001210VO;
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
@RequestMapping(value = "/api/v1/mng/cash/sh03001210")
public class Sh03001210Controller extends BaseController {

    @Inject
    private Sh03001210Service sh03001210Service;

    @GetMapping
    public Responses.PageResponse list(Pageable pageable, RequestParams<Sh03001210> requestParams) {
        Page<Sh03001210> pages = sh03001210Service.find(pageable, requestParams.getString("filter", ""));
        return Responses.PageResponse.of(Sh03001210VO.of(pages.getContent()), pages);
    }

    @PutMapping
    @PostMapping
    public ApiResponse save(@RequestBody List<Sh03001210> request) {
        sh03001210Service.save(request);
        return ok();
    }
}