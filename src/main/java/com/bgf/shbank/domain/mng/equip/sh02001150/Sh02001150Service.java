package com.bgf.shbank.domain.mng.equip.sh02001150;

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
import java.util.List;

@Slf4j
@Service
public class Sh02001150Service extends BaseService<Sh02001150, String> {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${msg.exchange.gateway.online.ip}")
    private String ip;

    @Value("${msg.exchange.gateway.online.port}")
    private String port;

    private String url;

    @PostConstruct
    private void buildUrl() {
        url = "http://" + ip + ":" + port + "/api/v1/mng/equip/sh02001150";
    }
    
    @Inject
    public Sh02001150Service(Sh02001150Repository sh02001150Repository) {
        super(sh02001150Repository);
    }

    public Page<Sh02001150> find(Pageable pageable, String filter) {
        return filter(findAll(), pageable, filter, Sh02001150.class);
    }


    public Page<Sh02001150> find(Pageable pageable, RequestParams<Sh02001150> requestParams) {

        String jisaCode = requestParams.getString("jisaCode");
        String branchCode = requestParams.getString("branchCode");
        String cornerCode = requestParams.getString("cornerCode");
        String terminalNo = requestParams.getString("terminalNo");

        QSh02001150 qSh02001150 = QSh02001150.sh02001150;

        BooleanBuilder builder = new BooleanBuilder();

        if (isNotEmpty(branchCode)) {
            builder.and(qSh02001150.branchCode.eq(branchCode));
        }

        if (isNotEmpty(jisaCode)) {
            builder.and(qSh02001150.jisaCode.eq(jisaCode));
        }

        if (isNotEmpty(cornerCode)) {
            builder.and(qSh02001150.cornerCode.eq(cornerCode));
        }

        if (isNotEmpty(terminalNo)) {
            builder.and(qSh02001150.terminalNo.eq(terminalNo));
        }

        List<Sh02001150> resultList = select().from(qSh02001150).where(builder).fetch();

        return filter(resultList, pageable, "", Sh02001150.class);
    }

    public ApiResponse sendAndReceive(Sh02001150VO vo) {

        if (vo.getWorkCompleteDate().contains("-")) {
            vo.setWorkCompleteDate(vo.getWorkCompleteDate().replace("-", ""));
        }

        if (vo.getModelCode().length() == 4) {
            vo.setModelCode(vo.getModelCode().substring(1,4));
        }

        ResponseEntity<ApiResponse> apiResponseEntity;

        try {
            apiResponseEntity = restTemplate.postForEntity(url, vo, ApiResponse.class);
        } catch (RestClientException e) {
            log.error("Sh02001150Service-sendAndReceive :: {}", e.getMessage());
            throw new ApiException(ApiStatus.SYSTEM_ERROR, "전문서버가 응답하지 않습니다. 관리자에게 문의하시기 바랍니다.");
        }

        return apiResponseEntity.getBody();
    }


    public List<Sh02001150> find(RequestParams<Sh02001150VO> requestParams) {

        String jisaCode = requestParams.getString("jisaCode");
        String branchCode = requestParams.getString("branchCode");
        String cornerCode = requestParams.getString("cornerCode");
        String terminalNo = requestParams.getString("terminalNo");

        QSh02001150 qSh02001150 = QSh02001150.sh02001150;

        BooleanBuilder builder = new BooleanBuilder();

        if (isNotEmpty(branchCode)) {
            builder.and(qSh02001150.branchCode.eq(branchCode));
        }

        if (isNotEmpty(jisaCode)) {
            builder.and(qSh02001150.jisaCode.eq(jisaCode));
        }

        if (isNotEmpty(cornerCode)) {
            builder.and(qSh02001150.cornerCode.eq(cornerCode));
        }

        if (isNotEmpty(terminalNo)) {
            builder.and(qSh02001150.terminalNo.eq(terminalNo));
        }

        List<Sh02001150> resultList = select().from(qSh02001150).where(builder).fetch();

        return resultList;
    }

}