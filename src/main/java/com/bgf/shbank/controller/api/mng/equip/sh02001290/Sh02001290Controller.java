package com.bgf.shbank.controller.api.mng.equip.sh02001290;

import com.bgf.shbank.controller.view.ExcelView;
import com.bgf.shbank.domain.mng.equip.sh02001290.Sh02001290;
import com.bgf.shbank.domain.mng.equip.sh02001290.Sh02001290Service;
import com.bgf.shbank.domain.mng.equip.sh02001290.Sh02001290VO;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.View;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/v1/mng/equip/sh02001290")
public class Sh02001290Controller extends BaseController {

    @Inject
    private Sh02001290Service sh02001290Service;

    @GetMapping
    public Responses.PageResponse list(Pageable pageable, RequestParams<Sh02001290> requestParams) {
        Page<Sh02001290> pages = sh02001290Service.find(pageable, requestParams);
        return Responses.PageResponse.of(Sh02001290VO.of(pages.getContent()), pages);
    }

    @PutMapping
    @PostMapping
    public ApiResponse save(@RequestBody Sh02001290VO sh02001290VO) {

        ApiResponse apiResponse = sh02001290Service.sendAndReceive(sh02001290VO);

        if (apiResponse.getStatus() == -1) {
            throw new ApiException(ApiStatus.SYSTEM_ERROR, apiResponse.getMessage());
        }

        return apiResponse;
    }

    @GetMapping("/download")
    public View download(RequestParams<Sh02001290VO> requestParams, Model model) {

        List<Sh02001290> resultList = sh02001290Service.find(requestParams);

        List<Sh02001290VO> voList = Sh02001290VO.of(resultList);

        Map<String, Object> params = Maps.newHashMap();
        params.put("vo", voList);

        model.addAttribute("vo", voList);
        model.addAttribute("txId", "Sh02001290");
        model.addAttribute("fileName", "일회성비용청구-" + DateUtils.getNow("yyyyMMdd_HHmmss"));

        ExcelView view = new ExcelView();
        return view;
    }
}