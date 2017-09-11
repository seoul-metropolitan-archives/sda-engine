package com.bgf.shbank.domain.mng.equip.sh02001130;

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
public class Sh02001130VO extends BaseVO {

	private String jisaCode;
	private String jisaCodeName;

	private String branchCode;
	private String branchCodeName;

	private String cornerCode;

	private String terminalNo;

	private String workSeqNo;

	private String terminalSeqNo;

	private String changeChasu;

	private String stextGubun;
	private String stextGubunName;

	private String terminalCorpCode;
	private String terminalCorpCodeName;

	private String modelCode;
	private String modelCodeName;

	private String hireTerminalEnable;
	private String hireTerminalEnableName;

	private String installTerminalGubun;
	private String installTerminalGubunName;

	private String terminalHireFee;

	private String gatewayIpAddr;

	private String terminalIpAddr;

	private String terminalSmaskAddr;

	private String workPlanDatetime;

	private String terminalProdNo;


    public static Sh02001130VO of(Sh02001130 sh02001130) {

		BoundMapperFacade<Sh02001130, Sh02001130VO> mapper =
				ModelMapperUtils.getMapper("Sh02001130", Sh02001130VO.class.getPackage().getName());
		return mapper.map(sh02001130);
    }

    public static List<Sh02001130VO> of(List<Sh02001130> sh02001130List) {
        return sh02001130List.stream().map(sh02001130 -> of(sh02001130)).collect(toList());
    }

    public static List<Sh02001130VO> of(Page<Sh02001130> sh02001130Page) {
        return sh02001130Page.getContent().stream().map(sh02001130 -> of(sh02001130)).collect(toList());
    }
}