package com.bgf.shbank.domain.mng.error.sh01001110;

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
public class Sh01001110VO extends BaseVO {

	private String errorDatetime;

	private String errorDate;

	private String errorTime;

	private String errorDatetime1;

	private String errorDate1; // 개국일

	private String errorTime1; // 개국시간

	private String branchCode;

	private String branchName;

	private String branchTel;

	private String cornerCode;

	private String cornerName;

	private String terminalNo;

	private String jisaCode;

	private String jisaName;

	private String orgName;

	private String placeGubun;

	private String operTimeGubun;

	private String runtime;

	private String prodSerialNo;

	private String terminalCorpCode;

	private String terminalCorpName;

	private String modelCode;

	private String progressStatus;

	private String stextGubun;

	private String terminalError1;

	private String terminalError2;

	private String totalClassifyCode;

	private String errorType;

	private String cashPartStatus;

	private String checkPartStatus;

	private String portfolioPartStatus;

	private String jrnlPartStatus;

	private String rtrvlBoxStatus;

	private String cardPartStatus;

	private String bnkbPartStatus;

	private String giroPartStatus;

	private String suspendStatus;

	private String hwErrorStatus;

	private String maintenanceStatus;

	private String version;

	private String enableDesBoard;

	private String enableIcCard;

	private String enableEmv;

	private String enableIr;

	private String enableRf;

	private String enableFingerprint;

	private String encryptionStatus;

	private String terminalInfo;

	private String atmsTerminalNo;

	private String cashPartStatus50kWon;

	private String atmcExcclcTerminalError;

	private String atmcExcclcExecResult;

	private String terminalVerInfo;

	private String cardIssuedTerminalStatus;

	private String idScannerStatus;

	private String bioScannerStatus;

	private String scrtyCardHighendAtmOnly;

	private String cardStyleOtp;

	private String s20General;

	private String s20Frpy;

	private String slineGeneral;

	private String slineFrpy;

	private String fourtuneGeneral;

	private String fourtuneFrpy;

	private String rcppayBnkb;

	private String securityCorp;
	private String securityCorpContractNo;
	private String boothCorp;
	private String boothType;
	private String intercomNo;
	private String terminalLocation;
	private String commOffice;
	private String internetClassify;
	private String mngChannel;
	private String noticeContent;
	private String customerInfo;
	private String handleContent;
	private String customerWaitEnable;
	private String calleeReqSeqNo;
	private String txId;

    public static Sh01001110VO of(Sh01001110 sh01001110) {
		BoundMapperFacade<Sh01001110, Sh01001110VO> mapper =
				ModelMapperUtils.getMapper("Sh01001110", Sh01001110VO.class.getPackage().getName());
		return mapper.map(sh01001110);
    }

    public static List<Sh01001110VO> of(List<Sh01001110> sh01001110List) {
        return sh01001110List.stream().map(sh01001110 -> of(sh01001110)).collect(toList());
    }

    public static List<Sh01001110VO> of(Page<Sh01001110> sh01001110Page) {
        return sh01001110Page.getContent().stream().map(sh01001110 -> of(sh01001110)).collect(toList());
    }
}