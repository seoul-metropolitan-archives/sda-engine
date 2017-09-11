package com.bgf.shbank.controller.api.mng.cash.sh03001150;

import com.bgf.shbank.domain.mng.cash.sh03001150.Sh03001150;
import com.bgf.shbank.domain.mng.cash.sh03001150.Sh03001150Service;
import com.bgf.shbank.domain.mng.cash.sh03001150.Sh03001150VO;
import com.bgf.shbank.utils.ModelMapperUtils;
import io.onsemiro.controller.BaseController;
import io.onsemiro.core.api.ApiException;
import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.api.response.Responses;
import io.onsemiro.core.code.ApiStatus;
import io.onsemiro.core.parameter.RequestParams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

@RestController
@RequestMapping(value = "/api/v1/mng/cash/sh03001150")
public class Sh03001150Controller extends BaseController {

    @Inject
    private Sh03001150Service sh03001150Service;

    @GetMapping
    public Responses.PageResponse list(Pageable pageable, RequestParams<Sh03001150> requestParams) {
        Page<Sh03001150> pages = sh03001150Service.find(pageable, requestParams);
        return Responses.PageResponse.of(Sh03001150VO.of(pages.getContent()), pages);
    }

    @PutMapping
    @PostMapping
    public ApiResponse save(@RequestBody Sh03001150VO sh03001150VO) {

        Sh03001150 sh03001150 = ModelMapperUtils.map(sh03001150VO, Sh03001150.class);

        if (!StringUtils.isEmpty(sh03001150.getNoneProcessAmt())) {
            sh03001150.setNoneProcessAmt(StringUtils.replace(sh03001150.getNoneProcessAmt(), ",", ""));
        } else {
            sh03001150.setNoneProcessAmt("0");
        }

        if (!StringUtils.isEmpty(sh03001150.getDealAmt())) {
            sh03001150.setDealAmt(StringUtils.replace(sh03001150.getDealAmt(), ",", ""));
        } else {
            sh03001150.setDealAmt("0");
        }

        if (!StringUtils.isEmpty(sh03001150.getSendCommission())) {
            sh03001150.setSendCommission(StringUtils.replace(sh03001150.getSendCommission(), ",", ""));
        } else {
            sh03001150.setSendCommission("0");
        }

        if (!StringUtils.isEmpty(sh03001150.getRtnCommission())) {
            sh03001150.setRtnCommission(StringUtils.replace(sh03001150.getRtnCommission(), ",", ""));
        } else {
            sh03001150.setRtnCommission("0");
        }

        sh03001150Service.save(sh03001150);
        return ok();
    }

    @GetMapping(params = "stextSend")
    public ApiResponse stextSend(Sh03001150VO sh03001150VO) {

        if (!StringUtils.isEmpty(sh03001150VO.getNoneProcessAmt())) {
            sh03001150VO.setNoneProcessAmt(StringUtils.replace(sh03001150VO.getNoneProcessAmt(), ",", ""));
        }else {
            sh03001150VO.setNoneProcessAmt("0");
        }

        if (!StringUtils.isEmpty(sh03001150VO.getDealAmt())) {
            sh03001150VO.setDealAmt(StringUtils.replace(sh03001150VO.getDealAmt(), ",", ""));
        }else {
            sh03001150VO.setDealAmt("0");
        }

        if (!StringUtils.isEmpty(sh03001150VO.getSendCommission())) {
            sh03001150VO.setSendCommission(StringUtils.replace(sh03001150VO.getSendCommission(), ",", ""));
        }else {
            sh03001150VO.setSendCommission("0");
        }

        if (!StringUtils.isEmpty(sh03001150VO.getRtnCommission())) {
            sh03001150VO.setRtnCommission(StringUtils.replace(sh03001150VO.getRtnCommission(), ",", ""));
        }else {
            sh03001150VO.setRtnCommission("0");
        }

        sh03001150VO.setDealDate(StringUtils.replace(sh03001150VO.getDealDate(), "-", ""));
        sh03001150VO.setProcessDate(StringUtils.replace(sh03001150VO.getProcessDate(), "-", ""));

        ApiResponse apiResponse = sh03001150Service.stextSend(sh03001150VO);

        if (apiResponse.getStatus() == -1) {
            throw new ApiException(ApiStatus.SYSTEM_ERROR, apiResponse.getMessage());
        }

        return apiResponse;
    }

    @GetMapping(params = "nextSeqNo")
    public Sh03001150VO nextSeqNo(RequestParams<Sh03001150VO> requestParams) {
        return sh03001150Service.nextSeqNo(requestParams);
    }
}