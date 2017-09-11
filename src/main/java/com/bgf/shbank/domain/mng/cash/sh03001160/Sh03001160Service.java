package com.bgf.shbank.domain.mng.cash.sh03001160;

import com.bgf.shbank.utils.ModelMapperUtils;
import com.querydsl.core.BooleanBuilder;
import io.onsemiro.core.api.ApiException;
import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.code.ApiStatus;
import io.onsemiro.core.domain.BaseService;
import io.onsemiro.core.parameter.RequestParams;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.BoundMapperFacade;
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
import java.sql.Timestamp;
import java.util.List;

@Slf4j
@Service
public class Sh03001160Service extends BaseService<Sh03001160, Sh03001160.Sh03001160Id> {

    @Inject
    public Sh03001160Mapper sh03001160Mapper;

    @Inject
    public Sh03001160Service(Sh03001160Repository sh03001160Repository) {
        super(sh03001160Repository);
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
        url = "http://" + ip + ":" + port + "/api/v1/mng/cash/sh03001160";
    }

    public ApiResponse stextSend(Sh03001160VO vo) {
        ResponseEntity<ApiResponse> apiResponseEntity;

        try {
            apiResponseEntity = restTemplate.postForEntity(url, vo, ApiResponse.class);
        } catch (RestClientException e) {
            log.error("sh03001160Service-sendAndReceive :: {}", e.getMessage());
            throw new ApiException(ApiStatus.SYSTEM_ERROR, "전문서버가 응답하지 않습니다. 관리자에게 문의하시기 바랍니다.");
        }

        return apiResponseEntity.getBody();
    }

    public Page<Sh03001160> find(Pageable pageable, RequestParams<Sh03001160> requestParams) {

        String filter = requestParams.getString("filter");
        String jisaCode = requestParams.getString("jisaCode");
        String branchCode = requestParams.getString("branchCode");
        String terminalNo = requestParams.getString("terminalNo");
        Timestamp startDate = requestParams.getTimestamp("startDate");
        Timestamp endDate = requestParams.getTimestamp("endDate");

        QSh03001160 qSh03001160 = QSh03001160.sh03001160;

        BooleanBuilder builder = new BooleanBuilder();

        if (isNotEmpty(jisaCode)) {
            builder.and(qSh03001160.jisaCode.eq(jisaCode));
        }

        if (isNotEmpty(branchCode)) {
            builder.and(qSh03001160.branchCode.eq(branchCode));
        }

        if (isNotEmpty(terminalNo)) {
            builder.and(qSh03001160.terminalNo.eq(terminalNo));
        }

        if (startDate != null && endDate != null) {
            builder.and(qSh03001160.cashSendingDate.between(startDate, endDate));
        }

//        OrderSpecifier<String> sortOrder = qSh03001160.addCashSendingSeqNo.asc();
//        List<Sh03001160> resultList = select().from(qSh03001160).where(builder).orderBy(sortOrder).fetch();

        List<Sh03001160> resultList = select().from(qSh03001160).where(builder).fetch();

        return filter(resultList, pageable, filter, Sh03001160.class);
    }

    public Sh03001160VO nextSeqNo(RequestParams<Sh03001160VO> requestParams) {

        Sh03001160 sh03001160 = new Sh03001160();

        sh03001160.setCashSendingDate(requestParams.getTimestamp("cashSendingDate"));
        sh03001160.setJisaCode(requestParams.getString("jisaCode"));
        sh03001160.setBranchCode(requestParams.getString("branchCode"));
        sh03001160.setTerminalNo(requestParams.getString("terminalNo"));

        return buildVO(sh03001160Mapper.nextSeqNo(sh03001160));
    }

    private Sh03001160VO buildVO(Sh03001160 sh03001160) {

        if (sh03001160 == null) {
            return new Sh03001160VO();
        } else {
            BoundMapperFacade<Sh03001160, Sh03001160VO> mapper =
                    ModelMapperUtils.getMapper("Sh03001160", this.getClass().getPackage().getName());
            return mapper.map(sh03001160);
        }
    }
}