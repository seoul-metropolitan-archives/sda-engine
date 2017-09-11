package com.bgf.shbank.controller.api.mng.sla.sh_sla_f0;

import com.bgf.shbank.domain.mng.sla.sh_sla_f0.ShSlaF0;
import com.bgf.shbank.domain.mng.sla.sh_sla_f0.ShSlaF0Service;
import com.bgf.shbank.domain.mng.sla.sh_sla_f0.ShSlaF0VO;
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
@RequestMapping(value = "/api/v1/mng/sla/sh_sla_f0")
public class ShSlaF0Controller extends BaseController {

    @Value("${onsemiro.upload.repository.sla}")
    private String filePath;

    @Inject
    private ShSlaF0Service shSlaF0Service;

    @GetMapping
    public Responses.PageResponse list(Pageable pageable, RequestParams<ShSlaF0VO> requestParams) {
        Page<ShSlaF0> pages = shSlaF0Service.find(pageable, requestParams);
        return Responses.PageResponse.of(ShSlaF0VO.of(pages.getContent()), pages);
    }

    @PutMapping
    @PostMapping
    public ApiResponse save(@RequestBody List<ShSlaF0VO> requestParams) {
        List<ShSlaF0> request = Lists.newArrayList();
        ShSlaF0 shSlaF0 = null;

        for (ShSlaF0VO shSlaF0VO : requestParams) {
            shSlaF0 = ModelMapperUtils.map(shSlaF0VO, ShSlaF0.class);
            request.add(shSlaF0);
        }
        shSlaF0Service.save(request);
        return ok();
    }

    @PutMapping(value = "/writeSla")
    @PostMapping
    public ApiResponse saveFile(@RequestBody List<ShSlaF0VO> requestParams) throws IOException {

        StringBuilder slaStrList = new StringBuilder();

        for (ShSlaF0VO shSlaF0VO : requestParams) {
            StringJoiner slaString = new StringJoiner("|");
            slaString.add(shSlaF0VO.getTxId()); // 기기번호

            String[] errorDatetimeUnit = shSlaF0VO.getErrorDatetime().split(" ");
            slaString.add(errorDatetimeUnit[0].replace("-", "")); // 장애발생일
            slaString.add(errorDatetimeUnit[1].replace(":", "") + "0000"); // 장애발생시간

            slaString.add(shSlaF0VO.getCalleeChasu().replace(" ", "")); // 출동차수
            slaString.add(shSlaF0VO.getCorpCode()); // 업체
            slaString.add(shSlaF0VO.getBranchCode()); // 지점코드
            slaString.add(shSlaF0VO.getBranchName()); // 지점명
            slaString.add(shSlaF0VO.getCornerCode()); // 코너코드
            slaString.add(shSlaF0VO.getCornerName()); // 코너명
            slaString.add(shSlaF0VO.getLocation()); // 위치구분
            slaString.add(shSlaF0VO.getArea()); // 지역구분
            slaString.add(shSlaF0VO.getTerminalNo()); // 단말번호
            slaString.add(shSlaF0VO.getCalleeGubun()); // 출동구분
            slaString.add(shSlaF0VO.getCalleeResult()); // 결과

            String[] calleeReqDatetimeUnit = shSlaF0VO.getCalleeReqDatetime().split(" ");
            slaString.add(calleeReqDatetimeUnit[0].replace("-", "")); // 출동요청일
            slaString.add(calleeReqDatetimeUnit[1].replace(":", "")); // 출동요청시간

            String[] arrivalDatetimeUnit = shSlaF0VO.getArrivalDatetime().split(" ");
            slaString.add(arrivalDatetimeUnit[0].replace("-", "")); // 도착일
            slaString.add(arrivalDatetimeUnit[1].replace(":", "")); // 도착시간

            String[] handleDatetimeUnit = shSlaF0VO.getHandleDatetime().split(" ");
            slaString.add(handleDatetimeUnit[0].replace("-", "")); // 완료일
            slaString.add(handleDatetimeUnit[1].replace(":", "")); // 완료시간

            String[] recoverDatetimeUnit = shSlaF0VO.getRecoverDatetime().split(" ");
            slaString.add(recoverDatetimeUnit[0].replace("-", "")); // 복구일
            slaString.add(recoverDatetimeUnit[1].replace(":", "")); // 복구시간

            slaString.add(shSlaF0VO.getElapsedTime()); // 경과시간
            slaString.add(shSlaF0VO.getElapsedSeconds()); // 경과시간(초)
            slaString.add(shSlaF0VO.getErrorContent()); // 장애내용
            slaString.add(shSlaF0VO.getModelName()); // 기종
            slaString.add(shSlaF0VO.getAccept()); // 인정유무
            slaString.add(shSlaF0VO.getRefuseReason()); // 불인정사유

            slaStrList.append(slaString.toString().replace("\n", "").replace("\r", ""));
            slaStrList.append("\n");
        }

        File slaFile = FileUtils.getFile(filePath + File.separator + DateUtils.convertToString(LocalDateTime.now().minusMonths(1), "yyyyMM") + "_26_05F1");
        FileUtils.write(slaFile, slaStrList.toString().replace("null", "-"));
        return ok();
    }

}