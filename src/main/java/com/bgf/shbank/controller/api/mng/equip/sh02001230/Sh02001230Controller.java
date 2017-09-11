package com.bgf.shbank.controller.api.mng.equip.sh02001230;

import com.bgf.shbank.controller.view.ExcelView;
import com.bgf.shbank.domain.mng.equip.sh02001230.Sh02001230;
import com.bgf.shbank.domain.mng.equip.sh02001230.Sh02001230Service;
import com.bgf.shbank.domain.mng.equip.sh02001230.Sh02001230VO;
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
@RequestMapping(value = "/api/v1/mng/equip/sh02001230")
public class Sh02001230Controller extends BaseController {

    @Inject
    private Sh02001230Service sh02001230Service;

    @GetMapping
    public Responses.PageResponse list(Pageable pageable, RequestParams<Sh02001230> requestParams) {
        Page<Sh02001230> pages = sh02001230Service.find(pageable, requestParams);
        return Responses.PageResponse.of(Sh02001230VO.of(pages.getContent()), pages);
    }

    @PutMapping
    @PostMapping
    public ApiResponse save(@RequestBody Sh02001230VO sh02001230VO) {

        ApiResponse apiResponse = sh02001230Service.sendAndReceive(sh02001230VO);

        if (apiResponse.getStatus() == -1) {
            throw new ApiException(ApiStatus.SYSTEM_ERROR, apiResponse.getMessage());
        }

        return apiResponse;
    }

    @GetMapping("/download")
    public View download(RequestParams<Sh02001230VO> requestParams, Model model) {

        List<Sh02001230> resultList = sh02001230Service.find(requestParams);

        List<Sh02001230VO> voList = Sh02001230VO.of(resultList);

        Map<String, Object> params = Maps.newHashMap();
        params.put("vo", voList);

        model.addAttribute("vo", voList);
        model.addAttribute("txId", "Sh02001230");
        model.addAttribute("fileName", "일련번호변경 전송이력 목록-" + DateUtils.getNow("yyyyMMdd_HHmmss"));

        ExcelView view = new ExcelView();
        return view;
    }
}