package com.bgf.shbank.domain.mng.cash.sh03001140;

import com.querydsl.core.BooleanBuilder;
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
import java.util.List;

@Slf4j
@Service
public class Sh03001140Service extends BaseService<Sh03001140, Sh03001140.Sh03001140Id> {

    @Autowired
    private Sh03001140Repository sh03001140Repo;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${msg.exchange.gateway.batch.ip}")
    private String ip;

    @Value("${msg.exchange.gateway.batch.port}")
    private String port;

    private String url;

    @PostConstruct
    private void buildUrl() {
        url = "http://" + ip + ":" + port + "/api/v1/mng/cash/sh03001140";
    }

    @Inject
    public Sh03001140Service(Sh03001140Repository sh03001140Repository) {
        super(sh03001140Repository);
    }

    public Page<Sh03001140> find(Pageable pageable, RequestParams<Sh03001140> requestParams) {

        String filter = requestParams.getString("filter");
        String jisaCode = requestParams.getString("jisaCode");
        String branchCode = requestParams.getString("branchCode");
        String terminalNo = requestParams.getString("terminalNo");
        Timestamp startDate = requestParams.getTimestamp("startDate");
        Timestamp endDate = requestParams.getTimestamp("endDate");

        QSh03001140 qSh03001140 = QSh03001140.sh03001140;

        BooleanBuilder builder = new BooleanBuilder();

        if (isNotEmpty(jisaCode)) {
            builder.and(qSh03001140.jisaCode.eq(jisaCode));
        }

        if (isNotEmpty(branchCode)) {
            builder.and(qSh03001140.branchCode.eq(branchCode));
        }

        if (isNotEmpty(terminalNo)) {
            builder.and(qSh03001140.terminalNo.eq(terminalNo));
        }

        if (startDate != null && endDate != null) {
            builder.and(qSh03001140.closeDate.between(startDate, endDate));
        }

        List<Sh03001140> resultList = select().from(qSh03001140).where(builder).fetch();

        return filter(resultList, pageable, filter, Sh03001140.class);
    }

    public ApiResponse stextSend(Sh03001140VO vo) {
        ResponseEntity<ApiResponse> apiResponseEntity;

        try {
            apiResponseEntity = restTemplate.postForEntity(url, vo, ApiResponse.class);
        } catch (RestClientException e) {
            log.error("sh03001180Service-sendAndReceive :: {}", e.getMessage());
            throw new ApiException(ApiStatus.SYSTEM_ERROR, "전문서버가 응답하지 않습니다. 관리자에게 문의하시기 바랍니다.");
        }

        return apiResponseEntity.getBody();
    }
}