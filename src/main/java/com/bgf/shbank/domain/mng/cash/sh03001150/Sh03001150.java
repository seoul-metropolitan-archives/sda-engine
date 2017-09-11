package com.bgf.shbank.domain.mng.cash.sh03001150;

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
@Table(name = "ATMS_03001150")
@Comment(value = "")
@IdClass(Sh03001150.Sh03001150Id.class)
@Alias("sh03001150")
public class Sh03001150 extends SimpleJpaModel<Sh03001150.Sh03001150Id> {

	@Column(name = "JISA_CODE", length = 2, nullable = false)
	private String jisaCode;

	@Id
	@Column(name = "BRANCH_CODE", length = 4, nullable = false)
	private String branchCode;

	@Id
	@Column(name = "TERMINAL_NO", length = 4, nullable = false)
	private String terminalNo;

	@Column(name = "NONE_PROCESS_AMT", length = 15)
	private String noneProcessAmt;

	@Id
	@Column(name = "NONE_PROCESS_SEQ_NO", length = 3, nullable = false)
	private String noneProcessSeqNo;

	@Id
	@Column(name = "DEAL_DATE", nullable = false)
	private Timestamp dealDate;

	@Column(name = "STATEMENT_NO", length = 4)
	private String statementNo;

	@Column(name = "DEAL_BANK", length = 3)
	private String dealBank;

	@Column(name = "CARD_ACCOUNT_NO", length = 20)
	private String cardAccountNo;

	@Column(name = "DEAL_TYPE", length = 1)
	private String dealType;

	@Column(name = "DEAL_AMT", length = 15)
	private String dealAmt;

	@Column(name = "MANAGER_NAME", length = 10)
	private String managerName;

	@Column(name = "CUSTOMER_NAME", length = 10)
	private String customerName;

	@Column(name = "CUSTOMER_TELNO", length = 12)
	private String customerTelno;

	@Column(name = "MNG_OFFICE", length = 20)
	private String mngOffice;

	@Column(name = "UNUSL", length = 50)
	private String unusl;

	@Column(name = "PROCESS_DATE")
	private Timestamp processDate;

	@Column(name = "SEND_COMMISSION", length = 15)
	private String sendCommission;

	@Column(name = "RTN_COMMISSION", length = 15)
	private String rtnCommission;

	@Column(name = "STEXT_SEND_GUBUN", length = 1)
	private String stextSendGubun;


@Override
public Sh03001150Id getId() {
return Sh03001150Id.of(branchCode, terminalNo, noneProcessSeqNo, dealDate);
}

@Embeddable
@Data
@NoArgsConstructor
@RequiredArgsConstructor(staticName = "of")
public static class Sh03001150Id implements Serializable {

		@NonNull
		private String branchCode;

		@NonNull
		private String terminalNo;

		@NonNull
		private String noneProcessSeqNo;

		@NonNull
		private Timestamp dealDate;

}
}