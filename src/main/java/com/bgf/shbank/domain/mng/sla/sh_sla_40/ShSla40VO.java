package com.bgf.shbank.domain.mng.sla.sh_sla_40;

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
public class ShSla40VO extends BaseVO {

	private String txDate;

	private String seqNo;

	private String corpCode;

	private String gubun;

	private String jisaCode;

	private String branchCode;

	private String branchName;

	private String cornerCode;

	private String cornerName;

	private String terminalNo;

	private String calleeReqDatetime;

	private String checkpointScore1;

	private String checkpointScore2;

	private String checkpointScore3;

	private String checkpointScore4;

	private String happycallAgent;

	private String happycallDatetime;

	private String errorDatetime;

	private String calleeReqUser;

	private String customer;

	private String telNo;

	private String content;


    public static ShSla40VO of(ShSla40 shSla40) {
		BoundMapperFacade<ShSla40, ShSla40VO> mapper =
				ModelMapperUtils.getMapper("ShSla40", ShSla40.class.getPackage().getName());
		return mapper.map(shSla40);
    }

    public static List<ShSla40VO> of(List<ShSla40> shSla40List) {
        return shSla40List.stream().map(shSla40 -> of(shSla40)).collect(toList());
    }

    public static List<ShSla40VO> of(Page<ShSla40> shSla40Page) {
        return shSla40Page.getContent().stream().map(shSla40 -> of(shSla40)).collect(toList());
    }
}