package com.bgf.shbank.domain.mng.cash.sh03001120;

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
@Table(name = "ATMS_03001120")
@Comment(value = "")
@IdClass(Sh03001120.Sh03001120Id.class)
@Alias("sh03001120")
public class Sh03001120 extends SimpleJpaModel<Sh03001120.Sh03001120Id> {

	@Column(name = "JISA_CODE", length = 2, nullable = false)
	private String jisaCode;

	@Id
	@Column(name = "BRANCH_CODE", length = 4, nullable = false)
	private String branchCode;

	@Id
	@Column(name = "TERMINAL_NO", length = 4, nullable = false)
	private String terminalNo;

	@Column(name = "REFER_DATE", nullable = false)
	private Timestamp referDate;

	@Column(name = "REFER_STATEMENT_NO", length = 4, nullable = false)
	private String referStatementNo;

	@Column(name = "REFER_START_TIME")
	private Timestamp referStartTime;

	@Column(name = "REFER_END_TIME")
	private Timestamp referEndTime;

	@Id
	@Column(name = "DEAL_SEQ_NO", length = 4)
	private String dealSeqNo;

	@Id
	@Column(name = "DEAL_TIME")
	private Timestamp dealTime;

	@Column(name = "PROCESS_STATUS", length = 2)
	private String processStatus;

	@Column(name = "LOCATE_GUBUN", length = 2)
	private String locateGubun;

	@Column(name = "DEAL_METHOD", length = 2)
	private String dealMethod;

	@Column(name = "DEPOSIT_BANK_CODE", length = 3)
	private String depositBankCode;

	@Column(name = "DEPOSIT_ACCOUNT_NO", length = 16)
	private String depositAccountNo;

	@Column(name = "WITHDRAW_BANK_CODE", length = 3)
	private String withdrawBankCode;

	@Column(name = "WITHDRAW_ACCOUNT_NO", length = 16)
	private String withdrawAccountNo;

	@Column(name = "DEAL_AMT", length = 12)
	private String dealAmt;

	@Column(name = "STEXT_SEND_GUBUN", length = 1)
	private String stextSendGubun;


	@Override
	public Sh03001120Id getId() {
		return Sh03001120Id.of(branchCode, terminalNo, dealTime, dealSeqNo);
	}

	@Embeddable
	@Data
	@NoArgsConstructor
	@RequiredArgsConstructor(staticName = "of")
	public static class Sh03001120Id implements Serializable {

		@NonNull
		private String branchCode;

		@NonNull
		private String terminalNo;

		@NonNull
		private Timestamp dealTime;

		@NonNull
		private String dealSeqNo;

	}
}