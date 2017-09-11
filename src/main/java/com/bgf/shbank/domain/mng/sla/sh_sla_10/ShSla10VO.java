package com.bgf.shbank.domain.mng.sla.sh_sla_10;

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
public class ShSla10VO extends BaseVO {

    private String txDate;

    private String txId;

    private String errorDatetime;

    private String calleeChasu;

    private String calleeGubun;

    private String jisaCode;

    private String branchCode;

    private String branchName;

    private String cornerCode;

    private String cornerName;

    private String terminalNo;

    private String securityCorp;

    private String calleeReqReason;

    private String calleeReqDatetime;

    private String arrivalDatetime;

    private String elapsedTime;

    private String report;

    private String accept;

    private String refuseReason;


    public static ShSla10VO of(ShSla10 shSla10) {
        BoundMapperFacade<ShSla10, ShSla10VO> mapper =
                ModelMapperUtils.getMapper("ShSla10", ShSla10.class.getPackage().getName());
        return mapper.map(shSla10);
    }

    public static List<ShSla10VO> of(List<ShSla10> shSla10List) {
        return shSla10List.stream().map(shSla10 -> of(shSla10)).collect(toList());
    }

    public static List<ShSla10VO> of(Page<ShSla10> shSla10Page) {
        return shSla10Page.getContent().stream().map(shSla10 -> of(shSla10)).collect(toList());
    }
}