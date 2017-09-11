package com.bgf.shbank.domain.mng.etc.sh04001150;

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
public class Sh04001150VO extends BaseVO {

	private String jisaCode;
	private String jisaCodeName;

	private String branchCode;
	private String branchCodeName;

	private String cornerCode;

	private String terminalNo;

	private String cornerName;

	private String errorDatetime;

	private String calleeReqDatetime;

	private String calleeReqSeqNo;

	private String calleeReqChasu;

	private String calleeChasuGubun;
	private String calleeChasuGubunName;

	private String cornerArrivalDate;
	private String cornerArrivalTime;

	private String repairDatetime;

	private String terminalType;

	private String errorContent;

	private String penaltyAmt;

	private String nonePenaltyEnable;
	private String nonePenaltyEnableName;

	private String nonePenaltyReason;


    public static Sh04001150VO of(Sh04001150 sh04001150) {
		BoundMapperFacade<Sh04001150, Sh04001150VO> mapper =
				ModelMapperUtils.getMapper("Sh04001150", Sh04001150VO.class.getPackage().getName());
		return mapper.map(sh04001150);
    }

    public static List<Sh04001150VO> of(List<Sh04001150> sh04001150List) {
        return sh04001150List.stream().map(sh04001150 -> of(sh04001150)).collect(toList());
    }

    public static List<Sh04001150VO> of(Page<Sh04001150> sh04001150Page) {
        return sh04001150Page.getContent().stream().map(sh04001150 -> of(sh04001150)).collect(toList());
    }
}