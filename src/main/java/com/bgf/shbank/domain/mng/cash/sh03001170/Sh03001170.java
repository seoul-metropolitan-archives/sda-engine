package com.bgf.shbank.domain.mng.cash.sh03001170;

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
@Table(name = "ATMS_03001170")
@Comment(value = "")
@IdClass(Sh03001170.Sh03001170Id.class)
@Alias("sh03001170")
public class Sh03001170 extends SimpleJpaModel<Sh03001170.Sh03001170Id> {

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

	@Column(name = "CASH_50K_SENDING_AMT", length = 15)
	private String cash50kSendingAmt;

	@Column(name = "CASH_SENDING_AMT", length = 15)
	private String cashSendingAmt;

	@Column(name = "MNG_OFFICE", length = 20)
	private String mngOffice;

	@Transient
	private Timestamp startDate;

	@Transient
	private Timestamp endDate;

	@Transient
	private String jisaName;

	@Transient
	private String branchName;

	@Transient
	private String cornerName;


@Override
public Sh03001170Id getId() {
return Sh03001170Id.of(txId,  jisaCode, branchCode, terminalNo, cashSendingDate);
}

@Embeddable
@Data
@NoArgsConstructor
@RequiredArgsConstructor(staticName = "of")
public static class Sh03001170Id implements Serializable {

		@NonNull
		private Timestamp txId;

		@NonNull
		private String jisaCode;

		@NonNull
		private String branchCode;

		@NonNull
		private String terminalNo;

		@NonNull
		private Timestamp cashSendingDate;

}
}