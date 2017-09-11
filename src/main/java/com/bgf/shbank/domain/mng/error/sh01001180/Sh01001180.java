package com.bgf.shbank.domain.mng.error.sh01001180;

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
@Table(name = "ATMS_01001180")
@Comment(value = "")
@IdClass(Sh01001180.Sh01001180Id.class)
@Alias("sh01001180")
public class Sh01001180 extends SimpleJpaModel<Sh01001180.Sh01001180Id> {

	@Id
	@Column(name = "ERROR_DATETIME", nullable = false)
	private Timestamp errorDatetime;

	@Column(name = "MNG_STND_DATE")
	private String mngStndDate;

	@Column(name = "MNG_STND_CRITIC", length = 3)
	private String mngStndCritic;

	@Column(name = "ERROR_COUNT", length = 3)
	private String errorCount;

	@Column(name = "JISA_CODE", length = 2, nullable = false)
	private String jisaCode;

	@Id
	@Column(name = "BRANCH_CODE", length = 4, nullable = false)
	private String branchCode;

	@Id
	@Column(name = "TERMINAL_NO", length = 4, nullable = false)
	private String terminalNo;

	@Id
	@Column(name = "CORNER_CODE", length = 2, nullable = false)
	private String cornerCode;

	@Column(name = "MODEL_CODE", length = 3)
	private String modelCode;

	@Column(name = "CORNER_NAME", length = 40)
	private String cornerName;

	@Column(name = "UNUSL", length = 100)
	private String unusl;

	@Column(name = "CALLEE_SEQ_NO", length = 4)
	private String calleeSeqNo;

	@Column(name = "ERROR_METHOD", length = 1)
	private String errorMethod;

	@Column(name = "ERROR_TYPE", length = 1)
	private String errorType;

	@Column(name = "TOTAL_CLASSIFY_CODE", length = 2)
	private String totalClassifyCode;

	@Column(name = "TERMINAL_ERROR1", length = 5)
	private String terminalError1;

	@Column(name = "TERMINAL_ERROR2", length = 10)
	private String terminalError2;

	@Column(name = "HANDLE_DATETIME")
	private Timestamp handleDatetime;

	@Column(name = "HANDLE_EMP_NAME", length = 20)
	private String handleEmpName;

	@Column(name = "MEMO_CONTENT", length = 140)
	private String memoContent;

	@Column(name = "CALLEE_REQ_DATE")
	private Timestamp calleeReqDate;

	@Column(name = "STEXT_SEND_GUBUN", length = 1)
	private String stextSendGubun;


@Override
public Sh01001180Id getId() {
return Sh01001180Id.of(errorDatetime, branchCode, terminalNo, cornerCode);
}

@Embeddable
@Data
@NoArgsConstructor
@RequiredArgsConstructor(staticName = "of")
public static class Sh01001180Id implements Serializable {

		@NonNull
		private Timestamp errorDatetime;

		@NonNull
		private String branchCode;

		@NonNull
		private String terminalNo;

		@NonNull
		private String cornerCode;

}
}