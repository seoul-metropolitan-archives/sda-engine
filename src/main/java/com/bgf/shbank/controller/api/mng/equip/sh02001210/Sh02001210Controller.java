package com.bgf.shbank.controller.api.mng.equip.sh02001210;

import com.bgf.shbank.controller.view.ExcelView;
import com.bgf.shbank.domain.mng.equip.sh02001210.Sh02001210;
import com.bgf.shbank.domain.mng.equip.sh02001210.Sh02001210Service;
import com.bgf.shbank.domain.mng.equip.sh02001210.Sh02001210VO;
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
@RequestMapping(value = "/api/v1/mng/equip/sh02001210")
public class Sh02001210Controller extends BaseController {

    @Inject
    private Sh02001210Service sh02001210Service;

    @GetMapping
    public Responses.PageResponse list(Pageable pageable, RequestParams<Sh02001210> requestParams) {
        Page<Sh02001210> pages = sh02001210Service.find(pageable, requestParams);
        return Responses.PageResponse.of(Sh02001210VO.of(pages.getContent()), pages);
    }

    @PutMapping
    @PostMapping
    public ApiResponse save(@RequestBody List<Sh02001210> request) {
        sh02001210Service.save(request);
        return ok();
    }

    @GetMapping("/download")
    public View download(RequestParams<Sh02001210VO> requestParams, Model model) {

        List<Sh02001210> resultList = sh02001210Service.find(requestParams);

        List<Sh02001210VO> voList = Sh02001210VO.of(resultList);

        Map<String, Object> params = Maps.newHashMap();
        params.put("vo", voList);

        model.addAttribute("vo", voList);
        model.addAttribute("txId", "Sh02001210");
        model.addAttribute("fileName", "기기변경통보이력-" + DateUtils.getNow("yyyyMMdd_HHmmss"));

        ExcelView view = new ExcelView();
        return view;
    }
}