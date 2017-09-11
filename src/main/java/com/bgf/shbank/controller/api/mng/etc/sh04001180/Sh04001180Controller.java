package com.bgf.shbank.controller.api.mng.etc.sh04001180;

import com.bgf.shbank.controller.view.ExcelView;
import com.bgf.shbank.domain.mng.etc.sh04001180.Sh04001180;
import com.bgf.shbank.domain.mng.etc.sh04001180.Sh04001180Service;
import com.bgf.shbank.domain.mng.etc.sh04001180.Sh04001180VO;
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
@RequestMapping(value = "/api/v1/mng/etc/sh04001180")
public class Sh04001180Controller extends BaseController {

    @Inject
    private Sh04001180Service sh04001180Service;

    @GetMapping
    public Responses.PageResponse list(Pageable pageable, RequestParams<Sh04001180> requestParams) {
        Page<Sh04001180> pages = sh04001180Service.find(pageable, requestParams);
        return Responses.PageResponse.of(Sh04001180VO.of(pages.getContent()), pages);
    }

    @PutMapping
    @PostMapping
    public ApiResponse save(@RequestBody List<Sh04001180> request) {
        sh04001180Service.save(request);
        return ok();
    }

    @GetMapping("/download")
    public View download(RequestParams<Sh04001180VO> requestParams, Model model) {

        List<Sh04001180> resultList = sh04001180Service.find(requestParams);

        List<Sh04001180VO> voList = Sh04001180VO.of(resultList);

        Map<String, Object> params = Maps.newHashMap();
        params.put("vo", voList);

        model.addAttribute("vo", voList);
        model.addAttribute("txId", "Sh04001180");
        model.addAttribute("fileName", "CRT번호등록/변경-" + DateUtils.getNow("yyyyMMdd_HHmmss"));

        ExcelView view = new ExcelView();
        return view;
    }
}