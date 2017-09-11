package com.bgf.shbank.controller.api.mng.sla.sh_sla_10;

import com.bgf.shbank.domain.mng.sla.sh_sla_10.ShSla10;
import com.bgf.shbank.domain.mng.sla.sh_sla_10.ShSla10Service;
import com.bgf.shbank.domain.mng.sla.sh_sla_10.ShSla10VO;
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
@RequestMapping(value = "/api/v1/mng/sla/sh_sla_10")
public class ShSla10Controller extends BaseController {

    @Value("${onsemiro.upload.repository.sla}")
    private String filePath;

    @Inject
    private ShSla10Service shSla10Service;

    @GetMapping
    public Responses.PageResponse list(Pageable pageable, RequestParams<ShSla10VO> requestParams) {
        Page<ShSla10> pages = shSla10Service.find(pageable, requestParams);
        return Responses.PageResponse.of(ShSla10VO.of(pages.getContent()), pages);
    }

    @PutMapping
    @PostMapping
    public ApiResponse save(@RequestBody List<ShSla10VO> requestParams) {
        List<ShSla10> request = Lists.newArrayList();
        ShSla10 shSla10 = null;

        for (ShSla10VO shSla10VO : requestParams) {
            shSla10 = ModelMapperUtils.map(shSla10VO, ShSla10.class);
            request.add(shSla10);
        }
        shSla10Service.save(request);
        return ok();
    }

    @PutMapping(value = "/writeSla")
    @PostMapping
    public ApiResponse saveFile(@RequestBody List<ShSla10VO> requestParams) throws IOException {

        StringBuilder slaStrList = new StringBuilder();

        for (ShSla10VO shSla10VO : requestParams) {
            StringJoiner slaString = new StringJoiner("|");
            slaString.add(shSla10VO.getTxId()); // 기기번호

            String[] errorDatetimeUnit = shSla10VO.getErrorDatetime().split(" ");
            slaString.add(errorDatetimeUnit[0].replace("-", "")); // 장애발생일
            slaString.add(errorDatetimeUnit[1].replace(":", "") + "0000"); // 장애발생시간

            slaString.add(shSla10VO.getCalleeChasu().replace(" ", "")); // 출동차수
            slaString.add(shSla10VO.getCalleeGubun().replace(" ", "")); // 출동구분
            slaString.add(shSla10VO.getBranchCode()); // 지점코드
            slaString.add(shSla10VO.getBranchName()); // 지점명
            slaString.add(shSla10VO.getCornerCode()); // 코너코드
            slaString.add(shSla10VO.getCornerName()); // 코너명
            slaString.add(shSla10VO.getTerminalNo()); // 단말번호
            slaString.add(shSla10VO.getSecurityCorp()); // 출동업체
            slaString.add(shSla10VO.getCalleeReqReason()); // 출동내용

            String[] calleeReqDatetimeUnit = shSla10VO.getCalleeReqDatetime().split(" ");
            slaString.add(calleeReqDatetimeUnit[0].replace("-", "")); // 출동요청일
            slaString.add(calleeReqDatetimeUnit[1].replace(":", "")); // 출동요청시간

            String[] arrivalDatetimeUnit = shSla10VO.getArrivalDatetime().split(" ");
            slaString.add(arrivalDatetimeUnit[0].replace("-", "")); // 도착일
            slaString.add(arrivalDatetimeUnit[1].replace(":", "")); // 도착시간
            slaString.add(shSla10VO.getElapsedTime()); // 경과시간
            slaString.add(shSla10VO.getReport()); // 처리내용
            slaString.add(shSla10VO.getAccept()); // 인정유무
            slaString.add(shSla10VO.getRefuseReason()); // 불인정사유

            slaStrList.append(slaString.toString().replace("\n", "").replace("\r", ""));
            slaStrList.append("\n");
        }

        File slaFile = FileUtils.getFile(filePath + File.separator + DateUtils.convertToString(LocalDateTime.now().minusMonths(1), "yyyyMM") + "_26_0511");
        FileUtils.write(slaFile, slaStrList.toString().replace("null", "-"));
        return ok();
    }

}