package com.bgf.shbank.controller.api.mng.equip.sh02001160;

import com.bgf.shbank.controller.view.ExcelView;
import com.bgf.shbank.domain.mng.equip.sh02001160.Sh02001160;
import com.bgf.shbank.domain.mng.equip.sh02001160.Sh02001160Service;
import com.bgf.shbank.domain.mng.equip.sh02001160.Sh02001160VO;
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
@RequestMapping(value = "/api/v1/mng/equip/sh02001160")
public class Sh02001160Controller extends BaseController {

    @Inject
    private Sh02001160Service sh02001160Service;

    @GetMapping
    public Responses.PageResponse list(Pageable pageable, RequestParams<Sh02001160> requestParams) {
        Page<Sh02001160> pages = sh02001160Service.find(pageable, requestParams);
        return Responses.PageResponse.of(Sh02001160VO.of(pages.getContent()), pages);
    }

    @PutMapping
    @PostMapping
    public ApiResponse save(@RequestBody Sh02001160VO sh02001160VO) {
        Sh02001160 sh02001160 = ModelMapperUtils.map(sh02001160VO, Sh02001160.class);


        String hireFee = sh02001160VO.getHireFee();
        if (!StringUtils.isEmpty(hireFee) && hireFee.contains(",")) {
            hireFee = StringUtils.replace(hireFee, ",", "");
            sh02001160.setHireFee(hireFee);
        }

        sh02001160Service.save(sh02001160);
        return ok();
    }

    @GetMapping("/download")
    public View download(RequestParams<Sh02001160VO> requestParams, Model model) {

        List<Sh02001160> resultList = sh02001160Service.find(requestParams);

        List<Sh02001160VO> voList = Sh02001160VO.of(resultList);

        Map<String, Object> params = Maps.newHashMap();
        params.put("vo", voList);

        model.addAttribute("vo", voList);
        model.addAttribute("txId", "Sh02001160");
        model.addAttribute("fileName", "신규시설물등록통보이력-" + DateUtils.getNow("yyyyMMdd_HHmmss"));

        ExcelView view = new ExcelView();
        return view;
    }
}