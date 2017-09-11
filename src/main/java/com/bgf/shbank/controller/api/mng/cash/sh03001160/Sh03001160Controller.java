package com.bgf.shbank.controller.api.mng.cash.sh03001160;

import com.bgf.shbank.domain.mng.cash.sh03001160.Sh03001160;
import com.bgf.shbank.domain.mng.cash.sh03001160.Sh03001160Service;
import com.bgf.shbank.domain.mng.cash.sh03001160.Sh03001160VO;
import com.bgf.shbank.utils.DateUtils;
import com.bgf.shbank.utils.ModelMapperUtils;
import io.onsemiro.controller.BaseController;
import io.onsemiro.core.api.ApiException;
import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.api.response.Responses;
import io.onsemiro.core.code.ApiStatus;
import io.onsemiro.core.parameter.RequestParams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@RestController
@RequestMapping(value = "/api/v1/mng/cash/sh03001160")
public class Sh03001160Controller extends BaseController {

    @Inject
    private Sh03001160Service sh03001160Service;

    @GetMapping
    public Responses.PageResponse list(Pageable pageable, RequestParams<Sh03001160> requestParams) {
        Page<Sh03001160> pages = sh03001160Service.find(pageable, requestParams);
        return Responses.PageResponse.of(Sh03001160VO.of(pages.getContent()), pages);
    }

    @GetMapping(params = "nextSeqNo")
    public Sh03001160VO nextSeqNo(RequestParams<Sh03001160VO> requestParams) {
        return sh03001160Service.nextSeqNo(requestParams);
    }

    @PutMapping
    @PostMapping
    public ApiResponse save(@RequestBody Sh03001160VO sh03001160VO) {

        sh03001160VO.setAddCashSendingAmt(sh03001160VO.getAddCashSendingAmt().replace(",", ""));
        sh03001160VO.setAddCash50kSendingAmt(sh03001160VO.getAddCash50kSendingAmt().replace(",", ""));

        Sh03001160 sh03001160 = ModelMapperUtils.map(sh03001160VO, Sh03001160.class);

        if (sh03001160VO.getTxId() == null) {
            sh03001160.setTxId(Timestamp.valueOf(DateUtils.convertToString(LocalDateTime.now(), DateUtils.DATE_TIME_PATTERN)));
        } else {
            sh03001160.setTxId(DateUtils.convertToTimestamp(sh03001160VO.getTxId(), "yyyy-MM-dd HH:mm:ss"));
        }

        sh03001160Service.save(sh03001160);

        return ok();
    }

    @GetMapping(params = "stextSend")
    public ApiResponse stextSend(Sh03001160VO sh03001160VO) {
        if (sh03001160VO.getTxId() == null) {
            LocalDateTime now = LocalDateTime.now();
            sh03001160VO.setTxId(DateUtils.convertToString(now, "yyyy-MM-dd HH:mm:ss"));
        }

        sh03001160VO.setAddCashSendingAmt(sh03001160VO.getAddCashSendingAmt().replace(",", ""));
        sh03001160VO.setAddCash50kSendingAmt(sh03001160VO.getAddCash50kSendingAmt().replace(",", ""));

        ApiResponse apiResponse = sh03001160Service.stextSend(sh03001160VO);

        if (apiResponse.getStatus() == -1) {
            throw new ApiException(ApiStatus.SYSTEM_ERROR, apiResponse.getMessage());
        }

        return apiResponse;
    }
}