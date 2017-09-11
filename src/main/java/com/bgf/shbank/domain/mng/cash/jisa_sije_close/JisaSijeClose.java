package com.bgf.shbank.domain.mng.cash.jisa_sije_close;

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
@Table(name = "ATMS_JISA_SIJE_CLOSE")
@Comment(value = "")
@IdClass(JisaSijeClose.JisaSijeCloseId.class)
@Alias("jisaSijeClose")
public class JisaSijeClose extends SimpleJpaModel<JisaSijeClose.JisaSijeCloseId> {

	@Column(name = "TX_ID", nullable = false)
	private Timestamp txId;

	@Id
	@Column(name = "CLOSE_DATE", nullable = false)
	private Timestamp closeDate;

	@Id
	@Column(name = "JISA_CODE", length = 2, nullable = false)
	private String jisaCode;

	@Column(name = "PREV_DAY_RESERVE_SIJE", length = 15)
	private String prevDayReserveSije;

	@Column(name = "THIS_DAY_CASH_DEPOSIT_AMT", length = 15)
	private String thisDayCashDepositAmt;

	@Column(name = "JISA_TO_SHINHAN_SEND_AMT", length = 15)
	private String jisaToShinhanSendAmt;

	@Column(name = "CLOSE_AMT", length = 15)
	private String closeAmt;

	@Column(name = "UN_CHECK_AMT", length = 15)
	private String unCheckAmt;

	@Column(name = "SIJE_MISTAKE_AMT", length = 15)
	private String sijeMistakeAmt;

	@Column(name = "CASH_SENDING_AMT", length = 15)
	private String cashSendingAmt;

	@Column(name = "ADD_CASH_SENDING_AMT", length = 15)
	private String addCashSendingAmt;

	@Column(name = "JISA_SAFE_AMT", length = 15)
	private String jisaSafeAmt;

	@Column(name = "MEMO_CONTENT", length = 140)
	private String memoContent;

	@Column(name = "USER_NM", length = 30)
	private String userNm;

	@Transient
	private Timestamp prevCloseDate;


@Override
public JisaSijeCloseId getId() {
return JisaSijeCloseId.of(jisaCode, closeDate);
}

@Embeddable
@Data
@NoArgsConstructor
@RequiredArgsConstructor(staticName = "of")
public static class JisaSijeCloseId implements Serializable {

		@NonNull
		private String jisaCode;

		@NonNull
		private Timestamp closeDate;

}
}