package com.bgf.shbank.domain.mng.equip.sh02001130;

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
@Table(name = "ATMS_02001130")
@Comment("")
@Alias("sh02001130")
public class Sh02001130 extends SimpleJpaModel<String> {

	@Column(name = "JISA_CODE", length = 2, nullable = false)
	private String jisaCode;

	@Column(name = "BRANCH_CODE", length = 4, nullable = false)
	private String branchCode;

	@Column(name = "CORNER_CODE", length = 2, nullable = false)
	private String cornerCode;

	@Column(name = "TERMINAL_NO", length = 4, nullable = false)
	private String terminalNo;

	@Id
	@Column(name = "WORK_SEQ_NO", length = 8, nullable = false)
	private String workSeqNo;

	@Column(name = "TERMINAL_SEQ_NO", length = 2)
	private String terminalSeqNo;

	@Column(name = "CHANGE_CHASU", length = 2)
	private String changeChasu;

	@Column(name = "STEXT_GUBUN", length = 1)
	private String stextGubun;

	@Column(name = "TERMINAL_CORP_CODE", length = 1)
	private String terminalCorpCode;

	@Column(name = "MODEL_CODE", length = 3)
	private String modelCode;

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

	@Column(name = "WORK_PLAN_DATETIME")
	private Timestamp workPlanDatetime;

	@Column(name = "TERMINAL_PROD_NO", length = 15)
	private String terminalProdNo;


    @Override
    public String getId() {
        return workSeqNo;
    }
}