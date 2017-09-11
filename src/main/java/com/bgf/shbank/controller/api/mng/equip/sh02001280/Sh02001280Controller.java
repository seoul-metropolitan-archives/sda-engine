package com.bgf.shbank.controller.api.mng.equip.sh02001280;

import com.bgf.shbank.controller.view.ExcelView;
import com.bgf.shbank.domain.mng.equip.sh02001280.Sh02001280;
import com.bgf.shbank.domain.mng.equip.sh02001280.Sh02001280Service;
import com.bgf.shbank.domain.mng.equip.sh02001280.Sh02001280VO;
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
@RequestMapping(value = "/api/v1/mng/equip/sh02001280")
public class Sh02001280Controller extends BaseController {

    @Inject
    private Sh02001280Service sh02001280Service;

    @GetMapping
    public Responses.PageResponse list(Pageable pageable, RequestParams<Sh02001280> requestParams) {
        Page<Sh02001280> pages = sh02001280Service.find(pageable, requestParams);
        return Responses.PageResponse.of(Sh02001280VO.of(pages.getContent()), pages);
    }

    @PutMapping
    @PostMapping
    public ApiResponse save(@RequestBody List<Sh02001280> request) {
        sh02001280Service.save(request);
        return ok();
    }

    @GetMapping("/download")
    public View download(RequestParams<Sh02001280VO> requestParams, Model model) {

        List<Sh02001280> resultList = sh02001280Service.find(requestParams);

        List<Sh02001280VO> voList = Sh02001280VO.of(resultList);

        Map<String, Object> params = Maps.newHashMap();
        params.put("vo", voList);

        model.addAttribute("vo", voList);
        model.addAttribute("txId", "Sh02001280");
        model.addAttribute("fileName", "기타작업정보-" + DateUtils.getNow("yyyyMMdd_HHmmss"));

        ExcelView view = new ExcelView();
        return view;
    }
}