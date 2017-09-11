package com.bgf.shbank.domain.mng.cash.sh03001190;

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
public class Sh03001190Service extends BaseService<Sh03001190, Sh03001190.Sh03001190Id> {

    @Inject
    private Sh03001190Mapper sh03001190Mapper;

    @Autowired
    private Sh03001190Repository sh03001190Repo;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${msg.exchange.gateway.batch.ip}")
    private String ip;

    @Value("${msg.exchange.gateway.batch.port}")
    private String port;

    private String url;

    @PostConstruct
    private void buildUrl() {
        url = "http://" + ip + ":" + port + "/api/v1/mng/cash/sh03001190";
    }

    @Inject
    public Sh03001190Service(Sh03001190Repository sh03001190Repository) {
        super(sh03001190Repository);
    }

    public List<Sh03001190FormVO> findFormAmt(Pageable pageable, RequestParams<Sh03001190VO> requestParams) {
        String filter = requestParams.getString("filter");

        Sh03001190VO sh03001190VO = new Sh03001190VO();
        sh03001190VO.setReqDate(requestParams.getString("reqDate"));
        List<Sh03001190FormVO> resultList = sh03001190Mapper.findFormAmt(sh03001190VO);

        return resultList;
    }

    public Page<Sh03001190> find(Pageable pageable, RequestParams<Sh03001190> requestParams) {

        String filter = requestParams.getString("filter");
        String jisaCode = requestParams.getString("jisaCode");
        String branchCode = requestParams.getString("branchCode");
        Timestamp startDate = requestParams.getTimestamp("startDate");
        Timestamp endDate = requestParams.getTimestamp("endDate");

        QSh03001190 qSh03001190 = QSh03001190.sh03001190;

        BooleanBuilder builder = new BooleanBuilder();

        if (isNotEmpty(jisaCode)) {
            builder.and(qSh03001190.jisaCode.eq(jisaCode));
        }

        if (isNotEmpty(branchCode)) {
            builder.and(qSh03001190.branchCode.eq(branchCode));
        }

        if (startDate != null && endDate != null) {
            builder.and(qSh03001190.reqDate.between(startDate, endDate));
        }

        OrderSpecifier<Timestamp> sortOrder = qSh03001190.txId.desc();

        List<Sh03001190> resultList = select().from(qSh03001190).where(builder).orderBy(sortOrder).fetch();

        return filter(resultList, pageable, filter, Sh03001190.class);
    }

    public ApiResponse stextSend(Sh03001190VO vo) {
        ResponseEntity<ApiResponse> apiResponseEntity;

        try {
            apiResponseEntity = restTemplate.postForEntity(url, vo, ApiResponse.class);
        } catch (RestClientException e) {
            log.error("sh03001180Service-sendAndReceive :: {}", e.getMessage());
            throw new ApiException(ApiStatus.SYSTEM_ERROR, "전문서버가 응답하지 않습니다. 관리자에게 문의하시기 바랍니다.");
        }

        return apiResponseEntity.getBody();
    }

    public Long nextDayCashSendingAmt(RequestParams<Sh03001190> requestParams) {

        Timestamp reqDate = requestParams.getTimestamp("reqDate");
        LocalDateTime req = reqDate.toLocalDateTime();
        reqDate = Timestamp.valueOf(req.minusDays(2));

        QSh03001190 qSh03001190 = QSh03001190.sh03001190;

        BooleanBuilder builder = new BooleanBuilder();

        if (reqDate != null) {
            builder.and(qSh03001190.reqDate.eq(reqDate));
        }

        OrderSpecifier<Timestamp> sortOrder = qSh03001190.txId.desc();

        List<Sh03001190> resultList = select().from(qSh03001190).where(builder).orderBy(sortOrder).fetch();

        Long sum = 0L;
        for (Sh03001190 sh03001190 : resultList) {
            String value = sh03001190.getNextDayCashSendingAmt().trim();
            sum += Long.parseLong(value);
        }

        return sum;
    }
}
