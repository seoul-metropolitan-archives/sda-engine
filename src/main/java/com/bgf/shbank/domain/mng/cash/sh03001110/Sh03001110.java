package com.bgf.shbank.domain.mng.cash.sh03001110;

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
@Table(name = "ATMS_03001110")
@Comment(value = "")
@IdClass(Sh03001110.Sh03001110Id.class)
@Alias("sh03001110")
public class Sh03001110 extends SimpleJpaModel<Sh03001110.Sh03001110Id> {

	@Transient
	private String sijeConfirm;

	@Column(name = "TX_ID", nullable = false)
	private Timestamp txId;

	@Id
	@Column(name = "REFER_DATE", nullable = false)
	private Timestamp referDate;

	@Column(name = "JISA_CODE", length = 2, nullable = false)
	private String jisaCode;

	@Id
	@Column(name = "BRANCH_CODE", length = 4, nullable = false)
	private String branchCode;

	@Id
	@Column(name = "TERMINAL_NO", length = 4, nullable = false)
	private String terminalNo;

	@Column(name = "CASH_WITHDRAW_AMT", length = 15)
	private String cashWithdrawAmt;

	@Column(name = "CASH_DEPOSIT_AMT", length = 15)
	private String cashDepositAmt;

	@Column(name = "CASH_AMT", length = 15)
	private String cashAmt;

	@Column(name = "CHECK_WITHDRAW_AMT", length = 15)
	private String checkWithdrawAmt;

	@Column(name = "CHECK_DEPOSIT_AMT", length = 15)
	private String checkDepositAmt;

	@Column(name = "CHECK_GIVE_ENABLE_COUNT", length = 4)
	private String checkGiveEnableCount;

	@Column(name = "CASH_50K_GIVE_ENABLE_COUNT", length = 5)
	private String cash50kGiveEnableCount;

	@Column(name = "STEXT_SEND_GUBUN", length = 1)
	private String stextSendGubun;

	@Transient
	private Timestamp startDate;

	@Transient
	private Timestamp endDate;

	@Transient
	private String stextGubun;
	@Transient
	private String totalClassifyCode;
	@Transient
	private String errorType;
	@Transient
	private Timestamp openDatetime;


@Override
public Sh03001110Id getId() {
return Sh03001110Id.of(referDate, branchCode, terminalNo);
}

@Embeddable
@Data
@NoArgsConstructor
@RequiredArgsConstructor(staticName = "of")
public static class Sh03001110Id implements Serializable {

		@NonNull
		private Timestamp referDate;

		@NonNull
		private String branchCode;

		@NonNull
		private String terminalNo;

}
}