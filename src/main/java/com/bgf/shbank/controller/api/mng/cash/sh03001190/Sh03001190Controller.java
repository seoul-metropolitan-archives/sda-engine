package com.bgf.shbank.controller.api.mng.cash.sh03001190;

import com.bgf.shbank.domain.mng.cash.sh03001190.Sh03001190;
import com.bgf.shbank.domain.mng.cash.sh03001190.Sh03001190FormVO;
import com.bgf.shbank.domain.mng.cash.sh03001190.Sh03001190Service;
import com.bgf.shbank.domain.mng.cash.sh03001190.Sh03001190VO;
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
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/mng/cash/sh03001190")
public class Sh03001190Controller extends BaseController {

    @Inject
    private Sh03001190Service sh03001190Service;

    @GetMapping
    public Responses.PageResponse list(Pageable pageable, RequestParams<Sh03001190> requestParams) {
        Page<Sh03001190> pages = sh03001190Service.find(pageable, requestParams);
        return Responses.PageResponse.of(Sh03001190VO.of(pages.getContent()), pages);
    }

    @GetMapping(params = "findFormAmt")
    public List<Sh03001190FormVO> findFormAmt(Pageable pageable, RequestParams<Sh03001190VO> requestParams) {
        List<Sh03001190FormVO> pages = sh03001190Service.findFormAmt(pageable, requestParams);
        return pages;
    }

    @PutMapping
    @PostMapping
    public ApiResponse save(@RequestBody Sh03001190VO sh03001190VO) {

        Sh03001190 sh03001190 = ModelMapperUtils.map(sh03001190VO, Sh03001190.class);

        if (!StringUtils.isEmpty(sh03001190.getNextDayCashSendingAmt())) {
            sh03001190.setNextDayCashSendingAmt(StringUtils.replace(sh03001190.getNextDayCashSendingAmt(), ",", ""));
        }else{
            sh03001190.setNextDayCashSendingAmt("0");
        }

        if (!StringUtils.isEmpty(sh03001190.getNowDayAddCashSendingAmt())) {
            sh03001190.setNowDayAddCashSendingAmt(StringUtils.replace(sh03001190.getNowDayAddCashSendingAmt(), ",", ""));
        }else{
            sh03001190.setNowDayAddCashSendingAmt("0");
        }

        if (!StringUtils.isEmpty(sh03001190.getNowDayRtrvlAmt())) {
            sh03001190.setNowDayRtrvlAmt(StringUtils.replace(sh03001190.getNowDayRtrvlAmt(), ",", ""));
        }else{
            sh03001190.setNowDayRtrvlAmt("0");
        }

        if (!StringUtils.isEmpty(sh03001190.getNowDayLackAmt())) {
            sh03001190.setNowDayLackAmt(StringUtils.replace(sh03001190.getNowDayLackAmt(), ",", ""));
        }else{
            sh03001190.setNowDayLackAmt("0");
        }

        if (!StringUtils.isEmpty(sh03001190.getThisDayRtrvlExcpectAmt())) {
            sh03001190.setThisDayRtrvlExcpectAmt(StringUtils.replace(sh03001190.getThisDayRtrvlExcpectAmt(), ",", ""));
        }else{
            sh03001190.setThisDayRtrvlExcpectAmt("0");
        }

        if (!StringUtils.isEmpty(sh03001190.getNextDayRtrvlExcpectAmt())) {
            sh03001190.setNextDayRtrvlExcpectAmt(StringUtils.replace(sh03001190.getNextDayRtrvlExcpectAmt(), ",", ""));
        }else{
            sh03001190.setNextDayRtrvlExcpectAmt("0");
        }

        if (!StringUtils.isEmpty(sh03001190.getNextDayBillingAmt())) {
            sh03001190.setNextDayBillingAmt(StringUtils.replace(sh03001190.getNextDayBillingAmt(), ",", ""));
        }else{
            sh03001190.setNextDayBillingAmt("0");
        }

        if (!StringUtils.isEmpty(sh03001190.getTotalStockAmt())) {
            sh03001190.setTotalStockAmt(StringUtils.replace(sh03001190.getTotalStockAmt(), ",", ""));
        }else{
            sh03001190.setTotalStockAmt("0");
        }

        if (!StringUtils.isEmpty(sh03001190.getBeforeBdateStockAmt())) {
            sh03001190.setBeforeBdateStockAmt(StringUtils.replace(sh03001190.getBeforeBdateStockAmt(), ",", ""));
        }else{
            sh03001190.setBeforeBdateStockAmt("0");
        }

        if (!StringUtils.isEmpty(sh03001190.getBeforeBdateRecvAmt())) {
            sh03001190.setBeforeBdateRecvAmt(StringUtils.replace(sh03001190.getBeforeBdateRecvAmt(), ",", ""));
        }else{
            sh03001190.setBeforeBdateRecvAmt("0");
        }

        if (!StringUtils.isEmpty(sh03001190.getBeforeBdateCashSendingAmt())) {
            sh03001190.setBeforeBdateCashSendingAmt(StringUtils.replace(sh03001190.getBeforeBdateCashSendingAmt(), ",", ""));
        }else{
            sh03001190.setBeforeBdateCashSendingAmt("0");
        }

        if (!StringUtils.isEmpty(sh03001190.getBeforeBdateGiveAmt())) {
            sh03001190.setBeforeBdateGiveAmt(StringUtils.replace(sh03001190.getBeforeBdateGiveAmt(), ",", ""));
        }else{
            sh03001190.setBeforeBdateGiveAmt("0");
        }

        if (!StringUtils.isEmpty(sh03001190.getBeforeBdateDepositAmt())) {
            sh03001190.setBeforeBdateDepositAmt(StringUtils.replace(sh03001190.getBeforeBdateDepositAmt(), ",", ""));
        }else{
            sh03001190.setBeforeBdateDepositAmt("0");
        }

        if (!StringUtils.isEmpty(sh03001190.getThisDayAddCashSendingAmt())) {
            sh03001190.setThisDayAddCashSendingAmt(StringUtils.replace(sh03001190.getThisDayAddCashSendingAmt(), ",", ""));
        }else{
            sh03001190.setThisDayAddCashSendingAmt("0");
        }

        if (!StringUtils.isEmpty(sh03001190.getThisDayNoneLoadAmt())) {
            sh03001190.setThisDayNoneLoadAmt(StringUtils.replace(sh03001190.getThisDayNoneLoadAmt(), ",", ""));
        }else{
            sh03001190.setThisDayNoneLoadAmt("0");
        }

        if (!StringUtils.isEmpty(sh03001190.getPaymentOverAmt())) {
            sh03001190.setPaymentOverAmt(StringUtils.replace(sh03001190.getPaymentOverAmt(), ",", ""));
        }else{
            sh03001190.setPaymentOverAmt("0");
        }

        if(StringUtils.isEmpty(sh03001190.getTxId())){
            sh03001190.setTxId(Timestamp.valueOf(DateUtils.convertToString(LocalDateTime.now(), DateUtils.DATE_TIME_PATTERN)));
        }

        sh03001190Service.save(sh03001190);

        return ok();
    }

