package com.bgf.shbank.domain.mng.sla.sh_sla_50;

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
public class ShSla50VO extends BaseVO {

    private String txDate;

    private String corpCode;

    private String txDatetime;

    private String ocmAmount;

    private String ocmAmount2;

    private String accountBalance;


    public static ShSla50VO of(ShSla50 shSla50) {
        BoundMapperFacade<ShSla50, ShSla50VO> mapper =
                ModelMapperUtils.getMapper("ShSla50", ShSla50.class.getPackage().getName());
        return mapper.map(shSla50);
    }

    public static List<ShSla50VO> of(List<ShSla50> shSla50List) {
        return shSla50List.stream().map(shSla50 -> of(shSla50)).collect(toList());
    }

    public static List<ShSla50VO> of(Page<ShSla50> shSla50Page) {
        return shSla50Page.getContent().stream().map(shSla50 -> of(shSla50)).collect(toList());
    }
}