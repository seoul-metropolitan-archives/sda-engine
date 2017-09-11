package com.bgf.shbank.domain.mng.sla.sh_sla_a0;

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
public class ShSlaA0VO extends BaseVO {

    private String txDate;

    private String regDatetime;

    private String jisaCode;

    private String branchCode;

    private String cornerCode;

    private String overhaulDate;

    private String branchName;

    private String cornerName;

    private String operDate;

    private String branchGubun;

    private String corpCode;

    private String takePhotoEnable;

    private String successCount;

    private String overhaulCount;

    private String overhaulResult;

    private String overhaulUnusl;

    private String submitTargetCount;

    private String submitCount;

    private String productCondt;

    private String terminalCondt;

    private String groundCondt;

    private String garbageCondt;

    private String cashEnvelopeCondt;

    private String posterCondt;


    public static ShSlaA0VO of(ShSlaA0 shSlaA0) {
        BoundMapperFacade<ShSlaA0, ShSlaA0VO> mapper =
                ModelMapperUtils.getMapper("ShSlaA0", ShSlaA0.class.getPackage().getName());
        return mapper.map(shSlaA0);
    }

    public static List<ShSlaA0VO> of(List<ShSlaA0> shSlaA0List) {
        return shSlaA0List.stream().map(shSlaA0 -> of(shSlaA0)).collect(toList());
    }

    public static List<ShSlaA0VO> of(Page<ShSlaA0> shSlaA0Page) {
        return shSlaA0Page.getContent().stream().map(shSlaA0 -> of(shSlaA0)).collect(toList());
    }
}