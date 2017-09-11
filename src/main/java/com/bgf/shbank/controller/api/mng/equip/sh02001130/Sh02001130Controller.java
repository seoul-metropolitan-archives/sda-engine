package com.bgf.shbank.controller.api.mng.equip.sh02001130;

import com.bgf.shbank.controller.view.ExcelView;
import com.bgf.shbank.domain.mng.equip.sh02001130.Sh02001130;
import com.bgf.shbank.domain.mng.equip.sh02001130.Sh02001130Service;
import com.bgf.shbank.domain.mng.equip.sh02001130.Sh02001130VO;
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
@RequestMapping(value = "/api/v1/mng/equip/sh02001130")
public class Sh02001130Controller extends BaseController {

    @Inject
    private Sh02001130Service sh02001130Service;

    @GetMapping
    public Responses.PageResponse list(Pageable pageable, RequestParams<Sh02001130> requestParams) {
        Page<Sh02001130> pages = sh02001130Service.find(pageable, requestParams);
        return Responses.PageResponse.of(Sh02001130VO.of(pages.getContent()), pages);
    }

    @PutMapping
    @PostMapping
    public ApiResponse save(@RequestBody Sh02001130VO sh02001130VO) {

        Sh02001130 sh02001130 = ModelMapperUtils.map(sh02001130VO, Sh02001130.class);

        String terminalHireFee = sh02001130VO.getTerminalHireFee();
        if (!StringUtils.isEmpty(terminalHireFee) && terminalHireFee.contains(",")) {
            terminalHireFee = StringUtils.replace(terminalHireFee, ",", "");
            sh02001130.setTerminalHireFee(terminalHireFee);
        }
        String modelCode = sh02001130VO.getModelCode();
        sh02001130.setTerminalCorpCode(modelCode.substring(0, 1));
        sh02001130.setModelCode(modelCode.substring(1, 4));

        sh02001130Service.save(sh02001130);

        return ok();
    }

    @GetMapping("/download")
    public View download(RequestParams<Sh02001130VO> requestParams, Model model) {

        List<Sh02001130> resultList = sh02001130Service.find(requestParams);

        List<Sh02001130VO> voList = Sh02001130VO.of(resultList);

        Map<String, Object> params = Maps.newHashMap();
        params.put("vo", voList);

        model.addAttribute("vo", voList);
        model.addAttribute("txId", "Sh02001130");
        model.addAttribute("fileName", "기기신규등록통보이력-" + DateUtils.getNow("yyyyMMdd_HHmmss"));

        ExcelView view = new ExcelView();
        return view;
    }
}