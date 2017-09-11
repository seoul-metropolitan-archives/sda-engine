package com.bgf.shbank.controller.api.mng.cash.sh03001180;

import com.bgf.shbank.controller.view.ExcelView;
import com.bgf.shbank.domain.mng.cash.sh03001180.Sh03001180;
import com.bgf.shbank.domain.mng.cash.sh03001180.Sh03001180ExcelVO;
import com.bgf.shbank.domain.mng.cash.sh03001180.Sh03001180Service;
import com.bgf.shbank.domain.mng.cash.sh03001180.Sh03001180VO;
import com.bgf.shbank.utils.ModelMapperUtils;
import io.onsemiro.controller.BaseController;
import io.onsemiro.core.api.ApiException;
import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.api.response.Responses;
import io.onsemiro.core.code.ApiStatus;
import io.onsemiro.core.parameter.RequestParams;
import io.onsemiro.utils.CommonCodeUtils;
import io.onsemiro.utils.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.View;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/mng/cash/sh03001180")
public class Sh03001180Controller extends BaseController {

    @Inject
    private Sh03001180Service sh03001180Service;

    @GetMapping
    public Responses.PageResponse list(Pageable pageable, RequestParams<Sh03001180> requestParams) {
        Page<Sh03001180> pages = sh03001180Service.find(pageable, requestParams.getString("filter", ""));
        return Responses.PageResponse.of(Sh03001180VO.of(pages.getContent()), pages);
    }

    @GetMapping(params = "findAll")
    public Responses.PageResponse findAll(Pageable pageable, RequestParams<Sh03001180VO> requestParams) {
        Page<Sh03001180> pages = sh03001180Service.findAll(pageable, requestParams);
        return Responses.PageResponse.of(Sh03001180VO.of(pages.getContent()), pages);
    }

    @PutMapping
    @PostMapping
    public ApiResponse save(@RequestBody Sh03001180VO sh03001180VO) {
        LocalDateTime now = LocalDateTime.now();
        sh03001180VO.setTxId(DateUtils.convertToString(now, "yyyy-MM-dd HH:mm:ss"));
        sh03001180VO.setCashSendingAmt(sh03001180VO.getCashSendingAmt().replace(",",""));
        sh03001180VO.setCash50kSendingAmt(sh03001180VO.getCash50kSendingAmt().replace(",",""));

        Sh03001180 sh03001180 = ModelMapperUtils.map(sh03001180VO, Sh03001180.class);
        sh03001180Service.save(sh03001180);
        return ok();
    }

    @GetMapping(params = "stextSend")
    public ApiResponse stextSend(Sh03001180VO sh03001180VO) {
        LocalDateTime now = LocalDateTime.now();
        sh03001180VO.setTxId(DateUtils.convertToString(now, "yyyy-MM-dd HH:mm:ss"));
        sh03001180VO.setCashSendingAmt(sh03001180VO.getCashSendingAmt().replace(",",""));
        sh03001180VO.setCash50kSendingAmt(sh03001180VO.getCash50kSendingAmt().replace(",",""));

        if(StringUtils.isEmpty(sh03001180VO.getAcceptEnable())){
            sh03001180VO.setAcceptEnable("");
        }

        ApiResponse apiResponse = sh03001180Service.stextSend(sh03001180VO);

        if (apiResponse.getStatus() == -1) {
            throw new ApiException(ApiStatus.SYSTEM_ERROR, apiResponse.getMessage());
        }

        return apiResponse;
    }

    @GetMapping("/download")
    public View download(RequestParams<Sh03001180ExcelVO> requestParams, Model model) {

        List<Sh03001180ExcelVO> voList = sh03001180Service.findExcel(requestParams);

        String dayWeek = DateUtils.convertToDateTime(requestParams.getString("startDate"),"yyyy-MM-dd").getDayOfWeek().name();

        model.addAttribute("jisa", CommonCodeUtils.getName("JISA_CODE", requestParams.getString("jisaCode")));
        model.addAttribute("date", requestParams.getString("startDate") + dayOfWeekStringConvert(dayWeek));
        model.addAttribute("user", requestParams.getString("userName"));
        model.addAttribute("vo", voList);
        model.addAttribute("txId", "sh03001180");
        model.addAttribute("fileName", "현금수송계획표 -" + com.bgf.shbank.utils.DateUtils.getNow("yyyyMMdd_HHmmss"));

        return new ExcelView();
    }

    private String dayOfWeekStringConvert(String value) {

        String name = "";

        switch (value) {
            case "MONDAY": name = " 월요일";
                break;
            case "TUESDAY": name = " 화요일";
                break;
            case "WEDNESDAY": name = " 수요일";
                break;
            case "THURSDAY": name = " 목요일";
                break;
            case "FRIDAY": name = " 금요일";
                break;
            case "SATURDAY": name = " 토요일";
                break;
            case "SUNDAY": name = " 일요일";
                break;
        }

        return name;
    }
}