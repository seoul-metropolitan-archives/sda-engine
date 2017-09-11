package com.bgf.shbank.domain.mng.equip.sh02001210;

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
public class Sh02001210VO extends BaseVO {

	private String workSeqNo;

	private String terminalSeqNo;

	private String changeChasu;

	private String stextGubun;
	private String stextGubunName;

	private String nouseJisaCode;
	private String nouseJisaCodeName;

	private String nouseBranchCode;
	private String nouseBranchCodeName;

	private String nouseCornerCode;
	private String nouseCornerCodeName;

	private String nouseTerminalNo;

	private String nouseTerminalCorpCode;
	private String nouseTerminalCorpCodeName;

	private String nouseModelCode;
	private String nouseModelCodeName;

	private String nouseHireTerminalEnable;
	private String nouseHireTerminalEnableName;

	private String nouseGubun;
	private String nouseGubunName;

	private String workPlanDatetime;

	private String newBranchCode;
	private String newBranchCodeName;

	private String newCornerCode;
	private String newCornerCodeName;

	private String newTerminalNo;

	private String newTerminalCorpCode;
	private String newTerminalCorpCodeName;

	private String newModelCode;
	private String newModelCodeName;

	private String newHireTerminalEnable;
	private String newHireTerminalEnableName;

	private String newInstallTerminalGubun;
	private String newInstallTerminalGubunName;

	private String newTerminalHireFee;

	private String newGatewayIpAddr;

	private String newTerminalIpAddr;

	private String newTerminalSmaskAddr;

	private String terminalProdNo;

	private String newTerminalMngGubun;
	private String newTerminalMngGubunName;


    public static Sh02001210VO of(Sh02001210 sh02001210) {
		BoundMapperFacade<Sh02001210, Sh02001210VO> mapper =
				ModelMapperUtils.getMapper("Sh02001210", Sh02001210VO.class.getPackage().getName());
		return mapper.map(sh02001210);
    }

    public static List<Sh02001210VO> of(List<Sh02001210> sh02001210List) {
        return sh02001210List.stream().map(sh02001210 -> of(sh02001210)).collect(toList());
    }

    public static List<Sh02001210VO> of(Page<Sh02001210> sh02001210Page) {
        return sh02001210Page.getContent().stream().map(sh02001210 -> of(sh02001210)).collect(toList());
    }
}