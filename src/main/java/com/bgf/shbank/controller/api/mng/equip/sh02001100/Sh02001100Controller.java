package com.bgf.shbank.controller.api.mng.equip.sh02001100;

import com.bgf.shbank.controller.view.ExcelView;
import com.bgf.shbank.domain.mng.equip.sh02001100.Sh02001100;
import com.bgf.shbank.domain.mng.equip.sh02001100.Sh02001100Service;
import com.bgf.shbank.domain.mng.equip.sh02001100.Sh02001100VO;
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
@RequestMapping(value = "/api/v1/mng/equip/sh02001100")
public class Sh02001100Controller extends BaseController {

    @Inject
    private Sh02001100Service sh02001100Service;

    @GetMapping
    public Responses.PageResponse list(Pageable pageable, RequestParams<Sh02001100> requestParams) {
        Page<Sh02001100> pages = sh02001100Service.find(pageable, requestParams);
        return Responses.PageResponse.of(Sh02001100VO.of(pages.getContent()), pages);
    }

    @PutMapping
    @PostMapping
    public ApiResponse save(@RequestBody List<Sh02001100> request) {
        sh02001100Service.save(request);
        return ok();
    }


    @GetMapping("/download")
    public View download(RequestParams<Sh02001100VO> requestParams, Model model) {

        List<Sh02001100> resultList = sh02001100Service.find(requestParams);

        List<Sh02001100VO> voList = Sh02001100VO.of(resultList);

        Map<String, Object> params = Maps.newHashMap();
        params.put("vo", voList);

        model.addAttribute("vo", voList);
        model.addAttribute("txId", "sh02001100");
        model.addAttribute("fileName", "작업일정통보조회-" + DateUtils.getNow("yyyyMMdd_HHmmss"));

        ExcelView view = new ExcelView();
        return view;
    }
}