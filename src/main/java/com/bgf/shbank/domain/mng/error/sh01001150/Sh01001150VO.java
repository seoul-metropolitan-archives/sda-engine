package com.bgf.shbank.domain.mng.error.sh01001150;

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
public class Sh01001150VO extends BaseVO {

	private String jisaCode;

	private String branchCode;

	private String cornerCode;

	private String terminalNo;

	private String errorDatetime;

	private String calleeReqSeqNo;

	private String calleeReqChasu;

	private String calleeReqGubun;

	private String calleeChasuGubun;

	private String handleResult;

	private String handleFailReason;

	private String asMethod;

	private String handleDatetime;

	private String handleEmpName;

	private String handleEmpTelno;

	private String handleContentCode;

	private String handleUnusl;

	private String crtNo;


    public static Sh01001150VO of(Sh01001150 sh01001150) {
		BoundMapperFacade<Sh01001150, Sh01001150VO> mapper =
				ModelMapperUtils.getMapper("Sh01001150", Sh01001150VO.class.getPackage().getName());
		return mapper.map(sh01001150);
    }

    public static List<Sh01001150VO> of(List<Sh01001150> sh01001150List) {
        return sh01001150List.stream().map(sh01001150 -> of(sh01001150)).collect(toList());
    }

    public static List<Sh01001150VO> of(Page<Sh01001150> sh01001150Page) {
        return sh01001150Page.getContent().stream().map(sh01001150 -> of(sh01001150)).collect(toList());
    }
}