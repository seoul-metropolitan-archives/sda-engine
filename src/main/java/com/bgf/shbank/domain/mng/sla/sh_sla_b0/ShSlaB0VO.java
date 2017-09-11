package com.bgf.shbank.domain.mng.sla.sh_sla_b0;

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
public class ShSlaB0VO extends BaseVO {

	private String txDate;

	private String txId;

	private String totalCorp;

	private String jisaCode;

	private String branchCode;

	private String branchName;

	private String cornerCode;

	private String cornerName;

	private String terminalNo;

	private String mngGubun;

	private String operTimeGubun;

	private String terminalCorpName;

	private String modelName;

	private String atmGubun;

	private String overhaulDatetime;


    public static ShSlaB0VO of(ShSlaB0 shSlaB0) {
		BoundMapperFacade<ShSlaB0, ShSlaB0VO> mapper =
				ModelMapperUtils.getMapper("ShSlaB0", ShSlaB0.class.getPackage().getName());
		return mapper.map(shSlaB0);
    }

    public static List<ShSlaB0VO> of(List<ShSlaB0> shSlaB0List) {
        return shSlaB0List.stream().map(shSlaB0 -> of(shSlaB0)).collect(toList());
    }

    public static List<ShSlaB0VO> of(Page<ShSlaB0> shSlaB0Page) {
        return shSlaB0Page.getContent().stream().map(shSlaB0 -> of(shSlaB0)).collect(toList());
    }
}