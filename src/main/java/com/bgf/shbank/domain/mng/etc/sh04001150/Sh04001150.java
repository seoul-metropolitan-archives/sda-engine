package com.bgf.shbank.domain.mng.etc.sh04001150;

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
@Table(name = "ATMS_04001150")
@Comment(value = "")
@IdClass(Sh04001150.Sh04001150Id.class)
@Alias("sh04001150")
public class Sh04001150 extends SimpleJpaModel<Sh04001150.Sh04001150Id> {

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

	@Column(name = "CORNER_NAME", length = 40)
	private String cornerName;

	@Id
	@Column(name = "ERROR_DATETIME", nullable = false)
	private Timestamp errorDatetime;

	@Column(name = "CALLEE_REQ_DATETIME")
	private Timestamp calleeReqDatetime;

	@Column(name = "CALLEE_REQ_SEQ_NO", length = 10)
	private String calleeReqSeqNo;

	@Column(name = "CALLEE_REQ_CHASU", length = 1)
	private String calleeReqChasu;

	@Column(name = "CALLEE_CHASU_GUBUN", length = 1)
	private String calleeChasuGubun;

	@Column(name = "CORNER_ARRIVAL_DATETIME")
	private Timestamp cornerArrivalDatetime;

	@Column(name = "REPAIR_DATETIME")
	private Timestamp repairDatetime;

	@Column(name = "TERMINAL_TYPE", length = 3)
	private String terminalType;

	@Column(name = "ERROR_CONTENT", length = 60)
	private String errorContent;

	@Column(name = "PENALTY_AMT", length = 5)
	private String penaltyAmt;

	@Column(name = "NONE_PENALTY_ENABLE", length = 1)
	private String nonePenaltyEnable;

	@Column(name = "NONE_PENALTY_REASON", length = 219)
	private String nonePenaltyReason;

	@Override
	public Sh04001150Id getId() {
	return Sh04001150Id.of(branchCode, cornerCode, terminalNo, errorDatetime);
	}

	@Embeddable
	@Data
	@NoArgsConstructor
	@RequiredArgsConstructor(staticName = "of")
	public static class Sh04001150Id implements Serializable {

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