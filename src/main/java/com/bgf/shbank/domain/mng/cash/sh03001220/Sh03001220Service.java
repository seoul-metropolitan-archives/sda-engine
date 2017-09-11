package com.bgf.shbank.domain.mng.cash.sh03001220;

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
public class Sh03001220Service extends BaseService<Sh03001220, Sh03001220.Sh03001220Id> {

    @Inject
    public Sh03001220Mapper sh03001220Mapper;

    @Autowired
    private Sh03001220Repository sh003001220Repo;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${msg.exchange.gateway.online.ip}")
    private String ip;

    @Value("${msg.exchange.gateway.online.port}")
    private String port;

    private String url;

    @PostConstruct
    private void buildUrl() {
        url = "http://" + ip + ":" + port + "/api/v1/mng/cash/sh03001220";
    }

    @Inject
    public Sh03001220Service(Sh03001220Repository sh03001220Repository) {
        super(sh03001220Repository);
    }

    public Page<Sh03001220> find(Pageable pageable, RequestParams<Sh03001220> requestParams) {

        String filter = requestParams.getString("filter");

        Sh03001220 sh03001220 = new Sh03001220();

        sh03001220.setJisaCode(requestParams.getString("jisaCode"));
        sh03001220.setReqGubun(requestParams.getString("reqGubun"));
        sh03001220.setReqDate(requestParams.getTimestamp("reqDate"));

        List<Sh03001220> resultList = sh03001220Mapper.findAll(sh03001220);

        return filter(resultList, pageable, filter, Sh03001220.class);
    }

    public ApiResponse stextSend(Sh03001220VO vo) {
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