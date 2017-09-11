package com.bgf.shbank.domain.mng.equip.sh02001210;

import io.onsemiro.core.annotations.Comment;
import io.onsemiro.core.domain.SimpleJpaModel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Setter
@Getter
@DynamicInsert
@DynamicUpdate
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "ATMS_02001210")
@Comment("")
@Alias("sh02001210")
public class Sh02001210 extends SimpleJpaModel<String> {

	@Id
	@Column(name = "WORK_SEQ_NO", length = 8, nullable = false)
	private String workSeqNo;

	@Column(name = "TERMINAL_SEQ_NO", length = 2)
	private String terminalSeqNo;

	@Column(name = "CHANGE_CHASU", length = 2)
	private String changeChasu;

	@Column(name = "STEXT_GUBUN", length = 1)
	private String stextGubun;

	@Column(name = "NOUSE_JISA_CODE", length = 2, nullable = false)
	private String nouseJisaCode;

	@Column(name = "NOUSE_BRANCH_CODE", length = 4, nullable = false)
	private String nouseBranchCode;

	@Column(name = "NOUSE_CORNER_CODE", length = 2, nullable = false)
	private String nouseCornerCode;

	@Column(name = "NOUSE_TERMINAL_NO", length = 4, nullable = false)
	private String nouseTerminalNo;

	@Column(name = "NOUSE_TERMINAL_CORP_CODE", length = 1)
	private String nouseTerminalCorpCode;

	@Column(name = "NOUSE_MODEL_CODE", length = 3)
	private String nouseModelCode;

	@Column(name = "NOUSE_HIRE_TERMINAL_ENABLE", length = 1)
	private String nouseHireTerminalEnable;

	@Column(name = "NOUSE_GUBUN", length = 1)
	private String nouseGubun;

	@Column(name = "WORK_PLAN_DATETIME")
	private Timestamp workPlanDatetime;

	@Column(name = "NEW_BRANCH_CODE", length = 4, nullable = false)
	private String newBranchCode;

	@Column(name = "NEW_CORNER_CODE", length = 2, nullable = false)
	private String newCornerCode;

	@Column(name = "NEW_TERMINAL_NO", length = 4, nullable = false)
	private String newTerminalNo;

	@Column(name = "NEW_TERMINAL_CORP_CODE", length = 1)
	private String newTerminalCorpCode;

	@Column(name = "NEW_MODEL_CODE", length = 3)
	private String newModelCode;

	@Column(name = "NEW_HIRE_TERMINAL_ENABLE", length = 1)
	private String newHireTerminalEnable;

	@Column(name = "NEW_INSTALL_TERMINAL_GUBUN", length = 1)
	private String newInstallTerminalGubun;

	@Column(name = "NEW_TERMINAL_HIRE_FEE", length = 10)
	private String newTerminalHireFee;

	@Column(name = "NEW_GATEWAY_IP_ADDR", length = 15)
	private String newGatewayIpAddr;

	@Column(name = "NEW_TERMINAL_IP_ADDR", length = 15)
	private String newTerminalIpAddr;

	@Column(name = "NEW_TERMINAL_SMASK_ADDR", length = 15)
	private String newTerminalSmaskAddr;

	@Column(name = "TERMINAL_PROD_NO", length = 15)
	private String terminalProdNo;

	@Column(name = "NEW_TERMINAL_MNG_GUBUN", length = 1)
	private String newTerminalMngGubun;


    @Override
    public String getId() {
        return workSeqNo;
    }
}