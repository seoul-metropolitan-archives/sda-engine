package com.bgf.shbank.domain.mng.cash.sh03001220;

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
@Table(name = "ATMS_03001220")
@Comment(value = "")
@IdClass(Sh03001220.Sh03001220Id.class)
@Alias("sh03001220")
public class Sh03001220 extends SimpleJpaModel<Sh03001220.Sh03001220Id> {

	@Column(name = "TX_ID", nullable = false)
	private Timestamp txId;

	@Column(name = "JISA_CODE", length = 2, nullable = false)
	private String jisaCode;

	@Column(name = "MANLESS_BRANCH_CODE", length = 4, nullable = false)
	private String manlessBranchCode;

	@Id
	@Column(name = "BRANCH_CODE", length = 4, nullable = false)
	private String branchCode;

	@Column(name = "CORNER_CODE", length = 2, nullable = false)
	private String cornerCode;

	@Id
	@Column(name = "TERMINAL_NO", length = 4, nullable = false)
	private String terminalNo;

	@Id
	@Column(name = "REQ_DATE", nullable = false)
	private Timestamp reqDate;

	@Column(name = "REQ_GUBUN", length = 1)
	private String reqGubun;

	@Column(name = "CASH_TYPE_CODE1", length = 2)
	private String cashTypeCode1;

	@Column(name = "CASH_TYPE_CODE1_RECV_AMT", length = 15)
	private String cashTypeCode1RecvAmt;

	@Column(name = "CASH_TYPE_CODE2", length = 2)
	private String cashTypeCode2;

	@Column(name = "CASH_TYPE_CODE2_RECV_AMT", length = 15)
	private String cashTypeCode2RecvAmt;

	@Column(name = "CASH_TYPE_CODE3", length = 2)
	private String cashTypeCode3;

	@Column(name = "CASH_TYPE_CODE3_RECV_AMT", length = 15)
	private String cashTypeCode3RecvAmt;

	@Column(name = "CASH_TYPE_CODE4", length = 2)
	private String cashTypeCode4;

	@Column(name = "CASH_TYPE_CODE4_RECV_AMT", length = 15)
	private String cashTypeCode4RecvAmt;

	@Column(name = "CASH_TYPE_CODE5", length = 2)
	private String cashTypeCode5;

	@Column(name = "CASH_TYPE_CODE5_RECV_AMT", length = 15)
	private String cashTypeCode5RecvAmt;

	@Column(name = "CASH_SENDING_CAR_NO", length = 20)
	private String cashSendingCarNo;

	@Column(name = "CHARGE_EMP_NAME", length = 20)
	private String chargeEmpName;

	@Column(name = "CHARGE_EMP_REGNO", length = 13)
	private String chargeEmpRegno;

	@Column(name = "CHARGE_EMP_PHOTO_URL", length = 100)
	private String chargeEmpPhotoUrl;

	@Column(name = "DIGITAL_SIGN_URL", length = 100)
	private String digitalSignUrl;

	@Column(name = "S20_G_CARD_RECV_COUNT", length = 4)
	private String s20GCardRecvCount;

	@Column(name = "S20_T_CARD_RECV_COUNT", length = 4)
	private String s20TCardRecvCount;

	@Column(name = "S20PINK_G_CARD_RECV_COUNT", length = 4)
	private String s20pinkGCardRecvCount;

	@Column(name = "S20PINK_T_CARD_RECV_COUNT", length = 4)
	private String s20pinkTCardRecvCount;

	@Column(name = "LOVE_CARD_RECV_COUNT", length = 4)
	private String loveCardRecvCount;

	@Column(name = "HIPOINT_CARD_RECV_COUNT", length = 4)
	private String hipointCardRecvCount;

	@Column(name = "SCRTY_CARD_RECV_COUNT", length = 4)
	private String scrtyCardRecvCount;

	@Column(name = "STEXT_SEND_GUBUN", length = 1)
	private String stextSendGubun;

@Override
public Sh03001220Id getId() {
return Sh03001220Id.of(branchCode, terminalNo, reqDate);
}

@Embeddable
@Data
@NoArgsConstructor
@RequiredArgsConstructor(staticName = "of")
public static class Sh03001220Id implements Serializable {
		@NonNull
		private String branchCode;

		@NonNull
		private String terminalNo;

		@NonNull
		private Timestamp reqDate;

}
}