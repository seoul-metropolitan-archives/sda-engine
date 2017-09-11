package com.bgf.shbank.domain.mng.error.sh01001180;

import com.bgf.shbank.domain.mng.error.sh01001190.Sh01001190VO;
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
public class Sh01001180Service extends BaseService<Sh01001180, Sh01001180.Sh01001180Id> {

    @Inject
    public Sh01001180Service(Sh01001180Repository sh01001180Repository) {
        super(sh01001180Repository);
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
        url = "http://" + ip + ":" + port + "/api/v1/mng/error/sh01001190";
    }

    public ApiResponse sendAndReceive(Sh01001180VO vo) {
        ResponseEntity<ApiResponse> apiResponseEntity;

        try {
            Sh01001190VO sh01001190VO = ModelMapperUtils.map(vo, Sh01001190VO.class);

            apiResponseEntity = restTemplate.postForEntity(url, sh01001190VO, ApiResponse.class);
        } catch (RestClientException e) {
            log.error("sh01001190Service-sendAndReceive :: {}", e.getMessage());
            throw new ApiException(ApiStatus.SYSTEM_ERROR, "전문서버가 응답하지 않습니다. 관리자에게 문의하시기 바랍니다.");
        }

        return apiResponseEntity.getBody();
    }

    public Page<Sh01001180> find(Pageable pageable, RequestParams<Sh01001180VO> requestParams) {
        String filter = requestParams.getString("filter");
        String jisaCode = requestParams.getString("jisaCode");
        String branchCode = requestParams.getString("branchCode");
        String cornerCode = requestParams.getString("cornerCode");
        String terminalNo = requestParams.getString("terminalNo");
        Timestamp startDate = requestParams.getTimestamp("startDate");
        Timestamp endDate = requestParams.getTimestamp("endDate");

        QSh01001180 qSh01001180 = QSh01001180.sh01001180;

        BooleanBuilder builder = new BooleanBuilder();

        if (isNotEmpty(jisaCode)) {
            builder.and(qSh01001180.jisaCode.eq(jisaCode));
        }

        if (isNotEmpty(branchCode)) {
            builder.and(qSh01001180.branchCode.eq(branchCode));
        }

        if (isNotEmpty(cornerCode)) {
            builder.and(qSh01001180.cornerCode.eq(cornerCode));
        }

        if (isNotEmpty(terminalNo)) {
            builder.and(qSh01001180.terminalNo.eq(terminalNo));
        }

        if (startDate != null && endDate != null) {
            builder.and(qSh01001180.errorDatetime.between(startDate, endDate));
        }

        OrderSpecifier<Timestamp> sortOrder = qSh01001180.errorDatetime.desc();
        List<Sh01001180> resultList = select().from(qSh01001180).where(builder).orderBy(sortOrder).fetch();

        return filter(resultList, pageable, filter, Sh01001180.class);
    }
}