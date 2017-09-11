package com.bgf.shbank.domain.mng.cash.sh03001180;

import io.onsemiro.core.api.ApiException;
import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.code.ApiStatus;
import io.onsemiro.core.domain.BaseService;
import io.onsemiro.core.parameter.RequestParams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

@Slf4j
@Service
public class Sh03001180Service extends BaseService<Sh03001180, Sh03001180.Sh03001180Id> {

    @Inject
    public Sh03001180Mapper sh03001180Mapper;

    @Inject
    public Sh03001180Service(Sh03001180Repository sh03001180Repository) {
        super(sh03001180Repository);
    }

    @Autowired
    private RestTemplate restTemplate;

    @Value("${msg.exchange.gateway.batch.ip}")
    private String ip;

    @Value("${msg.exchange.gateway.batch.port}")
    private String port;

    private String url;

    @PostConstruct
    private void buildUrl() {
        url = "http://" + ip + ":" + port + "/api/v1/mng/cash/sh03001180";
    }

    public ApiResponse stextSend(Sh03001180VO vo) {
        ResponseEntity<ApiResponse> apiResponseEntity;

        try {
            apiResponseEntity = restTemplate.postForEntity(url, vo, ApiResponse.class);
        } catch (RestClientException e) {
            log.error("sh03001180Service-sendAndReceive :: {}", e.getMessage());
            throw new ApiException(ApiStatus.SYSTEM_ERROR, "전문서버가 응답하지 않습니다. 관리자에게 문의하시기 바랍니다.");
        }

        return apiResponseEntity.getBody();
    }

    public Page<Sh03001180> find(Pageable pageable, String filter) {
        return filter(findAll(), pageable, filter, Sh03001180.class);
    }

    public Page<Sh03001180> findAll(Pageable pageable, RequestParams<Sh03001180VO> requestParams) {

        String filter = requestParams.getString("filter");

        Sh03001180 sh03001180 = new Sh03001180();

        sh03001180.setJisaCode(requestParams.getString("jisaCode"));
        sh03001180.setBranchCode(requestParams.getString("branchCode"));
        sh03001180.setTerminalNo(requestParams.getString("terminalNo"));
        sh03001180.setCashSendingDate(requestParams.getTimestamp("startDate"));

        List<Sh03001180> resultList = sh03001180Mapper.findAll(sh03001180);

        return filter(resultList, pageable, filter, Sh03001180.class);
    }

    public List<Sh03001180ExcelVO> findExcel(RequestParams<Sh03001180ExcelVO> requestParams) {

        Sh03001180 sh03001180 = new Sh03001180();
        sh03001180.setJisaCode(requestParams.getString("jisaCode"));
        sh03001180.setCashSendingDate(requestParams.getTimestamp("startDate"));

        List<Sh03001180ExcelVO> resultList = sh03001180Mapper.findExcel(sh03001180);

        Long sumCashAmt = 0l;
        Long sumCash50kGiveAmt = 0l;
        Long sumPredictionCashSendingAmt = 0l;
        Long sumPredictionCash50kSendingAmt = 0l;
        Long sumCashSendingAmt = 0l;
        Long sumCash50kSendingAmt = 0l;
        Long sumAddCashSendingAmt = 0l;
        Long sumAddCash50kSendingAmt = 0l;
        Long sumRtrvlAmt = 0l;

        for (int j = 0 ; j < resultList.size(); j++) {
            sumCashAmt += resultList.get(j).getCashAmt();
            sumCash50kGiveAmt += resultList.get(j).getCash50kGiveAmt()==null?0l:resultList.get(j).getCash50kGiveAmt();
            sumPredictionCashSendingAmt += resultList.get(j).getPredictionCashSendingAmt();
            sumPredictionCash50kSendingAmt += resultList.get(j).getPredictionCash50kSendingAmt();
            sumCashSendingAmt += resultList.get(j).getCashSendingAmt();
            sumCash50kSendingAmt += resultList.get(j).getCash50kSendingAmt();
            sumAddCashSendingAmt += resultList.get(j).getAddCashSendingAmt();
            sumAddCash50kSendingAmt += resultList.get(j).getAddCash50kSendingAmt();
            sumRtrvlAmt += resultList.get(j).getRtrvlAmt();
        }

        Sh03001180ExcelVO sh03001180ExcelVO = new Sh03001180ExcelVO();
        sh03001180ExcelVO.setCashAmt(sumCashAmt);
        sh03001180ExcelVO.setCash50kGiveAmt(sumCash50kGiveAmt);
        sh03001180ExcelVO.setPredictionCashSendingAmt(sumPredictionCashSendingAmt);
        sh03001180ExcelVO.setPredictionCash50kSendingAmt(sumPredictionCash50kSendingAmt);
        sh03001180ExcelVO.setCashSendingAmt(sumCashSendingAmt);
        sh03001180ExcelVO.setCash50kSendingAmt(sumCash50kSendingAmt);
        sh03001180ExcelVO.setAddCashSendingAmt(sumAddCashSendingAmt);
        sh03001180ExcelVO.setAddCash50kSendingAmt(sumAddCash50kSendingAmt);
        sh03001180ExcelVO.setRtrvlAmt(sumRtrvlAmt);

        if(resultList.size() < 64) {
            for (int j = resultList.size() ; j < 64; j++) {
                resultList.add(new Sh03001180ExcelVO());
            }
        }

        resultList.add(sh03001180ExcelVO);

        return resultList;
    }
}