package com.bgf.shbank.domain.mng.error.sh01001120;

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
@Table(name = "ATMS_01001120")
@Comment(value = "")
@IdClass(Sh01001120.Sh01001120Id.class)
@Alias("sh01001120")
public class Sh01001120 extends SimpleJpaModel<Sh01001120.Sh01001120Id> {

	@Column(name = "CALLEE_REQ_DATETIME", nullable = false)
	@Comment(value = "")
	private Timestamp calleeReqDatetime;

	@Id
	@Column(name = "ERROR_DATETIME", nullable = false)
	@Comment(value = "")
	private Timestamp errorDatetime;

	@Column(name = "CALLEE_REQ_SEQ_NO", length = 10)
	@Comment(value = "")
	private String calleeReqSeqNo;

	@Column(name = "CALLEE_REQ_CHASU", length = 2)
	@Comment(value = "")
	private String calleeReqChasu;

	@Column(name = "CALLEE_CHASU_GUBUN", length = 1)
	@Comment(value = "")
	private String calleeChasuGubun;

	@Column(name = "JISA_CODE", length = 2, nullable = false)
	@Comment(value = "")
	private String jisaCode;

	@Id
	@Column(name = "BRANCH_CODE", length = 4, nullable = false)
	@Comment(value = "")
	private String branchCode;

	@Column(name = "BRANCH_NAME", length = 40)
	@Comment(value = "")
	private String branchName;

	@Id
	@Column(name = "CORNER_CODE", length = 2, nullable = false)
	@Comment(value = "")
	private String cornerCode;

	@Column(name = "CORNER_NAME", length = 40)
	@Comment(value = "")
	private String cornerName;

	@Id
	@Column(name = "TERMINAL_NO", length = 4, nullable = false)
	@Comment(value = "")
	private String terminalNo;

	@Column(name = "TERMINAL_CORP_CODE", length = 1)
	@Comment(value = "")
	private String terminalCorpCode;

	@Column(name = "MODEL_CODE", length = 3)
	@Comment(value = "")
	private String modelCode;

	@Column(name = "CALLEE_REQ_GUBUN_CODE", length = 1)
	@Comment(value = "")
	private String calleeReqGubunCode;

	@Column(name = "CALLEE_REQ_REASON_CODE", length = 1)
	@Comment(value = "")
	private String calleeReqReasonCode;

	@Column(name = "CALLEE_EMP_NAME", length = 10)
	@Comment(value = "")
	private String calleeEmpName;

	@Column(name = "CALLEE_EMP_TELNO", length = 13)
	@Comment(value = "")
	private String calleeEmpTelno;

	@Column(name = "TERMINAL_ERROR_CODE_1", length = 5)
	@Comment(value = "")
	private String terminalErrorCode1;

	@Column(name = "TERMINAL_ERROR_CODE_2", length = 10)
	@Comment(value = "")
	private String terminalErrorCode2;

	@Column(name = "TOTAL_CLASSIFY_CODE", length = 2)
	@Comment(value = "")
	private String totalClassifyCode;

	@Column(name = "CALLEE_REQ_UNUSL", length = 200)
	@Comment(value = "")
	private String calleeReqUnusl;

	@Column(name = "CRT_NO", length = 15)
	@Comment(value = "")
	private String crtNo;

	@Column(name = "CUSTOMER_WAIT_ENABLE", length = 1)
	@Comment(value = "")
	private String customerWaitEnable;

	@Column(name = "PART_MNG_CALLEE_ENABLE", length = 1)
	@Comment(value = "")
	private String partMngCalleeEnable;

	@Transient
	private String selfCallGubun;


	@Override
	public Sh01001120Id getId() {
	return Sh01001120Id.of(errorDatetime, branchCode, cornerCode, terminalNo);
	}

	@Embeddable
	@Data
	@NoArgsConstructor
	@RequiredArgsConstructor(staticName = "of")
	public static class Sh01001120Id implements Serializable {

			@NonNull
			private Timestamp errorDatetime;

			@NonNull
			private String branchCode;

			@NonNull
			private String cornerCode;

			@NonNull
			private String terminalNo;
	}
}