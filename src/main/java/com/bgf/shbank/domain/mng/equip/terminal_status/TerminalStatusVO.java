package com.bgf.shbank.domain.mng.equip.terminal_status;

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
public class TerminalStatusVO extends BaseVO {

	private String jisaCode;
	private String jisaCodeName;

	private String branchCode;

	private String branchName;

	private String cornerCode;

	private String cornerName;

	private String terminalNo;

	private String operEnable;
	private String operEnableName;

	private String modelCode;
	private String modelCodeName;

	private String branchGubun;
	private String branchGubunName;

	private String terminalType;
	private String terminalTypeName;

	private String placeGubun;
	private String placeGubunName;

	private String operTimeGubun;
	private String operTimeGubunName;

	private String weekendOperGubun;
	private String weekendOperGubunName;

	private String checkOperEnable;
	private String checkOperEnableName;

	private String cash50kOperEnable;
	private String cash50kOperEnableName;

	private String operStartTime;

	private String operEndTime;

	private String taskApplyDate;

	private String workDate;

	private String securityCorp;
	private String securityCorpName;

	private String installPlaceGubun;
	private String installPlaceGubunName;

	private String addr;

	private String photoEnable;
	private String photoEnableName;

	private String boothCorp;
	private String boothCorpName;

	private String boothType;
	private String boothTypeName;

	private String intercomEnable;
	private String intercomEnableName;

	private String envelopeEnable;
	private String envelopeEnableName;

	private String garbagecanEnable;
	private String garbagecanEnableName;

	private String shredderEnable;
	private String shredderEnableName;

	private String extinguisherEnable;
	private String extinguisherEnableName;

	private String posterCount;

	private String coolerHeaterEnable;
	private String coolerHeaterEnableName;

	private String slopeEnable;
	private String slopeEnableName;

	private String terminalCorpCode;
	private String terminalCorpCodeName;

	private String hireTerminalEnable;
	private String hireTerminalEnableName;

	private String installTerminalGubun;
	private String installTerminalGubunName;

	private String terminalHireFee;

	private String gatewayIpAddr;

	private String terminalIpAddr;

	private String terminalSmaskAddr;

	private String terminalProdNo;

	private String intercomNo;
	private String terminalLocation;
	private String commOffice;
	private String internetClassify;

	private String securityCorpContractNo;

	private String mngChannel;

    public static TerminalStatusVO of(TerminalStatus terminalStatus) {
		BoundMapperFacade<TerminalStatus, TerminalStatusVO> mapper =
				ModelMapperUtils.getMapper("TerminalStatus", TerminalStatusVO.class.getPackage().getName());

		TerminalStatusVO terminalStatusVO = mapper.map(terminalStatus);
		return terminalStatusVO;
    }

    public static List<TerminalStatusVO> of(List<TerminalStatus> terminalStatusList) {
        return terminalStatusList.stream().map(terminalStatus -> of(terminalStatus)).collect(toList());
    }

    public static List<TerminalStatusVO> of(Page<TerminalStatus> terminalStatusPage) {
        return terminalStatusPage.getContent().stream().map(terminalStatus -> of(terminalStatus)).collect(toList());
    }
}