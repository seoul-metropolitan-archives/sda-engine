package com.bgf.shbank.domain.mng.error.sh01001230;

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
@Table(name = "ATMS_01001230")
@Comment(value = "")
@IdClass(Sh01001230.Sh01001230Id.class)
@Alias("sh01001230")
public class Sh01001230 extends SimpleJpaModel<Sh01001230.Sh01001230Id> {

	@Column(name = "JISA_CODE", length = 2, nullable = false)
	private String jisaCode;

	@Id
	@Column(name = "BRANCH_CODE", length = 4, nullable = false)
	private String branchCode;

	@Id
	@Column(name = "CORNER_CODE", length = 2, nullable = false)
	private String cornerCode;

	@Id
	@Column(name = "TERMINAL_NO", length = 4, nullable = false)
	private String terminalNo;

	@Column(name = "STEXT_CHASU", length = 1)
	private String stextChasu;

	@Id
	@Column(name = "HANDLE_OCCUR_DATETIME", nullable = false)
	private Timestamp handleOccurDatetime;

	@Id
	@Column(name = "HANDLE_SEQ_NO", length = 6, nullable = false)
	private String handleSeqNo;

	@Column(name = "HANDLE_NOTICE_DATETIME")
	private Timestamp handleNoticeDatetime;

	@Column(name = "HANDLE_NOTICE_EMP_NAME", length = 20)
	private String handleNoticeEmpName;

	@Column(name = "HANDLE_NOTICE_EMP_TELNO", length = 20)
	private String handleNoticeEmpTelno;

	@Column(name = "HANDLE_REQ_DESC", length = 200)
	private String handleReqDesc;

	@Column(name = "HANDLE_STATUS", length = 1)
	private String handleStatus;

	@Column(name = "SMS_ENABLE", length = 1)
	private String smsEnable;

	@Column(name = "CUSTOMER_NAME", length = 20)
	private String customerName;

	@Column(name = "CUSTOMER_TELNO", length = 15)
	private String customerTelno;

	@Column(name = "ERROR_TYPE", length = 1)
	private String errorType;

	@Column(name = "ACCOUNT_NO", length = 20)
	private String accountNo;

	@Column(name = "DEAL_SEQ_NO", length = 4)
	private String dealSeqNo;

	@Column(name = "DEAL_AMOUNT", length = 8)
	private String dealAmount;

	@Column(name = "HANDLE_DATETIME")
	private Timestamp handleDatetime;

	@Column(name = "HANDLE_EMP_NAME", length = 20)
	private String handleEmpName;

	@Column(name = "HANDLE_EMP_TELNO", length = 20)
	private String handleEmpTelno;

	@Column(name = "HANDLE_DESC", length = 200)
	private String handleDesc;

	@Column(name = "STEXT_SEND_GUBUN", length = 1)
	private String stextSendGubun;


@Override
public Sh01001230Id getId() {
return Sh01001230Id.of(branchCode, cornerCode, terminalNo, handleOccurDatetime, handleSeqNo);
}

@Embeddable
@Data
@NoArgsConstructor
@RequiredArgsConstructor(staticName = "of")
public static class Sh01001230Id implements Serializable {

		@NonNull
		private String branchCode;

		@NonNull
		private String cornerCode;

		@NonNull
		private String terminalNo;

		@NonNull
		private Timestamp handleOccurDatetime;

		@NonNull
		private String handleSeqNo;

}
}