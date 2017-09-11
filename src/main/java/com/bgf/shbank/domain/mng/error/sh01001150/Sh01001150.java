package com.bgf.shbank.domain.mng.error.sh01001150;

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
@Table(name = "ATMS_01001150")
@Comment(value = "")
@IdClass(Sh01001150.Sh01001150Id.class)
@Alias("sh01001150")
public class Sh01001150 extends SimpleJpaModel<Sh01001150.Sh01001150Id> {

	@Column(name = "JISA_CODE", length = 2, nullable = false)
	@Comment(value = "")
	private String jisaCode;

	@Id
	@Column(name = "BRANCH_CODE", length = 4, nullable = false)
	@Comment(value = "")
	private String branchCode;

	@Id
	@Column(name = "CORNER_CODE", length = 2, nullable = false)
	@Comment(value = "")
	private String cornerCode;

	@Id
	@Column(name = "TERMINAL_NO", length = 4, nullable = false)
	@Comment(value = "")
	private String terminalNo;

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

	@Column(name = "CALLEE_REQ_GUBUN", length = 1)
	@Comment(value = "")
	private String calleeReqGubun;

	@Column(name = "CALLEE_CHASU_GUBUN", length = 1)
	@Comment(value = "")
	private String calleeChasuGubun;

	@Column(name = "HANDLE_RESULT", length = 1)
	@Comment(value = "")
	private String handleResult;

	@Column(name = "HANDLE_FAIL_REASON", length = 1)
	@Comment(value = "")
	private String handleFailReason;

	@Column(name = "AS_METHOD", length = 1)
	@Comment(value = "")
	private String asMethod;

	@Column(name = "HANDLE_DATETIME", nullable = false)
	@Comment(value = "")
	private Timestamp handleDatetime;

	@Column(name = "HANDLE_EMP_NAME", length = 10)
	@Comment(value = "")
	private String handleEmpName;

	@Column(name = "HANDLE_EMP_TELNO", length = 13)
	@Comment(value = "")
	private String handleEmpTelno;

	@Column(name = "HANDLE_CONTENT_CODE", length = 1)
	@Comment(value = "")
	private String handleContentCode;

	@Column(name = "HANDLE_UNUSL", length = 200)
	@Comment(value = "")
	private String handleUnusl;

	@Column(name = "CRT_NO", length = 15)
	@Comment(value = "")
	private String crtNo;

	@Override
	public Sh01001150Id getId() {
	return Sh01001150Id.of(branchCode, cornerCode, terminalNo, errorDatetime);
	}

	@Embeddable
	@Data
	@NoArgsConstructor
	@RequiredArgsConstructor(staticName = "of")
	public static class Sh01001150Id implements Serializable {

			@NonNull
			private String branchCode;

			@NonNull
			private String cornerCode;

			@NonNull
			private String terminalNo;

			@NonNull
			private Timestamp errorDatetime;
	}
}