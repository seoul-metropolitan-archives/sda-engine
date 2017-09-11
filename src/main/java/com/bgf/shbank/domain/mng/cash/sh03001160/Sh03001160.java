package com.bgf.shbank.domain.mng.cash.sh03001160;

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
@Table(name = "ATMS_03001160")
@Comment(value = "")
@IdClass(Sh03001160.Sh03001160Id.class)
@Alias("sh03001160")
public class Sh03001160 extends SimpleJpaModel<Sh03001160.Sh03001160Id> {

	@Id
	@Column(name = "TX_ID", nullable = false)
	private Timestamp txId;

	@Column(name = "JISA_CODE", length = 2, nullable = false)
	private String jisaCode;

	@Id
	@Column(name = "BRANCH_CODE", length = 4, nullable = false)
	private String branchCode;

	@Id
	@Column(name = "TERMINAL_NO", length = 4, nullable = false)
	private String terminalNo;

	@Id
	@Column(name = "ADD_CASH_SENDING_SEQ_NO", length = 3, nullable = false)
	private String addCashSendingSeqNo;

	@Id
	@Column(name = "CASH_SENDING_DATE", nullable = false)
	private Timestamp cashSendingDate;

	@Column(name = "BILLING_EXPECT_DATE")
	private Timestamp billingExpectDate;

	@Column(name = "ADD_CASH_SENDING_AMT", length = 15)
	private String addCashSendingAmt;

	@Column(name = "MNG_OFFICE", length = 20)
	private String mngOffice;

	@Column(name = "CLOSE_GUBUN", length = 1)
	private String closeGubun;

	@Column(name = "CASH_SENDING_STND_DATE", nullable = false)
	private Timestamp cashSendingStndDate;

	@Column(name = "ADD_CASH_50K_SENDING_AMT", length = 15)
	private String addCash50kSendingAmt;

	@Column(name = "STEXT_SEND_GUBUN", length = 1)
	private String stextSendGubun;

@Override
public Sh03001160Id getId() {
return Sh03001160Id.of(txId, branchCode, terminalNo, addCashSendingSeqNo, cashSendingDate);
}

@Embeddable
@Data
@NoArgsConstructor
@RequiredArgsConstructor(staticName = "of")
public static class Sh03001160Id implements Serializable {

		@NonNull
		private Timestamp txId;

		@NonNull
		private String branchCode;

		@NonNull
		private String terminalNo;

		@NonNull
		private String addCashSendingSeqNo;

		@NonNull
		private Timestamp cashSendingDate;

}
}