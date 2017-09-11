package com.bgf.shbank.controller.api.mng.cash.sh03001170;

import com.bgf.shbank.controller.view.ExcelView;
import com.bgf.shbank.domain.mng.cash.sh03001170.Sh03001170;
import com.bgf.shbank.domain.mng.cash.sh03001170.Sh03001170Service;
import com.bgf.shbank.domain.mng.cash.sh03001170.Sh03001170VO;
import com.bgf.shbank.utils.DateUtils;
import com.google.common.collect.Maps;
import io.onsemiro.controller.BaseController;
import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.api.response.Responses;
import io.onsemiro.core.parameter.RequestParams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.View;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/v1/mng/cash/sh03001170")
public class Sh03001170Controller extends BaseController {

    @Inject
    private Sh03001170Service sh03001170Service;

    @GetMapping
    public Responses.PageResponse list(Pageable pageable, RequestParams<Sh03001170> requestParams) {
        Page<Sh03001170> pages = sh03001170Service.find(pageable, requestParams);
        return Responses.PageResponse.of(Sh03001170VO.of(pages.getContent()), pages);
    }

    @GetMapping(params = "findAll")
    public Responses.PageResponse findAll(Pageable pageable, RequestParams<Sh03001170VO> requestParams) {
        Page<Sh03001170> pages = sh03001170Service.findAll(pageable, requestParams);
        return Responses.PageResponse.of(Sh03001170VO.of(pages.getContent()), pages);
    }

    @PutMapping
    @PostMapping
    public ApiResponse save(@RequestBody List<Sh03001170> request) {
        sh03001170Service.save(request);
        return ok();
    }

    @GetMapping("/download")
    public View download(RequestParams<Sh03001170VO> requestParams, Model model) {

        List<Sh03001170> resultList = sh03001170Service.findExcel(requestParams);

        List<Sh03001170VO> voList = Sh03001170VO.of(resultList);

        Map<String, Object> params = Maps.newHashMap();
        params.put("vo", voList);

        model.addAttribute("vo", voList);
        model.addAttribute("txId", "sh03001170");
        model.addAttribute("fileName", "은행현송예측통보 이력-" + DateUtils.getNow("yyyyMMdd_HHmmss"));

        return new ExcelView();
    }
}