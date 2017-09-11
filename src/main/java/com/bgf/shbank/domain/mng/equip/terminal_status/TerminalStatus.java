package com.bgf.shbank.domain.mng.equip.terminal_status;

import io.onsemiro.core.annotations.Comment;
import io.onsemiro.core.domain.SimpleJpaModel;
import lombok.*;
import org.apache.ibatis.type.Alias;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Setter
@Getter
@DynamicInsert
@DynamicUpdate
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "ATMS_TERMINAL_STATUS")
@Comment(value = "")
@IdClass(TerminalStatus.TerminalStatusId.class)
@Alias("terminalStatus")
public class TerminalStatus extends SimpleJpaModel<TerminalStatus.TerminalStatusId> {

	@Id
	@Column(name = "JISA_CODE", length = 2, nullable = false)
	private String jisaCode;

	@Id
	@Column(name = "BRANCH_CODE", length = 4, nullable = false)
	private String branchCode;

	@Column(name = "BRANCH_NAME", length = 40)
	private String branchName;

	@Id
	@Column(name = "CORNER_CODE", length = 2, nullable = false)
	private String cornerCode;

	@Column(name = "CORNER_NAME", length = 40)
	private String cornerName;

	@Id
	@Column(name = "TERMINAL_NO", length = 4, nullable = false)
	private String terminalNo;

	@Column(name = "OPER_ENABLE", length = 1)
	private String operEnable;

	@Column(name = "MODEL_CODE", length = 3)
	private String modelCode;

	@Column(name = "BRANCH_GUBUN", length = 1)
	private String branchGubun;

	@Column(name = "TERMINAL_TYPE", length = 1)
	private String terminalType;

	@Column(name = "PLACE_GUBUN", length = 1)
	private String placeGubun;

	@Column(name = "OPER_TIME_GUBUN", length = 1)
	private String operTimeGubun;

	@Column(name = "WEEKEND_OPER_GUBUN", length = 1)
	private String weekendOperGubun;

	@Column(name = "CHECK_OPER_ENABLE", length = 1)
	private String checkOperEnable;

	@Column(name = "CASH_50K_OPER_ENABLE", length = 1)
	private String cash50kOperEnable;

	@Column(name = "OPER_START_TIME")
	private Timestamp operStartTime;

	@Column(name = "OPER_END_TIME")
	private Timestamp operEndTime;

	@Column(name = "TASK_APPLY_DATE")
	private Timestamp taskApplyDate;

	@Column(name = "WORK_DATE")
	private Timestamp workDate;

	@Column(name = "SECURITY_CORP", length = 2)
	private String securityCorp;

	@Column(name = "INSTALL_PLACE_GUBUN", length = 3)
	private String installPlaceGubun;

	@Column(name = "ADDR", length = 150)
	private String addr;

	@Column(name = "PHOTO_ENABLE", length = 1)
	private String photoEnable;

	@Column(name = "BOOTH_CORP", length = 30)
	private String boothCorp;

	@Column(name = "BOOTH_TYPE", length = 30)
	private String boothType;

	@Column(name = "INTERCOM_ENABLE", length = 1)
	private String intercomEnable;

	@Column(name = "ENVELOPE_ENABLE", length = 1)
	private String envelopeEnable;

	@Column(name = "GARBAGECAN_ENABLE", length = 1)
	private String garbagecanEnable;

	@Column(name = "SHREDDER_ENABLE", length = 1)
	private String shredderEnable;

	@Column(name = "EXTINGUISHER_ENABLE", length = 1)
	private String extinguisherEnable;

	@Column(name = "POSTER_COUNT", length = 2)
	private String posterCount;

	@Column(name = "COOLER_HEATER_ENABLE", length = 1)
	private String coolerHeaterEnable;

	@Column(name = "SLOPE_ENABLE", length = 1)
	private String slopeEnable;

	@Column(name = "TERMINAL_CORP_CODE", length = 1)
	private String terminalCorpCode;

	@Column(name = "HIRE_TERMINAL_ENABLE", length = 1)
	private String hireTerminalEnable;

	@Column(name = "INSTALL_TERMINAL_GUBUN", length = 1)
	private String installTerminalGubun;

	@Column(name = "TERMINAL_HIRE_FEE", length = 10)
	private String terminalHireFee;

	@Column(name = "GATEWAY_IP_ADDR", length = 15)
	private String gatewayIpAddr;

	@Column(name = "TERMINAL_IP_ADDR", length = 15)
	private String terminalIpAddr;

	@Column(name = "TERMINAL_SMASK_ADDR", length = 15)
	private String terminalSmaskAddr;

	@Column(name = "TERMINAL_PROD_NO", length = 15)
	private String terminalProdNo;

	@Column(name = "INTERCOM_NO", length = 20)
	private String intercomNo;

	@Column(name = "TERMINAL_LOCATION", length = 50)
	private String terminalLocation;

	@Column(name = "COMM_OFFICE", length = 10)
	private String commOffice;

	@Column(name = "INTERNET_CLASSIFY", length = 10)
	private String internetClassify;

	@Column(name = "SECURITY_CORP_CONTRACT_NO", length = 20)
	private String securityCorpContractNo;

	@Column(name = "MNG_CHANNEL", length = 10)
	private String mngChannel;

@Override
public TerminalStatusId getId() {
return TerminalStatusId.of(jisaCode, branchCode, cornerCode, terminalNo);
}

@Embeddable
@Data
@NoArgsConstructor
@RequiredArgsConstructor(staticName = "of")
public static class TerminalStatusId implements Serializable {

		@NonNull
		private String jisaCode;

		@NonNull
		private String branchCode;

		@NonNull
		private String cornerCode;

		@NonNull
		private String terminalNo;

}
}