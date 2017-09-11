package com.bgf.shbank.controller.api.mng.sla.sh_sla_g1;

import com.bgf.shbank.domain.mng.sla.sh_sla_g1.ShSlaG1;
import com.bgf.shbank.domain.mng.sla.sh_sla_g1.ShSlaG1Service;
import com.bgf.shbank.domain.mng.sla.sh_sla_g1.ShSlaG1VO;
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
@RequestMapping(value = "/api/v1/mng/sla/sh_sla_g1")
public class ShSlaG1Controller extends BaseController {

    @Value("${onsemiro.upload.repository.sla}")
    private String filePath;

    @Inject
    private ShSlaG1Service shSlaG1Service;

    @GetMapping
    public Responses.PageResponse list(Pageable pageable, RequestParams<ShSlaG1VO> requestParams) {
        Page<ShSlaG1> pages = shSlaG1Service.find(pageable, requestParams);
        return Responses.PageResponse.of(ShSlaG1VO.of(pages.getContent()), pages);
    }

    @PutMapping
    @PostMapping
    public ApiResponse save(@RequestBody List<ShSlaG1VO> requestParams) {
        List<ShSlaG1> request = Lists.newArrayList();
        ShSlaG1 shSlaG1 = null;

        boolean isDelete = false;

        for (ShSlaG1VO shSlaG1VO : requestParams) {
            if (shSlaG1VO.isDeleted()) {
                isDelete = true;
            }

            shSlaG1VO.setTxDate(shSlaG1VO.getWithdrawDate() + " " + shSlaG1VO.getWithdrawTime());
            shSlaG1VO.setWithdrawDatetime(shSlaG1VO.getWithdrawDate() + " " + shSlaG1VO.getWithdrawTime());
            shSlaG1VO.setReturnDatetime(shSlaG1VO.getReturnDate() + " " + shSlaG1VO.getReturnTime());
            shSlaG1VO.setTransferDatetime(shSlaG1VO.getTransferDate() + " " + shSlaG1VO.getTransferTime());

            if (shSlaG1VO.getRealNo().contains("-") && shSlaG1VO.getRealNo().length() == 19) {
                String [] nums = shSlaG1VO.getRealNo().split("-");
                if (nums.length == 4) {
                    String realNo = String.format("%s-****-%s-%s", nums[0], nums[1], nums[2]);
                    shSlaG1VO.setRealNo(realNo);
                }
            } else if (shSlaG1VO.getRealNo().length() == 16) {
                String realNo1 = shSlaG1VO.getRealNo().substring(0, 3);
                String realNo2 = shSlaG1VO.getRealNo().substring(8);
                String realNo = String.format("%s****%s", realNo1, realNo2);
                shSlaG1VO.setRealNo(realNo);
            }

            shSlaG1 = ModelMapperUtils.map(shSlaG1VO, ShSlaG1.class);
            request.add(shSlaG1);
        }

        if (isDelete) {
            shSlaG1Service.delete(request);
        } else {
            shSlaG1Service.save(request);
        }


        return ok();
    }

    @PutMapping(value = "/writeSla")
    @PostMapping
    public ApiResponse saveFile(@RequestBody List<ShSlaG1VO> requestParams) throws IOException {

        StringBuilder slaStrList = new StringBuilder();

        for (ShSlaG1VO shSlaG1VO : requestParams) {
            StringJoiner slaString = new StringJoiner("|");


            if (shSlaG1VO.getRealNo().contains("-") && shSlaG1VO.getRealNo().length() == 19) {
                String [] nums = shSlaG1VO.getRealNo().split("-");
                if (nums.length == 4) {
                    String realNo = String.format("%s-****-%s-%s", nums[0], nums[1], nums[2]);
                    shSlaG1VO.setRealNo(realNo);
                }
            } else if (shSlaG1VO.getRealNo().length() == 16) {
                String realNo1 = shSlaG1VO.getRealNo().substring(0, 3);
                String realNo2 = shSlaG1VO.getRealNo().substring(8);
                String realNo = String.format("%s****%s", realNo1, realNo2);
                shSlaG1VO.setRealNo(realNo);
            }

            String[] withdrawDatetimeUnit = shSlaG1VO.getWithdrawDatetime().split(" ");
            slaString.add(withdrawDatetimeUnit[0].replace("-", "")); // 회수일
            slaString.add(withdrawDatetimeUnit[1].replace(":", "")); // 회수시간

            slaString.add(shSlaG1VO.getRealClassify()); // 실물종류
            slaString.add(shSlaG1VO.getRealNo()); // 실물번호
            slaString.add(shSlaG1VO.getIssueOrg()); // 발행기관
            slaString.add(shSlaG1VO.getWithdrawOrg()); // 회수기관
            slaString.add(shSlaG1VO.getTxId()); // 기기번호
            slaString.add(shSlaG1VO.getProgressStatus()); // 진행현황
            slaString.add(shSlaG1VO.getWithdrawEmpName()); // 회수자
            slaString.add(shSlaG1VO.getTransferEmpName()); // 인계자
            slaString.add(shSlaG1VO.getTakeoverEmpName()); // 인수자
            slaString.add(shSlaG1VO.getReturnEmpName()); // 반환자
            slaString.add(shSlaG1VO.getReceiveEmpName()); // 수령자

            String[] transferDatetimeUnit = shSlaG1VO.getTransferDatetime().split(" ");
            slaString.add(transferDatetimeUnit[0].replace("-", "")); // 인계일
            slaString.add(transferDatetimeUnit[1].replace(":", "")); // 인계시간

            String[] returnDatetimeUnit = shSlaG1VO.getReturnDatetime().split(" ");
            slaString.add(returnDatetimeUnit[0].replace("-", "")); // 반환일
            slaString.add(returnDatetimeUnit[1].replace(":", "")); // 반환시간

            slaString.add(shSlaG1VO.getStorageUnusl()); // 보관사유/ 메모

            slaStrList.append(slaString.toString());
            slaStrList.append("\n");
        }

        File slaFile = FileUtils.getFile(filePath + File.separator + DateUtils.convertToString(LocalDateTime.now(), "yyyyMMdd") + "_26_05G1");
        FileUtils.write(slaFile, slaStrList.toString().replace("null", " "));
        return ok();
    }

}