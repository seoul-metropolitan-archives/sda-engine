package com.bgf.shbank.controller.api.mng.equip.facility_status;

import com.bgf.shbank.controller.view.ExcelView;
import com.bgf.shbank.domain.mng.equip.facility_status.FacilityStatus;
import com.bgf.shbank.domain.mng.equip.facility_status.FacilityStatusService;
import com.bgf.shbank.domain.mng.equip.facility_status.FacilityStatusVO;
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
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.View;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/v1/mng/equip/facility_status")
public class FacilityStatusController extends BaseController {

    @Inject
    private FacilityStatusService facilityStatusService;

    @GetMapping
    public Responses.PageResponse list(Pageable pageable, RequestParams<FacilityStatus> requestParams) {
        Page<FacilityStatus> pages = facilityStatusService.find(pageable, requestParams);
        return Responses.PageResponse.of(FacilityStatusVO.of(pages.getContent()), pages);
    }

    @PutMapping
    @PostMapping
    public ApiResponse save(@RequestBody FacilityStatusVO facilityStatusVO) {

        FacilityStatus facilityStatus = ModelMapperUtils.map(facilityStatusVO, FacilityStatus.class);

        if (facilityStatusVO.getJisaCode() == null || facilityStatusVO.getJisaCode().isEmpty()) {
            facilityStatus.setJisaCode(CommonCodeUtils.getCode("JISA_CODE", facilityStatusVO.getJisaCodeName()));
        }
        String hireFee = facilityStatusVO.getHireFee();
        if (!StringUtils.isEmpty(hireFee) && hireFee.contains(",")) {
            hireFee = StringUtils.replace(hireFee, ",", "");
            facilityStatus.setHireFee(hireFee);
        }

        facilityStatusService.save(facilityStatus);
        return ok();
    }


    @GetMapping("/download")
    public View download(RequestParams<FacilityStatusVO> requestParams, Model model) {

        List<FacilityStatus> resultList = facilityStatusService.find(requestParams);

        List<FacilityStatusVO> voList = FacilityStatusVO.of(resultList);

        Map<String, Object> params = Maps.newHashMap();
        params.put("vo", voList);

        model.addAttribute("vo", voList);
        model.addAttribute("txId", "facility_status");
        model.addAttribute("fileName", "시설물목록-" + DateUtils.getNow("yyyyMMdd_HHmmss"));

        ExcelView view = new ExcelView();
        return view;
    }
}