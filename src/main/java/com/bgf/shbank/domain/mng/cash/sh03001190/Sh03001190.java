package com.bgf.shbank.domain.mng.cash.sh03001190;

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
@Table(name = "ATMS_03001190")
@Comment(value = "")
@IdClass(Sh03001190.Sh03001190Id.class)
@Alias("sh03001190")
public class Sh03001190 extends SimpleJpaModel<Sh03001190.Sh03001190Id> {
	@Column(name = "TX_ID", nullable = false)
	private Timestamp txId;

	@Column(name = "JISA_CODE", length = 2, nullable = false)
	private String jisaCode;

	@Id
	@Column(name = "BRANCH_CODE", length = 4, nullable = false)
	private String branchCode;

	@Id
	@Column(name = "REQ_DATE", nullable = false)
	private Timestamp reqDate;

	@Column(name = "FUND_EXPENSE_START_DATE")
	private Timestamp fundExpenseStartDate;

	@Column(name = "FUND_EXPENSE_END_DATE")
	private Timestamp fundExpenseEndDate;

	@Column(name = "FUND_EXPENSE_TERM", length = 2)
	private String fundExpenseTerm;

	@Column(name = "NEXT_DAY_CASH_SENDING_AMT", length = 15)
	private String nextDayCashSendingAmt;

	@Column(name = "NOW_DAY_ADD_CASH_SENDING_AMT", length = 15)
	private String nowDayAddCashSendingAmt;

	@Column(name = "NOW_DAY_RTRVL_AMT", length = 15)
	private String nowDayRtrvlAmt;

	@Column(name = "NOW_DAY_LACK_AMT", length = 15)
	private String nowDayLackAmt;

	@Column(name = "THIS_DAY_RTRVL_EXCPECT_AMT", length = 15)
	private String thisDayRtrvlExcpectAmt;

	@Column(name = "NEXT_DAY_RTRVL_EXCPECT_AMT", length = 15)
	private String nextDayRtrvlExcpectAmt;

	@Column(name = "NEXT_DAY_BILLING_AMT", length = 15)
	private String nextDayBillingAmt;

	@Column(name = "TOTAL_STOCK_AMT", length = 15)
	private String totalStockAmt;

	@Column(name = "BEFORE_BDATE_STOCK_AMT", length = 15)
	private String beforeBdateStockAmt;

	@Column(name = "BEFORE_BDATE_RECV_AMT", length = 15)
	private String beforeBdateRecvAmt;

	@Column(name = "BEFORE_BDATE_CASH_SENDING_AMT", length = 15)
	private String beforeBdateCashSendingAmt;

	@Column(name = "BEFORE_BDATE_GIVE_AMT", length = 15)
	private String beforeBdateGiveAmt;

	@Column(name = "BEFORE_BDATE_DEPOSIT_AMT", length = 15)
	private String beforeBdateDepositAmt;

	@Column(name = "MNG_OFFICE", length = 20)
	private String mngOffice;

	@Column(name = "THIS_DAY_ADD_CASH_SENDING_AMT", length = 15)
	private String thisDayAddCashSendingAmt;

	@Column(name = "THIS_DAY_NONE_LOAD_AMT", length = 15)
	private String thisDayNoneLoadAmt;

	@Column(name = "PAYMENT_OVER_AMT", length = 15)
	private String paymentOverAmt;

	@Column(name = "STEXT_SEND_GUBUN", length = 1)
	private String stextSendGubun;

@Override
public Sh03001190Id getId() {
return Sh03001190Id.of(branchCode, reqDate);
}

@Embeddable
@Data
@NoArgsConstructor
@RequiredArgsConstructor(staticName = "of")
public static class Sh03001190Id implements Serializable {
		@NonNull
		private String branchCode;

		@NonNull
		private Timestamp reqDate;

}
}