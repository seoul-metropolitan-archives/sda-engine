
package com.bgf.shbank.domain.mng.error.sh01001120;

import com.bgf.shbank.domain.mng.equip.terminal_status.TerminalStatus;
import com.bgf.shbank.domain.mng.equip.terminal_status.TerminalStatusService;
import com.bgf.shbank.domain.mng.error.error_status.ErrorProcessStatus;
import com.bgf.shbank.domain.mng.error.error_status.ErrorProcessType;
import com.bgf.shbank.domain.mng.error.error_status.ErrorStatus;
import com.bgf.shbank.domain.mng.error.error_status.ErrorStatusRepository;
import com.bgf.shbank.domain.mng.error.sh01001110.Sh01001110;
import com.bgf.shbank.domain.mng.error.sh01001110.Sh01001110Repository;
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
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class Sh01001120Service extends BaseService<Sh01001120, Sh01001120.Sh01001120Id> {

    @Inject
    public Sh01001120Service(Sh01001120Repository sh01001120Repository) {
        super(sh01001120Repository);
    }

    @Autowired
    private Sh01001110Repository sh01001110Repository;

    @Autowired
    private ErrorStatusRepository errorStatusRepo;

    @Autowired
    private TerminalStatusService terminalStatusService;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${msg.exchange.gateway.online.ip}")
    private String ip;

    @Value("${msg.exchange.gateway.online.port}")
    private String port;

    private String url;

    @PostConstruct
    private void buildUrl() {
        url = "http://" + ip + ":" + port + "/api/v1/mng/error/sh01001120";
    }

    public ApiResponse sendAndReceive(Sh01001120VO vo) {
        ResponseEntity<ApiResponse> apiResponseEntity;

        try {
            apiResponseEntity = restTemplate.postForEntity(url, vo, ApiResponse.class);
        } catch (RestClientException e) {
            log.error("Sh01001120Service-sendAndReceive :: {}", e.getMessage());
            throw new ApiException(ApiStatus.SYSTEM_ERROR, "출동요청 전문응답코드가 99입니다.");
        }

        return apiResponseEntity.getBody();
    }

    public List<Sh01001120> find(RequestParams<Sh01001120VO> requestParams) {

        String filter = requestParams.getString("filter");
        String jisaCode = requestParams.getString("jisaCode");
        String branchCode = requestParams.getString("branchCode");
        String cornerCode = requestParams.getString("cornerCode");
        String terminalNo = requestParams.getString("terminalNo");
        Timestamp startDate = requestParams.getTimestamp("startDate");
        Timestamp endDate = requestParams.getTimestamp("endDate");

        QSh01001120 qSh01001120 = QSh01001120.sh01001120;

        BooleanBuilder builder = new BooleanBuilder();

        if (isNotEmpty(jisaCode)) {
            builder.and(qSh01001120.jisaCode.eq(jisaCode));
        }

        if (isNotEmpty(branchCode)) {
            builder.and(qSh01001120.branchCode.eq(branchCode));
        }

        if (isNotEmpty(cornerCode)) {
            builder.and(qSh01001120.cornerCode.eq(cornerCode));
        }

        if (isNotEmpty(terminalNo)) {
            builder.and(qSh01001120.terminalNo.eq(terminalNo));
        }

        if (startDate != null && endDate != null) {
            builder.and(qSh01001120.errorDatetime.between(startDate, endDate));
        }

        OrderSpecifier<Timestamp> sortOrder = qSh01001120.errorDatetime.desc();
        List<Sh01001120> resultList = select().from(qSh01001120).where(builder).orderBy(sortOrder).fetch();

        return filter(resultList, filter);
    }

    public Page<Sh01001120> find(Pageable pageable, RequestParams<Sh01001120VO> requestParams) {

        String filter = requestParams.getString("filter");

        List<Sh01001120> resultList = find(requestParams);

        return filter(resultList, pageable, filter, Sh01001120.class);
    }

    public Sh01001120VO findOne(RequestParams<Sh01001120VO> requestParams) {

        Sh01001120.Sh01001120Id id = new Sh01001120.Sh01001120Id();

        id.setErrorDatetime(requestParams.getTimestamp("errorDatetime"));
        id.setBranchCode(requestParams.getString("branchCode"));
        id.setCornerCode(requestParams.getString("cornerCode"));
        id.setTerminalNo(requestParams.getString("terminalNo"));

        return buildVO(findOne(id));
    }

    private Sh01001120VO buildVO(Sh01001120 sh01001120) {

        if (sh01001120 == null) {
            return new Sh01001120VO();
        } else {
            BoundMapperFacade<Sh01001120, Sh01001120VO> mapper =
                    ModelMapperUtils.getMapper("Sh01001120", this.getClass().getPackage().getName());
            return mapper.map(sh01001120);
        }
    }

    public void saveNewError(Sh01001120VO vo) {
        // 현재시간 생성
        LocalDateTime now = LocalDateTime.now();
        Timestamp errorDatetime = Timestamp.valueOf(DateUtils.convertToString(now, DateUtils.DATE_TIME_PATTERN));

        // 기기정보조회
        TerminalStatus terminalStatusParams = new TerminalStatus();
        terminalStatusParams.setJisaCode(vo.getJisaCode());
        terminalStatusParams.setBranchCode(vo.getBranchCode());
        terminalStatusParams.setCornerCode(vo.getCornerCode());
        terminalStatusParams.setTerminalNo(vo.getTerminalNo());

        TerminalStatus terminalStatus = terminalStatusService.findOne(terminalStatusParams);

        // 장애통보발생 및 저장
        Sh01001110 sh01001110 = new Sh01001110();
        sh01001110.setErrorDatetime(errorDatetime);
        sh01001110.setJisaCode(vo.getJisaCode());
        sh01001110.setBranchCode(vo.getBranchCode());
        sh01001110.setBranchName(vo.getBranchName());
        sh01001110.setCornerCode(vo.getCornerCode());
        sh01001110.setCornerName(vo.getCornerName());
        sh01001110.setTerminalNo(vo.getTerminalNo());
        sh01001110.setPlaceGubun(terminalStatus.getPlaceGubun());
        sh01001110.setOperTimeGubun(terminalStatus.getOperTimeGubun());

        LocalDateTime operStartTime = terminalStatus.getOperStartTime().toLocalDateTime();
        String operStartTimeStr = DateUtils.convertToString(operStartTime, "HH:mm");

        LocalDateTime operEndTime = terminalStatus.getOperEndTime().toLocalDateTime();
        String operEndTimeStr = DateUtils.convertToString(operEndTime, "HH:mm");

        sh01001110.setRuntime(operStartTimeStr + "~" + operEndTimeStr);
        sh01001110.setProdSerialNo(terminalStatus.getTerminalProdNo());
        sh01001110.setTerminalCorpCode(vo.getTerminalCorpCode());
        String modelCode = vo.getModelCode();
        if (modelCode.length() == 4) {
            sh01001110.setTerminalCorpCode(modelCode.substring(0, 1));
            modelCode = modelCode.substring(1);
        }
        sh01001110.setModelCode(modelCode);
        sh01001110.setStextGubun("2");
        sh01001110.setTotalClassifyCode(vo.getTotalClassifyCode());
        sh01001110.setErrorType("2");

        sh01001110Repository.save(sh01001110);

        // 장애모니터링 발생 및 저장
        ErrorStatus errorStatus = new ErrorStatus();

        errorStatus.setJisaCode(vo.getJisaCode());
        errorStatus.setBranchCode(vo.getBranchCode());
        errorStatus.setCornerCode(vo.getCornerCode());
        errorStatus.setTerminalNo(vo.getTerminalNo());
        errorStatus.setErrorDatetime(errorDatetime);
        errorStatus.setErrorType("2");
        errorStatus.setCalleeGubun("2");
        errorStatus.setErrorProcessStatus(ErrorProcessStatus.장애통보.getCode());
        errorStatus.setTxId("SH" + ErrorProcessType.자체출동.getCode() + vo.getTerminalNo());

        errorStatusRepo.save(errorStatus);
    }
}