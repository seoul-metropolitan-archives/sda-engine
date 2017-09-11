package com.bgf.shbank.controller.api.mng.cash.sh03001120;

import com.bgf.shbank.controller.view.ExcelView;
import com.bgf.shbank.domain.mng.cash.sh03001120.Sh03001120;
import com.bgf.shbank.domain.mng.cash.sh03001120.Sh03001120Service;
import com.bgf.shbank.domain.mng.cash.sh03001120.Sh03001120VO;
import com.bgf.shbank.utils.DateUtils;
import com.google.common.collect.Maps;
import io.onsemiro.controller.BaseController;
import io.onsemiro.core.api.ApiException;
import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.api.response.Responses;
import io.onsemiro.core.code.ApiStatus;
import io.onsemiro.core.parameter.RequestParams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.View;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/v1/mng/cash/sh03001120")
public class Sh03001120Controller extends BaseController {

    @Inject
    private Sh03001120Service sh03001120Service;

    @GetMapping
    public Responses.PageResponse list(Pageable pageable, RequestParams<Sh03001120VO> requestParams) {
        Page<Sh03001120> pages = sh03001120Service.find(pageable, requestParams);
        return Responses.PageResponse.of(Sh03001120VO.of(pages.getContent()), pages);
    }

    @GetMapping("/allList")
    public Responses.PageResponse allList(Pageable pageable, RequestParams<Sh03001120VO> requestParams) {
        Page<Sh03001120> pages = sh03001120Service.findAll(pageable, requestParams);
        return Responses.PageResponse.of(Sh03001120VO.of(pages.getContent()), pages);
    }

    @GetMapping(params = "stextSend")
    public ApiResponse findAll(Sh03001120VO sh03001120VO) {
        sh03001120VO.setReferDate(StringUtils.replace(sh03001120VO.getReferDate(), "-", ""));
        ApiResponse apiResponse =  sh03001120Service.findOne(sh03001120VO);

        if (apiResponse.getStatus() == -1) {
            throw new ApiException(ApiStatus.SYSTEM_ERROR, apiResponse.getMessage());
        }

        return apiResponse;
    }

    @GetMapping("/download")
    public View download(RequestParams<Sh03001120VO> requestParams, Model model) {

        List<Sh03001120> resultList = sh03001120Service.findExcel(requestParams);

        List<Sh03001120VO> voList = Sh03001120VO.of(resultList);

        Map<String, Object> params = Maps.newHashMap();
        params.put("vo", voList);

        model.addAttribute("vo", voList);
        model.addAttribute("txId", "Sh03001120");
        model.addAttribute("fileName", "거래내역조회-" + DateUtils.getNow("yyyyMMdd_HHmmss"));

        return new ExcelView();
    }
}