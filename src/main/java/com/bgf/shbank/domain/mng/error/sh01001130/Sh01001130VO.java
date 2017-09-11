package com.bgf.shbank.domain.mng.error.sh01001130;

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
public class Sh01001130VO extends BaseVO {

	private String jisaCode;

	private String branchCode;

	private String cornerCode;

	private String terminalNo;

	private String errorDatetime;

	private String calleeReqSeqNo;

	private String calleeReqChasu;

	private String calleeReqGubun;

	private String calleeChasuGubun;

	private String calleePlanDatetime;

	private String arrivalPlanDatetime;

	private String corpReceiptEmpName;

	private String corpCalleeEmpName;

	private String corpCalleeEmpTelno;

	private String unusl;

	private String crtNo;

	private String arrivalPlanGubun;

	private String securityCorpCode;

    public static Sh01001130VO of(Sh01001130 sh01001130) {
		BoundMapperFacade<Sh01001130, Sh01001130VO> mapper =
				ModelMapperUtils.getMapper("Sh01001130", Sh01001130VO.class.getPackage().getName());
		return mapper.map(sh01001130);
    }

    public static List<Sh01001130VO> of(List<Sh01001130> sh01001130List) {
        return sh01001130List.stream().map(sh01001130 -> of(sh01001130)).collect(toList());
    }

    public static List<Sh01001130VO> of(Page<Sh01001130> sh01001130Page) {
        return sh01001130Page.getContent().stream().map(sh01001130 -> of(sh01001130)).collect(toList());
    }
}