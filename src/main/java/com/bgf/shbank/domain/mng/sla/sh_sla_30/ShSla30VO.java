package com.bgf.shbank.domain.mng.sla.sh_sla_30;

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
public class ShSla30VO extends BaseVO {

    private String txDate;

    private String seqNo;

    private String minwonDatetime;

    private String jisaCode;

    private String branchCode;

    private String branchName;

    private String cornerCode;

    private String cornerName;

    private String corpCode;

    private String regType;

    private String handleType;

    private String minwonType;

    private String content;

    private String regDatetime;


    public static ShSla30VO of(ShSla30 shSla30) {
        BoundMapperFacade<ShSla30, ShSla30VO> mapper =
                ModelMapperUtils.getMapper("ShSla30", ShSla30.class.getPackage().getName());
        return mapper.map(shSla30);
    }

    public static List<ShSla30VO> of(List<ShSla30> shSla30List) {
        return shSla30List.stream().map(shSla30 -> of(shSla30)).collect(toList());
    }

    public static List<ShSla30VO> of(Page<ShSla30> shSla30Page) {
        return shSla30Page.getContent().stream().map(shSla30 -> of(shSla30)).collect(toList());
    }
}