package com.bgf.shbank.controller.api.mng.etc.sh04001130;

import com.bgf.shbank.controller.view.ExcelView;
import com.bgf.shbank.domain.mng.etc.sh04001130.Sh04001130;
import com.bgf.shbank.domain.mng.etc.sh04001130.Sh04001130Service;
import com.bgf.shbank.domain.mng.etc.sh04001130.Sh04001130VO;
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
@RequestMapping(value = "/api/v1/mng/etc/sh04001130")
public class Sh04001130Controller extends BaseController {

    @Inject
    private Sh04001130Service sh04001130Service;

    @GetMapping
    public Responses.PageResponse list(Pageable pageable, RequestParams<Sh04001130> requestParams) {
        Page<Sh04001130> pages = sh04001130Service.find(pageable, requestParams);
        return Responses.PageResponse.of(Sh04001130VO.of(pages.getContent()), pages);
    }

    @PutMapping
    @PostMapping
    public ApiResponse save(@RequestBody Sh04001130VO sh04001130VO) {

        ApiResponse apiResponse = sh04001130Service.sendAndReceive(sh04001130VO);

        if (apiResponse.getStatus() == -1) {
            throw new ApiException(ApiStatus.SYSTEM_ERROR, apiResponse.getMessage());
        }

        return apiResponse;
    }

    @GetMapping("/download")
    public View download(RequestParams<Sh04001130VO> requestParams, Model model) {

        List<Sh04001130> resultList = sh04001130Service.find(requestParams);

        List<Sh04001130VO> voList = Sh04001130VO.of(resultList);

        Map<String, Object> params = Maps.newHashMap();
        params.put("vo", voList);

        model.addAttribute("vo", voList);
        model.addAttribute("txId", "Sh04001130");
        model.addAttribute("fileName", "용역료관리-" + DateUtils.getNow("yyyyMMdd_HHmmss"));

        ExcelView view = new ExcelView();
        return view;
    }
}