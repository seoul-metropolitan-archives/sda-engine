package com.bgf.shbank.domain.mng.cash.sh03001140;

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
@Table(name = "ATMS_03001140")
@Comment(value = "")
@IdClass(Sh03001140.Sh03001140Id.class)
@Alias("sh03001140")
public class Sh03001140 extends SimpleJpaModel<Sh03001140.Sh03001140Id> {

	@Column(name = "JISA_CODE", length = 2, nullable = false)
	private String jisaCode;

	@Id
	@Column(name = "BRANCH_CODE", length = 4, nullable = false)
	private String branchCode;

	@Id
	@Column(name = "TERMINAL_NO", length = 4, nullable = false)
	private String terminalNo;

	@Id
	@Column(name = "CLOSE_DATE", nullable = false)
	private Timestamp closeDate;

	@Column(name = "PREV_DAY_CASH_SENDING_AMT", length = 15)
	private String prevDayCashSendingAmt;

	@Column(name = "DEPOSIT_AMT", length = 15)
	private String depositAmt;

	@Column(name = "GIVE_AMT", length = 15)
	private String giveAmt;

	@Column(name = "CLOSE_AMT", length = 15)
	private String closeAmt;

	@Column(name = "NONE_PROCESS_AMT", length = 15)
	private String noneProcessAmt;

	@Column(name = "RTRVL_FUND", length = 15)
	private String rtrvlFund;

	@Column(name = "NONE_PROCESS_AT", length = 1)
	private String noneProcessAt;

	@Column(name = "MNG_OFFICE", length = 20)
	private String mngOffice;

	@Column(name = "ADJUST_LACK_AMT_COUNT", length = 10)
	private String adjustLackAmtCount;

	@Column(name = "ADJUST_LACK_AMT", length = 15)
	private String adjustLackAmt;

	@Column(name = "STEXT_SEND_GUBUN", length = 1)
	private String stextSendGubun;

	@Column(name = "MEMO_CONTENT", length = 140)
	private String memoContent;


@Override
public Sh03001140Id getId() {
return Sh03001140Id.of(jisaCode, branchCode, terminalNo, closeDate);
}

@Embeddable
@Data
@NoArgsConstructor
@RequiredArgsConstructor(staticName = "of")
public static class Sh03001140Id implements Serializable {

		@NonNull
		private String jisaCode;

		@NonNull
		private String branchCode;

		@NonNull
		private String terminalNo;

		@NonNull
		private Timestamp closeDate;

}
}