package com.bgf.shbank.controller.api.mng.sla.sh_sla_70;

import com.bgf.shbank.domain.mng.sla.sh_sla_70.ShSla70;
import com.bgf.shbank.domain.mng.sla.sh_sla_70.ShSla70Service;
import com.bgf.shbank.domain.mng.sla.sh_sla_70.ShSla70VO;
import com.bgf.shbank.utils.DateUtils;
import com.bgf.shbank.utils.ModelMapperUtils;
import com.google.common.collect.Lists;
import io.onsemiro.controller.BaseController;
import io.onsemiro.core.api.response.ApiResponse;
import io.onsemiro.core.api.response.Responses;
import io.onsemiro.core.parameter.RequestParams;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.StringJoiner;

@RestController
@RequestMapping(value = "/api/v1/mng/sla/sh_sla_70")
public class ShSla70Controller extends BaseController {

    @Value("${onsemiro.upload.repository.sla}")
    private String filePath;

    @Inject
    private ShSla70Service shSla70Service;

    @GetMapping
    public Responses.PageResponse list(Pageable pageable, RequestParams<ShSla70VO> requestParams) {
        Page<ShSla70> pages = shSla70Service.find(pageable, requestParams);
        return Responses.PageResponse.of(ShSla70VO.of(pages.getContent()), pages);
    }

    @PutMapping
    @PostMapping
    public ApiResponse save(@RequestBody List<ShSla70VO> requestParams) {
        List<ShSla70> request = Lists.newArrayList();
        ShSla70 shSla70 = null;

        for (ShSla70VO shSla70VO : requestParams) {
            shSla70 = ModelMapperUtils.map(shSla70VO, ShSla70.class);
            request.add(shSla70);
        }
        shSla70Service.save(request);
        return ok();
    }

    @PutMapping(value = "/writeSla")
    @PostMapping
    public ApiResponse saveFile(@RequestBody List<ShSla70VO> requestParams) throws IOException {

        StringBuilder slaStrList = new StringBuilder();

        for (ShSla70VO shSla70VO : requestParams) {
            StringJoiner slaString = new StringJoiner("|");
            slaString.add(shSla70VO.getTxId()); // 기기번호

            String[] errorDatetimeUnit = shSla70VO.getErrorDatetime().split(" ");
            slaString.add(errorDatetimeUnit[0].replace("-", "")); // 장애발생일
            slaString.add(errorDatetimeUnit[1].replace(":", "") + "0000"); // 장애발생시간

            slaString.add(shSla70VO.getCalleeChasu().replace(" ", "")); // 출동차수
            slaString.add(shSla70VO.getCustomerWait()); // 고객대기유무
            slaString.add(shSla70VO.getEvalType()); // 평가구분
            slaString.add(shSla70VO.getCorpCode()); // 업체
            slaString.add(shSla70VO.getCalleeType()); // 출동형태
            slaString.add(shSla70VO.getBranchCode()); // 지점코드
            slaString.add(shSla70VO.getBranchName()); // 지점명
            slaString.add(shSla70VO.getCornerCode()); // 코너코드
            slaString.add(shSla70VO.getCornerName()); // 코너명
            slaString.add(shSla70VO.getLocation()); // 위치구분
            slaString.add(shSla70VO.getArea()); // 지역구분
            slaString.add(shSla70VO.getTerminalNo()); // 단말번호
            slaString.add(shSla70VO.getCalleeGubun()); // 출동구분

            String[] calleeReqDatetimeUnit = shSla70VO.getCalleeReqDatetime().split(" ");
            slaString.add(calleeReqDatetimeUnit[0].replace("-", "")); // 출동요청일
            slaString.add(calleeReqDatetimeUnit[1].replace(":", "")); // 출동요청시간

            String[] arrivalPlanDatetimeUnit = shSla70VO.getArrivalPlanDatetime().split(" ");
            slaString.add(arrivalPlanDatetimeUnit[0].replace("-", "")); // 도착예정일
            slaString.add(arrivalPlanDatetimeUnit[1].replace(":", "")); // 도착예정시간

            String[] arrivalDatetimeUnit = shSla70VO.getArrivalDatetime().split(" ");
            slaString.add(arrivalDatetimeUnit[0].replace("-", "")); // 도착일
            slaString.add(arrivalDatetimeUnit[1].replace(":", "")); // 도착시간


            slaString.add(shSla70VO.getArrivalType()); // 도착처리방법
            slaString.add(shSla70VO.getCalleeReqElapsedTime()); // 출동요청시간
            slaString.add(shSla70VO.getCalleeReqElapsedSeconds()); // 출동요청시간(초)
            slaString.add(shSla70VO.getArrivalElapsedTime()); // 출동준수시간
            slaString.add(shSla70VO.getArrivalElapsedSeconds()); // 출동준수시간(초)

            String[] reportDatetimeUnit = shSla70VO.getReportDatetime().split(" ");
            slaString.add(reportDatetimeUnit[0].replace("-", "")); // 도착후업체보고예정일자
            slaString.add(reportDatetimeUnit[1].replace(":", "")); // 도착후업체보고예정시간
            slaString.add(shSla70VO.getErrorContent()); // 장애내용
            slaString.add(shSla70VO.getModelName()); // 기종
            slaString.add(shSla70VO.getAccept()); // 인정유무
            slaString.add(shSla70VO.getRefuseReason()); // 불인정사유

            slaStrList.append(slaString.toString().replace("\n", "").replace("\r", ""));
            slaStrList.append("\n");
        }

        File slaFile = FileUtils.getFile(filePath + File.separator + DateUtils.convertToString(LocalDateTime.now().minusMonths(1), "yyyyMM") + "_26_0571");
        FileUtils.write(slaFile, slaStrList.toString().replace("null", "-"));
        return ok();
    }

}