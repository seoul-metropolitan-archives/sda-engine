package com.bgf.shbank.domain.mng.etc.sh04001110;

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
public class Sh04001110Service extends BaseService<Sh04001110, Timestamp> {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${msg.exchange.gateway.batch.ip}")
    private String ip;

    @Value("${msg.exchange.gateway.batch.port}")
    private String port;

    private String url;

    @PostConstruct
    private void buildUrl() {
        url = "http://" + ip + ":" + port + "/api/v1/mng/etc/sh04001110";
    }

    @Inject
    public Sh04001110Service(Sh04001110Repository sh04001110Repository) {
        super(sh04001110Repository);
    }

    public Page<Sh04001110> find(Pageable pageable, String filter) {
        return filter(findAll(), pageable, filter, Sh04001110.class);
    }

    public Page<Sh04001110> find(Pageable pageable, RequestParams<Sh04001110> requestParams) {

        String reqStextSeqNo = requestParams.getString("reqStextSeqNo");
        Timestamp startDate = requestParams.getTimestamp("startDate");
        Timestamp endDate = requestParams.getTimestamp("endDate");

        QSh04001110 qSh04001110 = QSh04001110.sh04001110;

        BooleanBuilder builder = new BooleanBuilder();

        if (isNotEmpty(reqStextSeqNo)) {
            builder.and(qSh04001110.reqStextSeqNo.eq(reqStextSeqNo));
        }

        if (startDate != null && endDate != null) {
            builder.and(qSh04001110.reqStextDate.between(startDate, endDate));
        }

        List<Sh04001110> resultList = select().from(qSh04001110).where(builder).fetch();

        return filter(resultList, pageable, "", Sh04001110.class);
    }

    public List<Sh04001110> find(RequestParams<Sh04001110VO> requestParams) {

        String reqStextSeqNo = requestParams.getString("reqStextSeqNo");
        Timestamp startDate = requestParams.getTimestamp("startDate");
        Timestamp endDate = requestParams.getTimestamp("endDate");

        QSh04001110 qSh04001110 = QSh04001110.sh04001110;

        BooleanBuilder builder = new BooleanBuilder();

        if (isNotEmpty(reqStextSeqNo)) {
            builder.and(qSh04001110.reqStextSeqNo.eq(reqStextSeqNo));
        }

        if (startDate != null && endDate != null) {
            builder.and(qSh04001110.reqStextDate.between(startDate, endDate));
        }

        List<Sh04001110> resultList = select().from(qSh04001110).where(builder).fetch();

        return resultList;
    }

    public ApiResponse stextSend(Sh04001110VO vo) {

        if (vo.getReqStextDate().contains("-")) {
            vo.setReqStextDate(vo.getReqStextDate().replace("-", ""));
        }

        ResponseEntity<ApiResponse> apiResponseEntity;

        try {
            apiResponseEntity = restTemplate.postForEntity(url, vo, ApiResponse.class);
        } catch (RestClientException e) {
            log.error("Sh04001110Service-sendAndReceive :: {}", e.getMessage());
            throw new ApiException(ApiStatus.SYSTEM_ERROR, "전문서버가 응답하지 않습니다. 관리자에게 문의하시기 바랍니다.");
        }

        return apiResponseEntity.getBody();
    }
}