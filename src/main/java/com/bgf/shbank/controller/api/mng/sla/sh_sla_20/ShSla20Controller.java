package com.bgf.shbank.controller.api.mng.sla.sh_sla_20;

import com.bgf.shbank.domain.mng.sla.sh_sla_20.ShSla20;
import com.bgf.shbank.domain.mng.sla.sh_sla_20.ShSla20Service;
import com.bgf.shbank.domain.mng.sla.sh_sla_20.ShSla20VO;
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
@RequestMapping(value = "/api/v1/mng/sla/sh_sla_20")
public class ShSla20Controller extends BaseController {

    @Value("${onsemiro.upload.repository.sla}")
    private String filePath;

    @Inject
    private ShSla20Service shSla20Service;

    @GetMapping
    public Responses.PageResponse list(Pageable pageable, RequestParams<ShSla20VO> requestParams) {
        Page<ShSla20> pages = shSla20Service.find(pageable, requestParams);
        return Responses.PageResponse.of(ShSla20VO.of(pages.getContent()), pages);
    }

    @PutMapping
    @PostMapping
    public ApiResponse save(@RequestBody List<ShSla20VO> requestParams) {
        List<ShSla20> request = Lists.newArrayList();
        ShSla20 shSla20 = null;

        for (ShSla20VO shSla20VO : requestParams) {
            shSla20 = ModelMapperUtils.map(shSla20VO, ShSla20.class);

            request.add(shSla20);
        }
        shSla20Service.save(request);
        return ok();
    }

    @PutMapping(value = "/writeSla")
    @PostMapping
    public ApiResponse saveFile(@RequestBody List<ShSla20VO> requestParams) throws IOException {

        StringBuilder slaStrList = new StringBuilder();

        for (ShSla20VO shSla20VO : requestParams) {
            StringJoiner slaString = new StringJoiner("|");
            slaString.add(shSla20VO.getTxId()); // 기기번호

            String[] errorDatetimeUnit = shSla20VO.getErrorDatetime().split(" ");
            slaString.add(errorDatetimeUnit[0].replace("-", "")); // 장애발생일
            slaString.add(errorDatetimeUnit[1].replace(":", "") + "0000"); // 장애발생시간

            slaString.add(shSla20VO.getCorpCode()); // 업체코드
            slaString.add(shSla20VO.getBranchCode()); // 지점코드
            slaString.add(shSla20VO.getBranchName()); // 지점명
            slaString.add(shSla20VO.getCornerCode()); // 코너코드
            slaString.add(shSla20VO.getCornerName()); // 코너명
            slaString.add(shSla20VO.getTerminalNo()); // 단말번호

            String[] recoveryDatetimeUnit = shSla20VO.getRecoveryDatetime().split(" ");
            slaString.add(recoveryDatetimeUnit[0].replace("-", "")); // 복구일
            slaString.add(recoveryDatetimeUnit[1].replace(":", "")); // 복구시간

            slaString.add(shSla20VO.getElapsedTime()); // 경과시간
            slaString.add(shSla20VO.getElapsedSeconds()); // 경과시간(초)
            slaString.add(shSla20VO.getErrorContent()); // 장애내용
            slaString.add(shSla20VO.getModelName()); // 모델명
            slaString.add(shSla20VO.getAccept()); // 인정유무
            slaString.add(shSla20VO.getRefuseReason()); // 불인정사유

            slaStrList.append(slaString.toString().replace("\n", "").replace("\r", ""));
            slaStrList.append("\n");
        }

        File slaFile = FileUtils.getFile(filePath + File.separator + DateUtils.convertToString(LocalDateTime.now().minusMonths(1), "yyyyMM") + "_26_0521");
        FileUtils.write(slaFile, slaStrList.toString().replace("null", "-"));
        return ok();
    }

}