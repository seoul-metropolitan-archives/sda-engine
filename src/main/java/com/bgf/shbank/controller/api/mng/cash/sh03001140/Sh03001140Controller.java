package com.bgf.shbank.controller.api.mng.cash.sh03001140;

import com.bgf.shbank.domain.mng.cash.sh03001140.Sh03001140;
import com.bgf.shbank.domain.mng.cash.sh03001140.Sh03001140Service;
import com.bgf.shbank.domain.mng.cash.sh03001140.Sh03001140VO;
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
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@RestController
@RequestMapping(value = "/api/v1/mng/cash/sh03001140")
public class Sh03001140Controller extends BaseController {

    @Inject
    private Sh03001140Service sh03001140Service;

    @GetMapping
    public Responses.PageResponse list(Pageable pageable, RequestParams<Sh03001140> requestParams) {
        Page<Sh03001140> pages = sh03001140Service.find(pageable, requestParams);
        return Responses.PageResponse.of(Sh03001140VO.of(pages.getContent()), pages);
    }

    @PutMapping
    @PostMapping
    public ApiResponse save(@RequestBody Sh03001140VO sh03001140VO) {
        Sh03001140 sh03001140 = ModelMapperUtils.map(sh03001140VO, Sh03001140.class);

        if (!StringUtils.isEmpty(sh03001140.getPrevDayCashSendingAmt())) {
            sh03001140.setPrevDayCashSendingAmt(StringUtils.replace(sh03001140.getPrevDayCashSendingAmt(), ",", ""));
        } else {
            sh03001140.setPrevDayCashSendingAmt("0");
        }

        if (!StringUtils.isEmpty(sh03001140.getDepositAmt())) {
            sh03001140.setDepositAmt(StringUtils.replace(sh03001140.getDepositAmt(), ",", ""));
        } else {
            sh03001140.setDepositAmt("0");
        }

        if (!StringUtils.isEmpty(sh03001140.getGiveAmt())) {
            sh03001140.setGiveAmt(StringUtils.replace(sh03001140.getGiveAmt(), ",", ""));
        } else {
            sh03001140.setGiveAmt("0");
        }

        if (!StringUtils.isEmpty(sh03001140.getCloseAmt())) {
            sh03001140.setCloseAmt(StringUtils.replace(sh03001140.getCloseAmt(), ",", ""));
        } else {
            sh03001140.setCloseAmt("0");
        }

        if (!StringUtils.isEmpty(sh03001140.getNoneProcessAmt())) {
            sh03001140.setNoneProcessAmt(StringUtils.replace(sh03001140.getNoneProcessAmt(), ",", ""));
        } else {
            sh03001140.setNoneProcessAmt("0");
        }

        if (!StringUtils.isEmpty(sh03001140.getRtrvlFund())) {
            sh03001140.setRtrvlFund(StringUtils.replace(sh03001140.getRtrvlFund(), ",", ""));
        } else {
            sh03001140.setRtrvlFund("0");
        }

        if (!StringUtils.isEmpty(sh03001140.getAdjustLackAmtCount())) {
            sh03001140.setAdjustLackAmtCount(StringUtils.replace(sh03001140.getAdjustLackAmtCount(), ",", ""));
        } else {
            sh03001140.setAdjustLackAmtCount("0");
        }

        if (!StringUtils.isEmpty(sh03001140.getAdjustLackAmt())) {
            sh03001140.setAdjustLackAmt(StringUtils.replace(sh03001140.getAdjustLackAmt(), ",", ""));
        } else {
            sh03001140.setAdjustLackAmt("0");
        }

        if (StringUtils.isEmpty(sh03001140.getCloseDate())) {
            sh03001140.setCloseDate(Timestamp.valueOf(DateUtils.convertToString(LocalDateTime.now(), DateUtils.DATE_TIME_PATTERN)));
        }

        sh03001140Service.save(sh03001140);
        return ok();
    }

    @GetMapping(params = "stextSend")
    public ApiResponse stextSend(Sh03001140VO sh03001140VO) {
        if (!StringUtils.isEmpty(sh03001140VO.getPrevDayCashSendingAmt())) {
            sh03001140VO.setPrevDayCashSendingAmt(StringUtils.replace(sh03001140VO.getPrevDayCashSendingAmt(), ",", ""));
        } else {
            sh03001140VO.setPrevDayCashSendingAmt("0");
        }

        if (!StringUtils.isEmpty(sh03001140VO.getDepositAmt())) {
            sh03001140VO.setDepositAmt(StringUtils.replace(sh03001140VO.getDepositAmt(), ",", ""));
        } else {
            sh03001140VO.setDepositAmt("0");
        }

        if (!StringUtils.isEmpty(sh03001140VO.getGiveAmt())) {
            sh03001140VO.setGiveAmt(StringUtils.replace(sh03001140VO.getGiveAmt(), ",", ""));
        } else {
            sh03001140VO.setGiveAmt("0");
        }

        if (!StringUtils.isEmpty(sh03001140VO.getCloseAmt())) {
            sh03001140VO.setCloseAmt(StringUtils.replace(sh03001140VO.getCloseAmt(), ",", ""));
        } else {
            sh03001140VO.setCloseAmt("0");
        }

        if (!StringUtils.isEmpty(sh03001140VO.getNoneProcessAmt())) {
            sh03001140VO.setNoneProcessAmt(StringUtils.replace(sh03001140VO.getNoneProcessAmt(), ",", ""));
        } else {
            sh03001140VO.setNoneProcessAmt("0");
        }

        if (!StringUtils.isEmpty(sh03001140VO.getRtrvlFund())) {
            sh03001140VO.setRtrvlFund(StringUtils.replace(sh03001140VO.getRtrvlFund(), ",", ""));
        } else {
            sh03001140VO.setRtrvlFund("0");
        }

        if (!StringUtils.isEmpty(sh03001140VO.getAdjustLackAmtCount())) {
            sh03001140VO.setAdjustLackAmtCount(StringUtils.replace(sh03001140VO.getAdjustLackAmtCount(), ",", ""));
        } else {
            sh03001140VO.setAdjustLackAmtCount("0");
        }

        if (!StringUtils.isEmpty(sh03001140VO.getAdjustLackAmt())) {
            sh03001140VO.setAdjustLackAmt(StringUtils.replace(sh03001140VO.getAdjustLackAmt(), ",", ""));
        } else {
            sh03001140VO.setAdjustLackAmt("0");
        }

        sh03001140VO.setCloseDate(StringUtils.replace(sh03001140VO.getCloseDate(), "-", ""));

        ApiResponse apiResponse = sh03001140Service.stextSend(sh03001140VO);

        if (apiResponse.getStatus() == -1) {
            throw new ApiException(ApiStatus.SYSTEM_ERROR, apiResponse.getMessage());
        }

        return apiResponse;
    }
}