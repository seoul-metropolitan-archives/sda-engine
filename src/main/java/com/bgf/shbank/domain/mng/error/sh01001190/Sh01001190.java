package com.bgf.shbank.domain.mng.error.sh01001190;

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
@Table(name = "ATMS_01001190")
@Comment(value = "")
@IdClass(Sh01001190.Sh01001190Id.class)
@Alias("sh01001190")
public class Sh01001190 extends SimpleJpaModel<Sh01001190.Sh01001190Id> {

	@Id
	@Column(name = "HANDLE_DATETIME", nullable = false)
	private Timestamp handleDatetime;

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

	@Column(name = "HANDLE_EMP_NAME", length = 10)
	private String handleEmpName;

	@Column(name = "MEMO_CONTENT", length = 140)
	private String memoContent;

	@Column(name = "CALLEE_REQ_DATE")
	private Timestamp calleeReqDate;

	@Column(name = "CALLEE_SEQ_NO", length = 4)
	private String calleeSeqNo;

	@Column(name = "ERROR_METHOD", length = 1)
	private String errorMethod;


@Override
public Sh01001190Id getId() {
return Sh01001190Id.of(handleDatetime, branchCode, terminalNo, cornerCode);
}

@Embeddable
@Data
@NoArgsConstructor
@RequiredArgsConstructor(staticName = "of")
public static class Sh01001190Id implements Serializable {

		@NonNull
		private Timestamp handleDatetime;

		@NonNull
		private String branchCode;

		@NonNull
		private String terminalNo;

		@NonNull
		private String cornerCode;

}
}