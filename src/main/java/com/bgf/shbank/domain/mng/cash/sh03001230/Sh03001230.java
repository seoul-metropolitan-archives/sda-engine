package com.bgf.shbank.domain.mng.cash.sh03001230;

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
@Table(name = "ATMS_03001230")
@Comment(value = "")
@IdClass(Sh03001230.Sh03001230Id.class)
@Alias("sh03001230")
public class Sh03001230 extends SimpleJpaModel<Sh03001230.Sh03001230Id> {

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
	@Column(name = "RTN_DATE", nullable = false)
	private Timestamp rtnDate;

	@Column(name = "CONFIRM_DATE")
	private Timestamp confirmDate;

	@Column(name = "DEAL_DATE")
	private Timestamp dealDate;

	@Column(name = "RTN_AMT", length = 15)
	private String rtnAmt;

	@Column(name = "CONTENT", length = 50)
	private String content;

	@Column(name = "CHARGE_CENTER", length = 50)
	private String chargeCenter;

	@Column(name = "NOTE", length = 50)
	private String note;

	@Column(name = "STEXT_SEND_GUBUN", length = 1)
	private String stextSendGubun;

@Override
public Sh03001230Id getId() {
return Sh03001230Id.of(branchCode, terminalNo, rtnDate);
}

@Embeddable
@Data
@NoArgsConstructor
@RequiredArgsConstructor(staticName = "of")
public static class Sh03001230Id implements Serializable {

		@NonNull
		private String branchCode;

		@NonNull
		private String terminalNo;

		@NonNull
		private Timestamp rtnDate;

}
}