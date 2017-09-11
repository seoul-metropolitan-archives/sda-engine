package com.bgf.shbank.domain.mng.cash.sh03001200;

import com.bgf.shbank.utils.ModelMapperUtils;
import com.querydsl.core.BooleanBuilder;
import io.onsemiro.core.api.ApiException;
import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.code.ApiStatus;
import io.onsemiro.core.domain.BaseService;
import io.onsemiro.core.parameter.RequestParams;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.BoundMapperFacade;
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
public class Sh03001200Service extends BaseService<Sh03001200, Sh03001200.Sh03001200Id> {

    @Inject
    public Sh03001200Service(Sh03001200Repository sh03001200Repository) {
        super(sh03001200Repository);
    }

    @Autowired
    private RestTemplate restTemplate;

    @Value("${msg.exchange.gateway.batch.ip}")
    private String ip;

    @Value("${msg.exchange.gateway.batch.port}")
    private String port;

    private String url;

    @PostConstruct
    private void buildUrl() {
        url = "http://" + ip + ":" + port + "/api/v1/mng/cash/sh03001200";
    }

    public ApiResponse sendAndReceive(Sh03001200VO vo) {
        ResponseEntity<ApiResponse> apiResponseEntity;

        try {
            Sh03001200VO sh03001200VO = ModelMapperUtils.map(vo, Sh03001200VO.class);

            apiResponseEntity = restTemplate.postForEntity(url, sh03001200VO, ApiResponse.class);
        } catch (RestClientException e) {
            log.error("sh03001200Service-sendAndReceive :: {}", e.getMessage());
            throw new ApiException(ApiStatus.SYSTEM_ERROR, "전문서버가 응답하지 않습니다. 관리자에게 문의하시기 바랍니다.");
        }

        return apiResponseEntity.getBody();
    }

    public Page<Sh03001200> find(Pageable pageable, String filter) {
        return filter(findAll(), pageable, filter, Sh03001200.class);
    }

    public Sh03001200VO findOne(RequestParams<Sh03001200VO> requestParams) {

        String jisaCode = requestParams.getString("jisaCode");
        String branchCode = requestParams.getString("branchCode");
        String terminalNo = requestParams.getString("terminalNo");

        QSh03001200 qSh03001200 = QSh03001200.sh03001200;

        BooleanBuilder builder = new BooleanBuilder();

        if (isNotEmpty(jisaCode)) {
            builder.and(qSh03001200.jisaCode.eq(jisaCode));
        }

        if (isNotEmpty(branchCode)) {
            builder.and(qSh03001200.branchCode.eq(branchCode));
        }

        if (isNotEmpty(terminalNo)) {
            builder.and(qSh03001200.terminalNo.eq(terminalNo));
        }

        List<Sh03001200> resultList = select().from(qSh03001200).where(builder).fetch();
        if (resultList.isEmpty()) {
            return null;
        }
        return buildVO(resultList.get(0));
    }

    private Sh03001200VO buildVO(Sh03001200 sh03001200) {

        if (sh03001200 == null) {
            return new Sh03001200VO();
        } else {
            BoundMapperFacade<Sh03001200, Sh03001200VO> mapper =
                    ModelMapperUtils.getMapper("Sh03001200", this.getClass().getPackage().getName());
            return mapper.map(sh03001200);
        }
    }
}