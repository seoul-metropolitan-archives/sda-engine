package com.bgf.shbank.domain.mng.cash.sh03001120;

import com.bgf.shbank.utils.DateUtils;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
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
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class Sh03001120Service extends BaseService<Sh03001120, Sh03001120.Sh03001120Id> {

    @Autowired
    private Sh03001120Repository sh03001120Repo;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${msg.exchange.gateway.online.ip}")
    private String ip;

    @Value("${msg.exchange.gateway.online.port}")
    private String port;

    private String url;

    @PostConstruct
    private void buildUrl() {
        url = "http://" + ip + ":" + port + "/api/v1/mng/cash/sh03001120";
    }

    @Inject
    public Sh03001120Service(Sh03001120Repository sh03001120Repository) {
        super(sh03001120Repository);
    }

    public Page<Sh03001120> find(Pageable pageable, RequestParams<Sh03001120VO> requestParams) {

        String filter = requestParams.getString("filter");
        String jisaCode = requestParams.getString("jisaCode");
        String branchCode = requestParams.getString("branchCode");
        String terminalNo = requestParams.getString("terminalNo");
        Timestamp referDate = requestParams.getTimestamp("referDate");

        String referStatementNo = requestParams.getString("referStatementNo");
        Timestamp dealStartTime = DateUtils.convertToTimestamp(requestParams.getString("referDate") + ' ' + requestParams.getString("referStartTime"), "yyyy-MM-dd HH:mm");
        Timestamp dealEndTime = DateUtils.convertToTimestamp(requestParams.getString("referDate") + ' ' + requestParams.getString("referEndTime"), "yyyy-MM-dd HH:mm");

        QSh03001120 qSh03001120 = QSh03001120.sh03001120;

        BooleanBuilder builder = new BooleanBuilder();

        if (isNotEmpty(jisaCode)) {
            builder.and(qSh03001120.jisaCode.eq(jisaCode));
        }

        if (isNotEmpty(branchCode)) {
            builder.and(qSh03001120.branchCode.eq(branchCode));
        }

        if (isNotEmpty(terminalNo)) {
            builder.and(qSh03001120.terminalNo.eq(terminalNo));
        }

        if (isNotEmpty(referStatementNo)) {
            builder.and(qSh03001120.referStatementNo.eq(referStatementNo));
        }

        if (referDate != null) {
            builder.and(qSh03001120.referDate.eq(referDate));
        }

        if (dealStartTime != null && dealEndTime != null) {
            builder.and(qSh03001120.dealTime.between(dealStartTime, dealEndTime));
        }

        OrderSpecifier<Timestamp> sortOrder = qSh03001120.dealTime.desc();

        List<Sh03001120> resultList = select().from(qSh03001120).where(builder).orderBy(sortOrder).fetch();

        return filter(resultList, pageable, filter, Sh03001120.class);
    }

    public Page<Sh03001120> findAll(Pageable pageable, RequestParams<Sh03001120VO> requestParams) {

        String filter = requestParams.getString("filter");
        String jisaCode = requestParams.getString("jisaCode");
        String branchCode = requestParams.getString("branchCode");
        String terminalNo = requestParams.getString("terminalNo");
        Timestamp referDate = requestParams.getTimestamp("referDate");

        LocalDateTime referDateTemp = referDate.toLocalDateTime();
        referDateTemp = referDateTemp.minusDays(3);

        Timestamp referStartDate = Timestamp.valueOf(referDateTemp);

        QSh03001120 qSh03001120 = QSh03001120.sh03001120;

        BooleanBuilder builder = new BooleanBuilder();

        if (isNotEmpty(jisaCode)) {
            builder.and(qSh03001120.jisaCode.eq(jisaCode));
        }

        if (isNotEmpty(branchCode)) {
            builder.and(qSh03001120.branchCode.eq(branchCode));
        }

        if (isNotEmpty(terminalNo)) {
            builder.and(qSh03001120.terminalNo.eq(terminalNo));
        }

        builder.and(qSh03001120.referDate.between(referStartDate, referDate));


        OrderSpecifier<Timestamp> sortOrder = qSh03001120.dealTime.desc();

        List<Sh03001120> resultList = select().from(qSh03001120).where(builder).orderBy(sortOrder).fetch();

        return filter(resultList, pageable, filter, Sh03001120.class);
    }

    public ApiResponse findOne(Sh03001120VO vo) {
        ResponseEntity<ApiResponse> apiResponseEntity;

        try {
            apiResponseEntity = restTemplate.postForEntity(url, vo, ApiResponse.class);
        } catch (RestClientException e) {
            log.error("Sh03001120Service-sendAndReceive :: {}", e.getMessage());
            throw new ApiException(ApiStatus.SYSTEM_ERROR, "전문서버가 응답하지 않습니다. 관리자에게 문의하시기 바랍니다.");
        }

        return apiResponseEntity.getBody();
    }

    public List<Sh03001120> findExcel(RequestParams<Sh03001120VO> requestParams) {

        String filter = requestParams.getString("filter");
        String jisaCode = requestParams.getString("jisaCode");
        String branchCode = requestParams.getString("branchCode");
        String terminalNo = requestParams.getString("terminalNo");
        Timestamp referDate = requestParams.getTimestamp("referDate");

        String referStatementNo = requestParams.getString("referStatementNo");
        Timestamp dealStartTime = DateUtils.convertToTimestamp(requestParams.getString("referDate") + ' ' + requestParams.getString("referStartTime"), "yyyy-MM-dd HH:mm");
        Timestamp dealEndTime = DateUtils.convertToTimestamp(requestParams.getString("referDate") + ' ' + requestParams.getString("referEndTime"), "yyyy-MM-dd HH:mm");

        QSh03001120 qSh03001120 = QSh03001120.sh03001120;

        BooleanBuilder builder = new BooleanBuilder();

        if (isNotEmpty(jisaCode)) {
            builder.and(qSh03001120.jisaCode.eq(jisaCode));
        }

        if (isNotEmpty(branchCode)) {
            builder.and(qSh03001120.branchCode.eq(branchCode));
        }

        if (isNotEmpty(terminalNo)) {
            builder.and(qSh03001120.terminalNo.eq(terminalNo));
        }

        if (isNotEmpty(referStatementNo)) {
            builder.and(qSh03001120.referStatementNo.eq(referStatementNo));
        }

        if (referDate != null) {
            builder.and(qSh03001120.referDate.eq(referDate));
        }

        if (dealStartTime != null && dealEndTime != null) {
            builder.and(qSh03001120.dealTime.between(dealStartTime, dealEndTime));
        }

        List<Sh03001120> resultList = select().from(qSh03001120).where(builder).fetch();

        return filter(resultList, filter);
    }
}