package com.bgf.shbank.domain.mng.error.sh01001160;

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
@Table(name = "ATMS_01001160")
@Comment(value = "")
@IdClass(Sh01001160.Sh01001160Id.class)
@Alias("sh01001160")
public class Sh01001160 extends SimpleJpaModel<Sh01001160.Sh01001160Id> {

	@Column(name = "JISA_CODE", length = 2, nullable = false)
	private String jisaCode;

	@Id
	@Column(name = "BRANCH_CODE", length = 4, nullable = false)
	private String branchCode;

	@Id
	@Column(name = "CORNER_CODE", length = 2, nullable = false)
	private String cornerCode;

	@Id
	@Column(name = "TERMINAL_NO", length = 4, nullable = false)
	private String terminalNo;

	@Id
	@Column(name = "ERROR_DATETIME", nullable = false)
	private Timestamp errorDatetime;

	@Column(name = "CALLEE_REQ_SEQ_NO", length = 10)
	private String calleeReqSeqNo;

	@Column(name = "CALLEE_REQ_CHASU", length = 2)
	private String calleeReqChasu;

	@Column(name = "CALLEE_REQ_GUBUN_CODE", length = 1)
	private String calleeReqGubunCode;

	@Column(name = "CALLEE_CHASU_GUBUN", length = 1)
	private String calleeChasuGubun;

	@Column(name = "CANCLE_REQ_DATETIME", nullable = false)
	private Timestamp cancleReqDatetime;

	@Column(name = "CALLEE_CANCLE_REASON_CODE", length = 1)
	private String calleeCancleReasonCode;

	@Column(name = "UNUSL", length = 200)
	private String unusl;

	@Column(name = "CRT_NO", length = 15)
	private String crtNo;

	@Column(name = "CALLEE_REQ_REASON_CODE", length = 1)
	private String calleeReqReasonCode;

	@Column(name = "TERMINAL_ERROR_CODE_1", length = 5)
	private String terminalErrorCode1;

	@Column(name = "TERMINAL_ERROR_CODE_2", length = 10)
	private String terminalErrorCode2;

	@Column(name = "TOTAL_CLASSIFY_CODE", length = 2)
	private String totalClassifyCode;

	@Transient
	private String selfCallGubun;


@Override
public Sh01001160Id getId() {
return Sh01001160Id.of(branchCode, cornerCode, terminalNo, errorDatetime);
}

@Embeddable
@Data
@NoArgsConstructor
@RequiredArgsConstructor(staticName = "of")
public static class Sh01001160Id implements Serializable {

		@NonNull
		private String branchCode;

		@NonNull
		private String cornerCode;

		@NonNull
		private String terminalNo;

		@NonNull
		private Timestamp errorDatetime;

}
}