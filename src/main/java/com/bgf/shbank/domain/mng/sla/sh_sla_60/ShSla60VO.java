package com.bgf.shbank.domain.mng.sla.sh_sla_60;

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
public class ShSla60VO extends BaseVO {

    private String txDate;

    private String corpCode;

    private String regDatetime;

    private String equals;

    private String etc;


    public static ShSla60VO of(ShSla60 shSla60) {
        BoundMapperFacade<ShSla60, ShSla60VO> mapper =
                ModelMapperUtils.getMapper("ShSla60", ShSla60.class.getPackage().getName());
        return mapper.map(shSla60);
    }

    public static List<ShSla60VO> of(List<ShSla60> shSla60List) {
        return shSla60List.stream().map(shSla60 -> of(shSla60)).collect(toList());
    }

    public static List<ShSla60VO> of(Page<ShSla60> shSla60Page) {
        return shSla60Page.getContent().stream().map(shSla60 -> of(shSla60)).collect(toList());
    }
}