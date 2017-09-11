package com.bgf.shbank.domain.mng.error.sh01001120;

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
public class Sh01001120VO extends BaseVO {

	private String calleeReqDatetime;

	private String errorDatetime;

	private String calleeReqSeqNo;

	private String calleeReqChasu;

	private String calleeChasuGubun;

	private String jisaCode;

	private String branchCode;

	private String branchName;

	private String cornerCode;

	private String cornerName;

	private String terminalNo;

	private String terminalCorpCode;

	private String modelCode;

	private String calleeReqGubunCode;

	private String calleeReqReasonCode;

	private String calleeEmpName;

	private String calleeEmpTelno;

	private String terminalErrorCode1;

	private String terminalErrorCode2;

	private String totalClassifyCode;

	private String calleeReqUnusl;

	private String crtNo;

	private String customerWaitEnable;

	private String partMngCalleeEnable;

	private String securityCorp;

	private String selfCalleeGubun;


	public static Sh01001120VO of(Sh01001120 sh01001120) {
		BoundMapperFacade<Sh01001120, Sh01001120VO> mapper =
				ModelMapperUtils.getMapper("Sh01001120", Sh01001120VO.class.getPackage().getName());
		return mapper.map(sh01001120);
	}

	public static List<Sh01001120VO> of(List<Sh01001120> sh01001120List) {
		return sh01001120List.stream().map(sh01001120 -> of(sh01001120)).collect(toList());
	}

	public static List<Sh01001120VO> of(Page<Sh01001120> sh01001120Page) {
		return sh01001120Page.getContent().stream().map(sh01001120 -> of(sh01001120)).collect(toList());
	}
}