package com.bgf.shbank.controller.api.mng.equip.sh02001110;

import com.bgf.shbank.controller.view.ExcelView;
import com.bgf.shbank.domain.mng.equip.sh02001110.Sh02001110;
import com.bgf.shbank.domain.mng.equip.sh02001110.Sh02001110Service;
import com.bgf.shbank.domain.mng.equip.sh02001110.Sh02001110VO;
import com.bgf.shbank.utils.DateUtils;
import com.bgf.shbank.utils.ModelMapperUtils;
import com.google.common.collect.Maps;
import io.onsemiro.controller.BaseController;
import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.api.response.Responses;
import io.onsemiro.core.parameter.RequestParams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.View;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/v1/mng/equip/sh02001110")
public class Sh02001110Controller extends BaseController {

    @Inject
    private Sh02001110Service sh02001110Service;

    @GetMapping
    public Responses.PageResponse list(Pageable pageable, RequestParams<Sh02001110> requestParams) {
        Page<Sh02001110> pages = sh02001110Service.find(pageable, requestParams);
        return Responses.PageResponse.of(Sh02001110VO.of(pages.getContent()), pages);
    }

    @PutMapping
    @PostMapping
    public ApiResponse save(@RequestBody Sh02001110VO sh02001110VO) {

        Sh02001110 sh02001110 = ModelMapperUtils.map(sh02001110VO, Sh02001110.class);

        String serviceFee = sh02001110VO.getServiceFee();
        if (!StringUtils.isEmpty(serviceFee) && serviceFee.contains(",")) {
            serviceFee = StringUtils.replace(serviceFee, ",", "");
            sh02001110.setServiceFee(serviceFee);
        }

        sh02001110Service.save(sh02001110);

        return ok();
    }

    @GetMapping(params = "details")
        public Sh02001110VO details(RequestParams<Sh02001110VO> requestParams) {
        return sh02001110Service.findOne(requestParams);
    }

    @GetMapping("/download")
    public View download(RequestParams<Sh02001110VO> requestParams, Model model) {

        List<Sh02001110> resultList = sh02001110Service.find(requestParams);

        List<Sh02001110VO> voList = Sh02001110VO.of(resultList);

        Map<String, Object> params = Maps.newHashMap();
        params.put("vo", voList);

        model.addAttribute("vo", voList);
        model.addAttribute("txId", "Sh02001110");
        model.addAttribute("fileName", "신규코너정보-" + DateUtils.getNow("yyyyMMdd_HHmmss"));

        ExcelView view = new ExcelView();
        return view;
    }
}