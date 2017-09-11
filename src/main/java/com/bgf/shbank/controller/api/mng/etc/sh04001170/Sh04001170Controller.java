package com.bgf.shbank.controller.api.mng.etc.sh04001170;

import com.bgf.shbank.controller.view.ExcelView;
import com.bgf.shbank.domain.mng.etc.sh04001170.Sh04001170;
import com.bgf.shbank.domain.mng.etc.sh04001170.Sh04001170Service;
import com.bgf.shbank.domain.mng.etc.sh04001170.Sh04001170VO;
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
@RequestMapping(value = "/api/v1/mng/etc/sh04001170")
public class Sh04001170Controller extends BaseController {

    @Inject
    private Sh04001170Service sh04001170Service;

    @GetMapping
    public Responses.PageResponse list(Pageable pageable, RequestParams<Sh04001170> requestParams) {
        Page<Sh04001170> pages = sh04001170Service.find(pageable, requestParams);
        return Responses.PageResponse.of(Sh04001170VO.of(pages.getContent()), pages);
    }

    @PutMapping
    @PostMapping
    public ApiResponse save(@RequestBody List<Sh04001170> request) {
        sh04001170Service.save(request);
        return ok();
    }

    @GetMapping("/download")
    public View download(RequestParams<Sh04001170VO> requestParams, Model model) {

        List<Sh04001170> resultList = sh04001170Service.find(requestParams);

        List<Sh04001170VO> voList = Sh04001170VO.of(resultList);

        Map<String, Object> params = Maps.newHashMap();
        params.put("vo", voList);

        model.addAttribute("vo", voList);
        model.addAttribute("txId", "Sh04001170");
        model.addAttribute("fileName", "기기정보전문-" + DateUtils.getNow("yyyyMMdd_HHmmss"));

        ExcelView view = new ExcelView();
        return view;
    }
}