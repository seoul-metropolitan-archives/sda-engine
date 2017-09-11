package com.bgf.shbank.controller.api.mng.etc.sh04001120;

import com.bgf.shbank.controller.view.ExcelView;
import com.bgf.shbank.domain.mng.etc.sh04001120.Sh04001120;
import com.bgf.shbank.domain.mng.etc.sh04001120.Sh04001120Service;
import com.bgf.shbank.domain.mng.etc.sh04001120.Sh04001120VO;
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
@RequestMapping(value = "/api/v1/mng/etc/sh04001120")
public class Sh04001120Controller extends BaseController {

    @Inject
    private Sh04001120Service sh04001120Service;

    @GetMapping
    public Responses.PageResponse list(Pageable pageable, RequestParams<Sh04001120> requestParams) {
        Page<Sh04001120> pages = sh04001120Service.find(pageable, requestParams.getString("filter", ""));
        return Responses.PageResponse.of(Sh04001120VO.of(pages.getContent()), pages);
    }

    @PutMapping
    @PostMapping
    public ApiResponse save(@RequestBody List<Sh04001120> request) {
        sh04001120Service.save(request);
        return ok();
    }

    @GetMapping("/download")
    public View download(RequestParams<Sh04001120VO> requestParams, Model model) {

        List<Sh04001120> resultList = sh04001120Service.find(requestParams);

        List<Sh04001120VO> voList = Sh04001120VO.of(resultList);

        Map<String, Object> params = Maps.newHashMap();
        params.put("vo", voList);

        model.addAttribute("vo", voList);
        model.addAttribute("txId", "Sh04001120");
        model.addAttribute("fileName", "요원정보관리-" + DateUtils.getNow("yyyyMMdd_HHmmss"));

        ExcelView view = new ExcelView();
        return view;
    }
}