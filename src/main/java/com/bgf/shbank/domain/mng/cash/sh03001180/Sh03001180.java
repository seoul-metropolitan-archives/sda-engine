package com.bgf.shbank.domain.mng.cash.sh03001180;

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
@Table(name = "ATMS_03001180")
@Comment(value = "")
@IdClass(Sh03001180.Sh03001180Id.class)
@Alias("sh03001180")
public class Sh03001180 extends SimpleJpaModel<Sh03001180.Sh03001180Id> {

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
	@Column(name = "CASH_SENDING_DATE", nullable = false)
	private Timestamp cashSendingDate;

	@Column(name = "CASH_SENDING_AMT", length = 15)
	private String cashSendingAmt;

	@Column(name = "ACCEPT_ENABLE", length = 1)
	private String acceptEnable;

	@Column(name = "CASH_50K_SENDING_AMT", length = 15)
	private String cash50kSendingAmt;

	@Column(name = "STEXT_SEND_GUBUN", length = 1)
	private String stextSendGubun;

	@Transient
	private String stextSendGubun1;

	@Transient
	private String monCashSendingEnable;

	@Transient
	private String tueCashSendingEnable;

	@Transient
	private String wedCashSendingEnable;

	@Transient
	private String thuCashSendingEnable;

	@Transient
	private String friCashSendingEnable;

@Override
public Sh03001180Id getId() {
return Sh03001180Id.of(branchCode, terminalNo, cashSendingDate);
}

@Embeddable
@Data
@NoArgsConstructor
@RequiredArgsConstructor(staticName = "of")
public static class Sh03001180Id implements Serializable {

		@NonNull
		private String branchCode;

		@NonNull
		private String terminalNo;

		@NonNull
		private Timestamp cashSendingDate;

}
}