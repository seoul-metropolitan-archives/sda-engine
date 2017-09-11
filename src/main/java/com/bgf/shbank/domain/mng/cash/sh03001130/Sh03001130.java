package com.bgf.shbank.domain.mng.cash.sh03001130;

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
@Table(name = "ATMS_03001130")
@Comment(value = "")
@IdClass(Sh03001130.Sh03001130Id.class)
@Alias("sh03001130")
public class Sh03001130 extends SimpleJpaModel<Sh03001130.Sh03001130Id> {

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

	@Column(name = "SAFE_NO", length = 4)
	private String safeNo;

	@Column(name = "REQ_DATE")
	private Timestamp reqDate;

	@Column(name = "CLOSE_GUBUN", length = 1)
	private String closeGubun;

	@Column(name = "CASH_GIVE_AMT", length = 15)
	private String cashGiveAmt;

	@Column(name = "CASH_DEPOSIT_AMT", length = 15)
	private String cashDepositAmt;

	@Column(name = "CASH_SENDING_AMT", length = 15)
	private String cashSendingAmt;

	@Column(name = "RTRVL_AMT", length = 15)
	private String rtrvlAmt;

	@Column(name = "CHECK_WITHDRAW_AMT", length = 15)
	private String checkWithdrawAmt;

	@Column(name = "CHECK_DEPOSIT_AMT", length = 15)
	private String checkDepositAmt;

	@Column(name = "NOW_DAY_PUT_AMT", length = 15)
	private String nowDayPutAmt;

	@Column(name = "PREV_DAY_PUT_AMT", length = 15)
	private String prevDayPutAmt;

	@Column(name = "PREV_DAY_ADD_PUT_AMT", length = 15)
	private String prevDayAddPutAmt;

	@Column(name = "HOLIDAY_ADD_PUT_AMT", length = 15)
	private String holidayAddPutAmt;

	@Column(name = "THIS_DAY_ADD_PUT_AMT", length = 15)
	private String thisDayAddPutAmt;

	@Column(name = "ATMC_TERMINAL_NOW_SIJAE", length = 15)
	private String atmcTerminalNowSijae;

	@Column(name = "SIJAE_CASH_50K", length = 15)
	private String sijaeCash50k;

	@Id
	@Column(name = "CLOSE_DATETIME", nullable = false)
	private Timestamp closeDatetime;

	@Column(name = "STEXT_SEND_GUBUN", length = 1)
	private String stextSendGubun;

	@Transient
	private String closeDate;

	@Transient
	private String closeTime;


@Override
public Sh03001130Id getId() {
return Sh03001130Id.of(txId, branchCode, terminalNo);
}

@Embeddable
@Data
@NoArgsConstructor
@RequiredArgsConstructor(staticName = "of")
public static class Sh03001130Id implements Serializable {

		@NonNull
		private Timestamp txId;

		@NonNull
		private String branchCode;

		@NonNull
		private String terminalNo;

}
}