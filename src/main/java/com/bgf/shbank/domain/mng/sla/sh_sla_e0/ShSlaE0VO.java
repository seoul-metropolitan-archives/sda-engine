package com.bgf.shbank.domain.mng.sla.sh_sla_e0;

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
public class ShSlaE0VO extends BaseVO {

    private String txDate;

    private String stndDate;

    private String txId;

    private String corpCode;

    private String jisaCode;

    private String branchCode;

    private String branchName;

    private String cornerCode;

    private String cornerName;

    private String terminalNo;

    private String operStartTime;

    private String operEndTime;

    private String modelName;

    private String totalOperTime;

    private String realOperTime;

    private String noneOperTime;


    public static ShSlaE0VO of(ShSlaE0 shSlaE0) {
        BoundMapperFacade<ShSlaE0, ShSlaE0VO> mapper =
                ModelMapperUtils.getMapper("ShSlaE0", ShSlaE0.class.getPackage().getName());
        return mapper.map(shSlaE0);
    }

    public static List<ShSlaE0VO> of(List<ShSlaE0> shSlaE0List) {
        return shSlaE0List.stream().map(shSlaE0 -> of(shSlaE0)).collect(toList());
    }

    public static List<ShSlaE0VO> of(Page<ShSlaE0> shSlaE0Page) {
        return shSlaE0Page.getContent().stream().map(shSlaE0 -> of(shSlaE0)).collect(toList());
    }
}