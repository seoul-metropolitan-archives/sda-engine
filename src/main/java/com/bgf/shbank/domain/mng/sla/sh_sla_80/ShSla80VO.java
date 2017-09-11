package com.bgf.shbank.domain.mng.sla.sh_sla_80;

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
public class ShSla80VO extends BaseVO {

	private String txDate;

	private String txId;

	private String errorDatetime;

	private String calleeChasu;

	private String customerWaitEnable;

	private String corpCode;

	private String calleeReqGubun;

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

	private String handleMethod;

	private String calleeElapsedTime;

	private String calleeElapsedSeconds;

	private String arrivalElapsedTime;

	private String arrivalElapsedSeconds;

	private String arrivalPlanReportDatetime;

	private String errorContent;

	private String modelName;


    public static ShSla80VO of(ShSla80 shSla80) {
		BoundMapperFacade<ShSla80, ShSla80VO> mapper =
				ModelMapperUtils.getMapper("ShSla80", ShSla80.class.getPackage().getName());
		return mapper.map(shSla80);
    }

    public static List<ShSla80VO> of(List<ShSla80> shSla80List) {
        return shSla80List.stream().map(shSla80 -> of(shSla80)).collect(toList());
    }

    public static List<ShSla80VO> of(Page<ShSla80> shSla80Page) {
        return shSla80Page.getContent().stream().map(shSla80 -> of(shSla80)).collect(toList());
    }
}