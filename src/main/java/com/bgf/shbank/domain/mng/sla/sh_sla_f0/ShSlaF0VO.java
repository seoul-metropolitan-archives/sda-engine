package com.bgf.shbank.domain.mng.sla.sh_sla_f0;

import com.bgf.shbank.utils.ModelMapperUtils;
import io.onsemiro.core.vo.BaseVO;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.glasnost.orika.BoundMapperFacade;
import org.springframework.data.domain.Page;

import java.util.List;

import static java.util.stream.Collectors.toList;


@Data
@NoArgsConstructor
public class ShSlaF0VO extends BaseVO {

    private String txDate;

    private String txId;

    private String errorDatetime;

    private String calleeChasu;

    private String corpCode;

    private String jisaCode;

    private String branchCode;

    private String branchName;

    private String cornerCode;

    private String cornerName;

    private String location;

    private String area;

    private String terminalNo;

    private String calleeGubun;

    private String calleeResult;

    private String calleeReqDatetime;

    private String arrivalDatetime;

    private String handleDatetime;

    private String recoverDatetime;

    private String elapsedTime;

    private String elapsedSeconds;

    private String errorContent;

    private String modelName;

    private String accept;

    private String refuseReason;


    public static ShSlaF0VO of(ShSlaF0 shSlaF0) {
        BoundMapperFacade<ShSlaF0, ShSlaF0VO> mapper =
                ModelMapperUtils.getMapper("ShSlaF0", ShSlaF0.class.getPackage().getName());
        return mapper.map(shSlaF0);
    }

    public static List<ShSlaF0VO> of(List<ShSlaF0> shSlaF0List) {
        return shSlaF0List.stream().map(shSlaF0 -> of(shSlaF0)).collect(toList());
    }

    public static List<ShSlaF0VO> of(Page<ShSlaF0> shSlaF0Page) {
        return shSlaF0Page.getContent().stream().map(shSlaF0 -> of(shSlaF0)).collect(toList());
    }
}