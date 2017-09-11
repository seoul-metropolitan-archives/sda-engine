package com.bgf.shbank.domain.mng.equip.sh02001290;

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
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.sql.Timestamp;
import java.util.List;

@Slf4j
@Service
public class Sh02001290Service extends BaseService<Sh02001290, Timestamp> {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${msg.exchange.gateway.online.ip}")
    private String ip;

    @Value("${msg.exchange.gateway.online.port}")
    private String port;

    private String url;

    @PostConstruct
    private void buildUrl() {
        url = "http://" + ip + ":" + port + "/api/v1/mng/equip/sh02001290";
    }

    @Inject
    public Sh02001290Service(Sh02001290Repository sh02001290Repository) {
        super(sh02001290Repository);
    }

    public Page<Sh02001290> find(Pageable pageable, String filter) {
        return filter(findAll(), pageable, filter, Sh02001290.class);
    }

    public Page<Sh02001290> find(Pageable pageable, RequestParams<Sh02001290> requestParams) {

        String filter = requestParams.getString("filter");
        String jisaCode = requestParams.getString("jisaCode");
        String branchCode = requestParams.getString("branchCode");
        Timestamp startDate = requestParams.getTimestamp("startDate");
        Timestamp endDate = requestParams.getTimestamp("endDate");

        QSh02001290 qSh02001290 = QSh02001290.sh02001290;

        BooleanBuilder builder = new BooleanBuilder();

        if (isNotEmpty(jisaCode)) {
            builder.and(qSh02001290.jisaCode.eq(jisaCode));
        }

        if (isNotEmpty(branchCode)) {
            builder.and(qSh02001290.branchCode.eq(branchCode));
        }

        if (startDate != null && endDate != null) {
            builder.and(qSh02001290.workDate.between(startDate, endDate));
        }

        OrderSpecifier<Timestamp> sortOrder = qSh02001290.txId.desc();

        List<Sh02001290> resultList = select().from(qSh02001290).where(builder).orderBy(sortOrder).fetch();

        return filter(resultList, pageable, filter, Sh02001290.class);
    }

    public List<Sh02001290> find(RequestParams<Sh02001290VO> requestParams) {

        String filter = requestParams.getString("filter");
        String jisaCode = requestParams.getString("jisaCode");
        String branchCode = requestParams.getString("branchCode");
        Timestamp startDate = requestParams.getTimestamp("startDate");
        Timestamp endDate = requestParams.getTimestamp("endDate");

        QSh02001290 qSh02001290 = QSh02001290.sh02001290;

        BooleanBuilder builder = new BooleanBuilder();

        if (isNotEmpty(jisaCode)) {
            builder.and(qSh02001290.jisaCode.eq(jisaCode));
        }

        if (isNotEmpty(branchCode)) {
            builder.and(qSh02001290.branchCode.eq(branchCode));
        }

        if (startDate != null && endDate != null) {
            builder.and(qSh02001290.workDate.between(startDate, endDate));
        }

        List<Sh02001290> resultList = select().from(qSh02001290).where(builder).fetch();

        return resultList;
    }

    public ApiResponse sendAndReceive(Sh02001290VO vo) {

        if (vo.getBillingMonth().length() == 8) {
            vo.setBillingMonth(vo.getBillingMonth().substring(0,6));
        }

        if (vo.getInspectionMonth().length() == 8) {
            vo.setInspectionMonth(vo.getInspectionMonth().substring(0,6));
        }
        if (vo.getTxId() == null) {

            String now = com.bgf.shbank.utils.DateUtils.getNow("yyyyMMdd HHmmss");

            vo.setTxId(now);
        }

        String billingAmt = vo.getBillingAmt();
        if (!StringUtils.isEmpty(billingAmt) && billingAmt.contains(",")) {
            billingAmt = StringUtils.replace(billingAmt, ",", "");
            vo.setBillingAmt(billingAmt);
        }

        ResponseEntity<ApiResponse> apiResponseEntity;

        try {
            apiResponseEntity = restTemplate.postForEntity(url, vo, ApiResponse.class);
        } catch (RestClientException e) {
            log.error("Sh02001290Service-sendAndReceive :: {}", e.getMessage());
            throw new ApiException(ApiStatus.SYSTEM_ERROR, "전문서버가 응답하지 않습니다. 관리자에게 문의하시기 바랍니다.");
        }

        return apiResponseEntity.getBody();
    }
}