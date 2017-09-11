package com.bgf.shbank.domain.mng.sla.sh_sla_70;

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
public class ShSla70VO extends BaseVO {

    private String txDate;

    private String txId;

    private String errorDatetime;

    private String calleeChasu;

    private String customerWait;

    private String evalType;

    private String corpCode;

    private String calleeType;

    private String jisaCode;

    private String branchCode;

    private String branchName;

    private String cornerCode;

    private String cornerName;

    private String location;

    private String area;

    private String terminalNo;

    private String calleeGubun;

    private String calleeReqDatetime;

    private String arrivalPlanDatetime;

    private String arrivalDatetime;

    private String arrivalType;

    private String calleeReqElapsedTime;

    private String calleeReqElapsedSeconds;

    private String arrivalElapsedTime;

    private String arrivalElapsedSeconds;

    private String reportDatetime;

    private String errorContent;

    private String modelName;

    private String accept;

    private String refuseReason;


    public static ShSla70VO of(ShSla70 shSla70) {
        BoundMapperFacade<ShSla70, ShSla70VO> mapper =
                ModelMapperUtils.getMapper("ShSla70", ShSla70.class.getPackage().getName());
        return mapper.map(shSla70);
    }

    public static List<ShSla70VO> of(List<ShSla70> shSla70List) {
        return shSla70List.stream().map(shSla70 -> of(shSla70)).collect(toList());
    }

    public static List<ShSla70VO> of(Page<ShSla70> shSla70Page) {
        return shSla70Page.getContent().stream().map(shSla70 -> of(shSla70)).collect(toList());
    }
}