package com.bgf.shbank.domain.mng.sla.sh_sla_20;

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
public class ShSla20VO extends BaseVO {

    private String txDate;

    private String txId;

    private String errorDatetime;

    private String corpCode;

    private String jisaCode;

    private String branchCode;

    private String branchName;

    private String cornerCode;

    private String cornerName;

    private String terminalNo;

    private String recoveryDatetime;

    private String elapsedTime;

    private String elapsedSeconds;

    private String errorContent;

    private String modelName;

    private String accept;

    private String refuseReason;


    public static ShSla20VO of(ShSla20 shSla20) {
        BoundMapperFacade<ShSla20, ShSla20VO> mapper =
                ModelMapperUtils.getMapper("ShSla20", ShSla20.class.getPackage().getName());
        return mapper.map(shSla20);
    }

    public static List<ShSla20VO> of(List<ShSla20> shSla20List) {
        return shSla20List.stream().map(shSla20 -> of(shSla20)).collect(toList());
    }

    public static List<ShSla20VO> of(Page<ShSla20> shSla20Page) {
        return shSla20Page.getContent().stream().map(shSla20 -> of(shSla20)).collect(toList());
    }
}