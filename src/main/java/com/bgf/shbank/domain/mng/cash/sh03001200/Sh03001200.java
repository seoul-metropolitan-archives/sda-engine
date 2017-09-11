package com.bgf.shbank.domain.mng.cash.sh03001200;

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
@Table(name = "ATMS_03001200")
@Comment(value = "")
@IdClass(Sh03001200.Sh03001200Id.class)
@Alias("sh03001200")
public class Sh03001200 extends SimpleJpaModel<Sh03001200.Sh03001200Id> {

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

	@Column(name = "MON_CASH_SENDING_ENABLE", length = 1)
	private String monCashSendingEnable;

	@Column(name = "TUE_CASH_SENDING_ENABLE", length = 1)
	private String tueCashSendingEnable;

	@Column(name = "WED_CASH_SENDING_ENABLE", length = 1)
	private String wedCashSendingEnable;

	@Column(name = "THU_CASH_SENDING_ENABLE", length = 1)
	private String thuCashSendingEnable;

	@Column(name = "FRI_CASH_SENDING_ENABLE", length = 1)
	private String friCashSendingEnable;


@Override
public Sh03001200Id getId() {
return Sh03001200Id.of(branchCode, terminalNo);
}

@Embeddable
@Data
@NoArgsConstructor
@RequiredArgsConstructor(staticName = "of")
public static class Sh03001200Id implements Serializable {

		@NonNull
		private String branchCode;

		@NonNull
		private String terminalNo;

}
}