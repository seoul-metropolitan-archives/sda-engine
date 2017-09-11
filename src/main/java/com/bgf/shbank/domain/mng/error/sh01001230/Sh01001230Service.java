package com.bgf.shbank.domain.mng.error.sh01001230;

import com.bgf.shbank.domain.mng.error.sh01001240.Sh01001240VO;
import com.bgf.shbank.utils.ModelMapperUtils;
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
import java.util.List;

@Slf4j
@Service
public class Sh01001230Service extends BaseService<Sh01001230, Sh01001230.Sh01001230Id> {

    @Inject
    public Sh01001230Service(Sh01001230Repository sh01001230Repository) {
        super(sh01001230Repository);
    }

    @Autowired
    private RestTemplate restTemplate;

    @Value("${msg.exchange.gateway.online.ip}")
    private String ip;

    @Value("${msg.exchange.gateway.online.port}")
    private String port;

    private String url;

    @PostConstruct
    private void buildUrl() {
        url = "http://" + ip + ":" + port + "/api/v1/mng/error/sh01001240";
    }

    public ApiResponse sendAndReceive(Sh01001230VO vo) {
        ResponseEntity<ApiResponse> apiResponseEntity;

        try {
            Sh01001240VO sh01001240VO = ModelMapperUtils.map(vo, Sh01001240VO.class);

            apiResponseEntity = restTemplate.postForEntity(url, sh01001240VO, ApiResponse.class);
        } catch (RestClientException e) {
            log.error("sh01001240Service-sendAndReceive :: {}", e.getMessage());
            throw new ApiException(ApiStatus.SYSTEM_ERROR, "전문서버가 응답하지 않습니다. 관리자에게 문의하시기 바랍니다.");
        }

        return apiResponseEntity.getBody();
    }

    public Page<Sh01001230> find(Pageable pageable, RequestParams<Sh01001230VO> requestParams) {
        String filter = requestParams.getString("filter");
        String jisaCode = requestParams.getString("jisaCode");
        String branchCode = requestParams.getString("branchCode");
        String cornerCode = requestParams.getString("cornerCode");
        String terminalNo = requestParams.getString("terminalNo");
        Timestamp startDate = requestParams.getTimestamp("startDate");
        Timestamp endDate = requestParams.getTimestamp("endDate");

        QSh01001230 qSh01001230 = QSh01001230.sh01001230;

        BooleanBuilder builder = new BooleanBuilder();

        if (isNotEmpty(jisaCode)) {
            builder.and(qSh01001230.jisaCode.eq(jisaCode));
        }

        if (isNotEmpty(branchCode)) {
            builder.and(qSh01001230.branchCode.eq(branchCode));
        }

        if (isNotEmpty(cornerCode)) {
            builder.and(qSh01001230.cornerCode.eq(cornerCode));
        }

        if (isNotEmpty(terminalNo)) {
            builder.and(qSh01001230.terminalNo.eq(terminalNo));
        }

        if (startDate != null && endDate != null) {
            builder.and(qSh01001230.handleOccurDatetime.between(startDate, endDate));
        }

        OrderSpecifier<Timestamp> sortOrder = qSh01001230.handleOccurDatetime.desc();
        List<Sh01001230> resultList = select().from(qSh01001230).where(builder).orderBy(sortOrder).fetch();

        return filter(resultList, pageable, filter, Sh01001230.class);
    }
}