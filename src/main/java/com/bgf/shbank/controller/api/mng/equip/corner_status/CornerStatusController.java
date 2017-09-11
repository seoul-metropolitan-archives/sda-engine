package com.bgf.shbank.controller.api.mng.equip.corner_status;

import com.bgf.shbank.controller.view.ExcelView;
import com.bgf.shbank.domain.mng.equip.corner_status.CornerStatus;
import com.bgf.shbank.domain.mng.equip.corner_status.CornerStatusService;
import com.bgf.shbank.domain.mng.equip.corner_status.CornerStatusVO;
import com.bgf.shbank.utils.DateUtils;
import com.bgf.shbank.utils.ModelMapperUtils;
import com.google.common.collect.Maps;
import io.onsemiro.controller.BaseController;
import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.api.response.Responses;
import io.onsemiro.core.parameter.RequestParams;
import io.onsemiro.utils.CommonCodeUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.View;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/v1/mng/equip/corner_status")
public class CornerStatusController extends BaseController {

    @Inject
    private CornerStatusService cornerStatusService;

    @GetMapping
    public Responses.PageResponse list(Pageable pageable, RequestParams<CornerStatus> requestParams) {
        Page<CornerStatus> pages = cornerStatusService.find(pageable, requestParams);
        return Responses.PageResponse.of(CornerStatusVO.of(pages.getContent()), pages);
    }

    @PutMapping
    @PostMapping
    public ApiResponse save(@RequestBody CornerStatusVO cornerStatusVO) {

        CornerStatus cornerStatus = ModelMapperUtils.map(cornerStatusVO, CornerStatus.class);
        if (cornerStatusVO.getJisaCode() == null || cornerStatusVO.getJisaCode().isEmpty()) {
            cornerStatus.setJisaCode(CommonCodeUtils.getCode("JISA_CODE", cornerStatusVO.getJisaCodeName()));
        }


        cornerStatusService.save(cornerStatus);

        return ok();
    }


    @GetMapping("/download")
    public View download(RequestParams<CornerStatusVO> requestParams, Model model) {

        List<CornerStatus> resultList = cornerStatusService.find(requestParams);

        List<CornerStatusVO> voList = CornerStatusVO.of(resultList);

        Map<String, Object> params = Maps.newHashMap();
        params.put("vo", voList);

        model.addAttribute("vo", voList);
        model.addAttribute("txId", "CornerStatus");
        model.addAttribute("fileName", "코너목록-" + DateUtils.getNow("yyyyMMdd_HHmmss"));

        ExcelView view = new ExcelView();
        return view;
    }
}