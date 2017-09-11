package com.bgf.shbank.domain.mng.error.sh01001160;

import com.bgf.shbank.domain.mng.error.error_status.ErrorProcessStatus;
import com.bgf.shbank.domain.mng.error.error_status.ErrorStatus;
import com.bgf.shbank.domain.mng.error.error_status.ErrorStatusRepository;
import com.bgf.shbank.utils.DateUtils;
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
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class Sh01001160Service extends BaseService<Sh01001160, Sh01001160.Sh01001160Id> {

    @Inject
    public Sh01001160Service(Sh01001160Repository sh01001160Repository) {
        super(sh01001160Repository);
    }

    @Autowired
    private ErrorStatusRepository errorStatusRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${msg.exchange.gateway.online.ip}")
    private String ip;

    @Value("${msg.exchange.gateway.online.port}")
    private String port;

    private String url;

    @PostConstruct
    private void buildUrl() {
        url = "http://" + ip + ":" + port + "/api/v1/mng/error/sh01001160";
    }

    public ApiResponse sendAndReceive(Sh01001160VO vo) {
        ResponseEntity<ApiResponse> apiResponseEntity;

        try {
            apiResponseEntity = restTemplate.postForEntity(url, vo, ApiResponse.class);
        } catch (RestClientException e) {
            log.error("Sh01001160Service-sendAndReceive :: {}", e.getMessage());
            throw new ApiException(ApiStatus.SYSTEM_ERROR, "전문서버가 응답하지 않습니다. 관리자에게 문의하시기 바랍니다.");
        }

        return apiResponseEntity.getBody();
    }


    public Page<Sh01001160> find(Pageable pageable, RequestParams<Sh01001160VO> requestParams) {
        String filter = requestParams.getString("filter");
        String jisaCode = requestParams.getString("jisaCode");
        String branchCode = requestParams.getString("branchCode");
        String cornerCode = requestParams.getString("cornerCode");
        String terminalNo = requestParams.getString("terminalNo");
        Timestamp startDate = requestParams.getTimestamp("startDate");
        Timestamp endDate = requestParams.getTimestamp("endDate");

        QSh01001160 qSh01001160 = QSh01001160.sh01001160;

        BooleanBuilder builder = new BooleanBuilder();

        if (isNotEmpty(jisaCode)) {
            builder.and(qSh01001160.jisaCode.eq(jisaCode));
        }

        if (isNotEmpty(branchCode)) {
            builder.and(qSh01001160.branchCode.eq(branchCode));
        }

        if (isNotEmpty(cornerCode)) {
            builder.and(qSh01001160.cornerCode.eq(cornerCode));
        }

        if (isNotEmpty(terminalNo)) {
            builder.and(qSh01001160.terminalNo.eq(terminalNo));
        }

        if (startDate != null && endDate != null) {
            builder.and(qSh01001160.errorDatetime.between(startDate, endDate));
        }

        OrderSpecifier<Timestamp> sortOrder = qSh01001160.errorDatetime.desc();
        List<Sh01001160> resultList = select().from(qSh01001160).where(builder).orderBy(sortOrder).fetch();

        return filter(resultList, pageable, filter, Sh01001160.class);
    }

    public void cancelCurrentErrorStatus(Sh01001160VO vo) {
        Sh01001160 entity = ModelMapperUtils.map(vo, Sh01001160.class);
        entity.setErrorDatetime(DateUtils.convertToTimestamp(vo.getErrorDatetime(), "yyyy-MM-dd HH:mm:ss"));

        ErrorStatus.ErrorStatusId id = new ErrorStatus.ErrorStatusId();

        id.setErrorDatetime(entity.getId().getErrorDatetime());
        id.setBranchCode(entity.getId().getBranchCode());
        id.setTerminalNo(entity.getId().getTerminalNo());
        id.setCornerCode(entity.getId().getCornerCode());

        ErrorStatus errorStatus = errorStatusRepository.findOne(id);

        if (errorStatus != null) {
            if (vo.getSelfCalleeGubun().equals("3")) {
                // 조치완료
                errorStatus.setHandleDatetime(Timestamp.valueOf(LocalDateTime.now()));
                errorStatus.setErrorProcessStatus(ErrorProcessStatus.조치완료.getCode());
            } else {
                // 강제취소
                errorStatus.setErrorProcessStatus(ErrorProcessStatus.자체취소.getCode());
            }

            errorStatusRepository.save(errorStatus);
        }
    }
}