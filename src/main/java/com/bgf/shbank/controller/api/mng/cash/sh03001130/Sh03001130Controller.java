package com.bgf.shbank.controller.api.mng.cash.sh03001130;

import com.bgf.shbank.domain.mng.cash.sh03001130.Sh03001130;
import com.bgf.shbank.domain.mng.cash.sh03001130.Sh03001130Service;
import com.bgf.shbank.domain.mng.cash.sh03001130.Sh03001130VO;
import io.onsemiro.controller.BaseController;
import io.onsemiro.core.api.ApiException;
import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.api.response.Responses;
import io.onsemiro.core.code.ApiStatus;
import io.onsemiro.core.parameter.RequestParams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/mng/cash/sh03001130")
public class Sh03001130Controller extends BaseController {

    @Inject
    private Sh03001130Service sh03001130Service;

    @GetMapping
    public Responses.PageResponse list(Pageable pageable, RequestParams<Sh03001130> requestParams) {
        Page<Sh03001130> pages = sh03001130Service.find(pageable, requestParams);
        return Responses.PageResponse.of(Sh03001130VO.of(pages.getContent()), pages);
    }

    @GetMapping(params = "findPage")
    public Responses.PageResponse findPage(Pageable pageable, RequestParams<Sh03001130VO> requestParams) {
        Page<Sh03001130> pages = sh03001130Service.findPage(pageable, requestParams);
        return Responses.PageResponse.of(Sh03001130VO.of(pages.getContent()), pages);
    }

    @GetMapping(params = "findCloseAmt")
    public Sh03001130VO findPage(RequestParams<Sh03001130VO> requestParams) {
        return sh03001130Service.findCloseAmt(requestParams);
    }

    @GetMapping(params = "stext")
    public ApiResponse stextSend(Sh03001130VO sh03001130VO) {
        sh03001130VO.setReqDate(StringUtils.replace(sh03001130VO.getReqDate(), "-", ""));
        sh03001130VO.setSafeNo("7000");

        ApiResponse apiResponse = sh03001130Service.stextSend(sh03001130VO);
        if (apiResponse.getStatus() == -1) {
            throw new ApiException(ApiStatus.SYSTEM_ERROR, apiResponse.getMessage());
        }

        return apiResponse;
    }

    @GetMapping(params = "findAll")
    public ApiResponse findAll(Sh03001130VO requestParams) {
        sh03001130Service.findAll(requestParams);
        return ok();
    }

    @PutMapping
    @PostMapping
    public ApiResponse save(@RequestBody List<Sh03001130> request) {
        sh03001130Service.save(request);
        return ok();
    }
}