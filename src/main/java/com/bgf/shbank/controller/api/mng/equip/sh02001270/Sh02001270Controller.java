package com.bgf.shbank.controller.api.mng.equip.sh02001270;

import com.bgf.shbank.controller.view.ExcelView;
import com.bgf.shbank.domain.mng.equip.sh02001270.Sh02001270;
import com.bgf.shbank.domain.mng.equip.sh02001270.Sh02001270Service;
import com.bgf.shbank.domain.mng.equip.sh02001270.Sh02001270VO;
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
@RequestMapping(value = "/api/v1/mng/equip/sh02001270")
public class Sh02001270Controller extends BaseController {

    @Inject
    private Sh02001270Service sh02001270Service;

    @GetMapping
    public Responses.PageResponse list(Pageable pageable, RequestParams<Sh02001270> requestParams) {
        Page<Sh02001270> pages = sh02001270Service.find(pageable, requestParams);
        return Responses.PageResponse.of(Sh02001270VO.of(pages.getContent()), pages);
    }

    @PutMapping
    @PostMapping
    public ApiResponse save(@RequestBody List<Sh02001270> request) {
        sh02001270Service.save(request);
        return ok();
    }

    @GetMapping("/download")
    public View download(RequestParams<Sh02001270VO> requestParams, Model model) {

        List<Sh02001270> resultList = sh02001270Service.find(requestParams);

        List<Sh02001270VO> voList = Sh02001270VO.of(resultList);

        Map<String, Object> params = Maps.newHashMap();
        params.put("vo", voList);

        model.addAttribute("vo", voList);
        model.addAttribute("txId", "Sh02001270");
        model.addAttribute("fileName", "코너상태변경통보-" + DateUtils.getNow("yyyyMMdd_HHmmss"));

        ExcelView view = new ExcelView();
        return view;
    }
}