    @GetMapping(params = "stextSend")
    public ApiResponse stextSend(Sh03001190VO sh03001190VO) {


        if (!StringUtils.isEmpty(sh03001190VO.getNextDayCashSendingAmt())) {
            sh03001190VO.setNextDayCashSendingAmt(StringUtils.replace(sh03001190VO.getNextDayCashSendingAmt(), ",", ""));
        }else{
            sh03001190VO.setNextDayCashSendingAmt("0");
        }

        if (!StringUtils.isEmpty(sh03001190VO.getNowDayAddCashSendingAmt())) {
            sh03001190VO.setNowDayAddCashSendingAmt(StringUtils.replace(sh03001190VO.getNowDayAddCashSendingAmt(), ",", ""));
        }else{
            sh03001190VO.setNowDayAddCashSendingAmt("0");
        }

        if (!StringUtils.isEmpty(sh03001190VO.getNowDayRtrvlAmt())) {
            sh03001190VO.setNowDayRtrvlAmt(StringUtils.replace(sh03001190VO.getNowDayRtrvlAmt(), ",", ""));
        }else{
            sh03001190VO.setNowDayRtrvlAmt("0");
        }

        if (!StringUtils.isEmpty(sh03001190VO.getNowDayLackAmt())) {
            sh03001190VO.setNowDayLackAmt(StringUtils.replace(sh03001190VO.getNowDayLackAmt(), ",", ""));
        }else{
            sh03001190VO.setNowDayLackAmt("0");
        }

        if (!StringUtils.isEmpty(sh03001190VO.getThisDayRtrvlExcpectAmt())) {
            sh03001190VO.setThisDayRtrvlExcpectAmt(StringUtils.replace(sh03001190VO.getThisDayRtrvlExcpectAmt(), ",", ""));
        }else{
            sh03001190VO.setThisDayRtrvlExcpectAmt("0");
        }

        if (!StringUtils.isEmpty(sh03001190VO.getNextDayRtrvlExcpectAmt())) {
            sh03001190VO.setNextDayRtrvlExcpectAmt(StringUtils.replace(sh03001190VO.getNextDayRtrvlExcpectAmt(), ",", ""));
        }else{
            sh03001190VO.setNextDayRtrvlExcpectAmt("0");
        }

        if (!StringUtils.isEmpty(sh03001190VO.getNextDayBillingAmt())) {
            sh03001190VO.setNextDayBillingAmt(StringUtils.replace(sh03001190VO.getNextDayBillingAmt(), ",", ""));
        }else{
            sh03001190VO.setNextDayBillingAmt("0");
        }

        if (!StringUtils.isEmpty(sh03001190VO.getTotalStockAmt())) {
            sh03001190VO.setTotalStockAmt(StringUtils.replace(sh03001190VO.getTotalStockAmt(), ",", ""));
        }else{
            sh03001190VO.setTotalStockAmt("0");
        }

        if (!StringUtils.isEmpty(sh03001190VO.getBeforeBdateStockAmt())) {
            sh03001190VO.setBeforeBdateStockAmt(StringUtils.replace(sh03001190VO.getBeforeBdateStockAmt(), ",", ""));
        }else{
            sh03001190VO.setBeforeBdateStockAmt("0");
        }

        if (!StringUtils.isEmpty(sh03001190VO.getBeforeBdateRecvAmt())) {
            sh03001190VO.setBeforeBdateRecvAmt(StringUtils.replace(sh03001190VO.getBeforeBdateRecvAmt(), ",", ""));
        }else{
            sh03001190VO.setBeforeBdateRecvAmt("0");
        }

        if (!StringUtils.isEmpty(sh03001190VO.getBeforeBdateCashSendingAmt())) {
            sh03001190VO.setBeforeBdateCashSendingAmt(StringUtils.replace(sh03001190VO.getBeforeBdateCashSendingAmt(), ",", ""));
        }else{
            sh03001190VO.setBeforeBdateCashSendingAmt("0");
        }

        if (!StringUtils.isEmpty(sh03001190VO.getBeforeBdateGiveAmt())) {
            sh03001190VO.setBeforeBdateGiveAmt(StringUtils.replace(sh03001190VO.getBeforeBdateGiveAmt(), ",", ""));
        }else{
            sh03001190VO.setBeforeBdateGiveAmt("0");
        }

        if (!StringUtils.isEmpty(sh03001190VO.getBeforeBdateDepositAmt())) {
            sh03001190VO.setBeforeBdateDepositAmt(StringUtils.replace(sh03001190VO.getBeforeBdateDepositAmt(), ",", ""));
        }else{
            sh03001190VO.setBeforeBdateDepositAmt("0");
        }

        if (!StringUtils.isEmpty(sh03001190VO.getThisDayAddCashSendingAmt())) {
            sh03001190VO.setThisDayAddCashSendingAmt(StringUtils.replace(sh03001190VO.getThisDayAddCashSendingAmt(), ",", ""));
        }else{
            sh03001190VO.setThisDayAddCashSendingAmt("0");
        }

        if (!StringUtils.isEmpty(sh03001190VO.getThisDayNoneLoadAmt())) {
            sh03001190VO.setThisDayNoneLoadAmt(StringUtils.replace(sh03001190VO.getThisDayNoneLoadAmt(), ",", ""));
        }else{
            sh03001190VO.setThisDayNoneLoadAmt("0");
        }

        if (!StringUtils.isEmpty(sh03001190VO.getPaymentOverAmt())) {
            sh03001190VO.setPaymentOverAmt(StringUtils.replace(sh03001190VO.getPaymentOverAmt(), ",", ""));
        }else{
            sh03001190VO.setPaymentOverAmt("0");
        }

        if(StringUtils.isEmpty(sh03001190VO.getTxId())){
            sh03001190VO.setTxId(DateUtils.convertToString(LocalDateTime.now(), DateUtils.DATE_TIME_PATTERN));
        }

        sh03001190VO.setReqDate(StringUtils.replace(sh03001190VO.getReqDate(), "-", ""));
        sh03001190VO.setFundExpenseStartDate(StringUtils.replace(sh03001190VO.getFundExpenseStartDate(), "-", ""));
        sh03001190VO.setFundExpenseEndDate(StringUtils.replace(sh03001190VO.getFundExpenseEndDate(), "-", ""));

        ApiResponse apiResponse = sh03001190Service.stextSend(sh03001190VO);

        if (apiResponse.getStatus() == -1) {
            throw new ApiException(ApiStatus.SYSTEM_ERROR, apiResponse.getMessage());
        }

        return apiResponse;
    }


    @GetMapping(params = "nextSendingAmt")
    public Long nextDayCashSendingAmt(RequestParams<Sh03001190> requestParams) {
        Long value = sh03001190Service.nextDayCashSendingAmt(requestParams);

        return value;
    }

}