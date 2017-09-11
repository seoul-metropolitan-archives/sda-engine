package com.bgf.shbank.domain.mng.error.sh01001140;

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
@Table(name = "ATMS_01001140")
@Comment(value = "")
@IdClass(Sh01001140.Sh01001140Id.class)
@Alias("sh01001140")
public class Sh01001140 extends SimpleJpaModel<Sh01001140.Sh01001140Id> {

	@Column(name = "JISA_CODE", length = 2, nullable = false)
	@Comment(value = "")
	private String jisaCode;

	@Id
	@Column(name = "BRANCH_CODE", length = 4, nullable = false)
	@Comment(value = "")
	private String branchCode;

	@Id
	@Column(name = "CORNER_CODE", length = 2, nullable = false)
	@Comment(value = "")
	private String cornerCode;

	@Id
	@Column(name = "TERMINAL_NO", length = 4, nullable = false)
	@Comment(value = "")
	private String terminalNo;

	@Id
	@Column(name = "ERROR_DATETIME", nullable = false)
	@Comment(value = "")
	private Timestamp errorDatetime;

	@Column(name = "CALLEE_REQ_SEQ_NO", length = 10)
	@Comment(value = "")
	private String calleeReqSeqNo;

	@Column(name = "CALLEE_REQ_CHASU", length = 2)
	@Comment(value = "")
	private String calleeReqChasu;

	@Column(name = "CALLEE_REQ_GUBUN", length = 1)
	@Comment(value = "")
	private String calleeReqGubun;

	@Column(name = "CALLEE_CHASU_GUBUN", length = 1)
	@Comment(value = "")
	private String calleeChasuGubun;

	@Column(name = "CORNER_ARRIVAL_DATETIME", nullable = false)
	@Comment(value = "")
	private Timestamp cornerArrivalDatetime;

	@Column(name = "ARRIVAL_CORP_CODE", length = 1)
	@Comment(value = "")
	private String arrivalCorpCode;

	@Column(name = "UNUSL", length = 100)
	@Comment(value = "")
	private String unusl;

	@Column(name = "CRT_NO", length = 15)
	@Comment(value = "")
	private String crtNo;

	@Override
	public Sh01001140Id getId() {
	return Sh01001140Id.of(branchCode, cornerCode, terminalNo, errorDatetime);
	}

	@Embeddable
	@Data
	@NoArgsConstructor
	@RequiredArgsConstructor(staticName = "of")
	public static class Sh01001140Id implements Serializable {

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