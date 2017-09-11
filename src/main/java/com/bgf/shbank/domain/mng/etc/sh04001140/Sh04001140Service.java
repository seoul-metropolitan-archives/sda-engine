package com.bgf.shbank.domain.mng.etc.sh04001140;

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
public class Sh04001140Service extends BaseService<Sh04001140, Sh04001140.Sh04001140Id> {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${msg.exchange.gateway.batch.ip}")
    private String ip;

    @Value("${msg.exchange.gateway.batch.port}")
    private String port;

    private String url;

    @PostConstruct
    private void buildUrl() {
        url = "http://" + ip + ":" + port + "/api/v1/mng/etc/sh04001140";
    }

    @Inject
    public Sh04001140Service(Sh04001140Repository sh04001140Repository) {
        super(sh04001140Repository);
    }

    public Page<Sh04001140> find(Pageable pageable, String filter) {
        return filter(findAll(), pageable, filter, Sh04001140.class);
    }

    public Page<Sh04001140> find(Pageable pageable, RequestParams<Sh04001140> requestParams) {

        String filter = requestParams.getString("filter");
        String jisaCode = requestParams.getString("jisaCode");
        String branchCode = requestParams.getString("branchCode");
        String cornerCode = requestParams.getString("cornerCode");
        String terminalNo = requestParams.getString("terminalNo");

        QSh04001140 qSh04001140 = QSh04001140.sh04001140;

        BooleanBuilder builder = new BooleanBuilder();

        if (isNotEmpty(jisaCode)) {
            builder.and(qSh04001140.jisaCode.eq(jisaCode));
        }

        if (isNotEmpty(branchCode)) {
            builder.and(qSh04001140.branchCode.eq(branchCode));
        }

        if (isNotEmpty(cornerCode)) {
            builder.and(qSh04001140.cornerCode.eq(cornerCode));
        }

        if (isNotEmpty(terminalNo)) {
            builder.and(qSh04001140.terminalNo.eq(terminalNo));
        }

        List<Sh04001140> resultList = select().from(qSh04001140).where(builder).fetch();

        return filter(resultList, pageable, filter, Sh04001140.class);
    }


    public List<Sh04001140> find(RequestParams<Sh04001140VO> requestParams) {

        String filter = requestParams.getString("filter");
        String jisaCode = requestParams.getString("jisaCode");
        String branchCode = requestParams.getString("branchCode");
        String cornerCode = requestParams.getString("cornerCode");
        String terminalNo = requestParams.getString("terminalNo");

        QSh04001140 qSh04001140 = QSh04001140.sh04001140;

        BooleanBuilder builder = new BooleanBuilder();

        if (isNotEmpty(jisaCode)) {
            builder.and(qSh04001140.jisaCode.eq(jisaCode));
        }

        if (isNotEmpty(branchCode)) {
            builder.and(qSh04001140.branchCode.eq(branchCode));
        }

        if (isNotEmpty(cornerCode)) {
            builder.and(qSh04001140.cornerCode.eq(cornerCode));
        }

        if (isNotEmpty(terminalNo)) {
            builder.and(qSh04001140.terminalNo.eq(terminalNo));
        }

        List<Sh04001140> resultList = select().from(qSh04001140).where(builder).fetch();

        return resultList;
    }

    public ApiResponse sendAndReceive(Sh04001140VO vo) {

        if (vo.getOverhaulDate().contains("-")) {
            vo.setOverhaulDate(vo.getOverhaulDate().replace("-", ""));
        }

        if (vo.getOverhaulStartTime().contains(":")) {
            vo.setOverhaulStartTime(vo.getOverhaulStartTime().replace(":", ""));
        }

        if (vo.getOverhaulEndTime().contains(":")) {
            vo.setOverhaulEndTime(vo.getOverhaulEndTime().replace(":", ""));
        }

        if (vo.getModelCode().length() == 4) {
            vo.setModelCode(vo.getModelCode().substring(0, 3));
        }

        ResponseEntity<ApiResponse> apiResponseEntity;

        try {
            apiResponseEntity = restTemplate.postForEntity(url, vo, ApiResponse.class);
        } catch (RestClientException e) {
            log.error("Sh04001140Service-sendAndReceive :: {}", e.getMessage());
            throw new ApiException(ApiStatus.SYSTEM_ERROR, "전문서버가 응답하지 않습니다. 관리자에게 문의하시기 바랍니다.");
        }

        return apiResponseEntity.getBody();
    }
}