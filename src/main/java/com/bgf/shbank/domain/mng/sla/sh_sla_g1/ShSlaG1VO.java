package com.bgf.shbank.domain.mng.sla.sh_sla_g1;

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
public class ShSlaG1VO extends BaseVO {

    private String txDate;

    private String withdrawDatetime;

    private String withdrawDate;

    private String withdrawTime;

    private String realClassify;

    private String realNo;

    private String issueOrg;

    private String withdrawOrg;

    private String txId;

    private String jisaCode;

    private String progressStatus;

    private String withdrawEmpName;

    private String transferEmpName;

    private String takeoverEmpName;

    private String returnEmpName;

    private String receiveEmpName;

    private String transferDatetime;

    private String transferDate;

    private String transferTime;

    private String returnDatetime;

    private String returnDate;

    private String returnTime;

    private String storageUnusl;

    private boolean isDeleted;

    public static ShSlaG1VO of(ShSlaG1 shSlaG1) {
        BoundMapperFacade<ShSlaG1, ShSlaG1VO> mapper =
                ModelMapperUtils.getMapper("ShSlaG1", ShSlaG1.class.getPackage().getName());
        return mapper.map(shSlaG1);
    }

    public static List<ShSlaG1VO> of(List<ShSlaG1> shSlaG1List) {
        return shSlaG1List.stream().map(shSlaG1 -> of(shSlaG1)).collect(toList());
    }

    public static List<ShSlaG1VO> of(Page<ShSlaG1> shSlaG1Page) {
        return shSlaG1Page.getContent().stream().map(shSlaG1 -> of(shSlaG1)).collect(toList());
    }
}