package com.bgf.shbank.domain.mng.etc.sh04001140;

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
@Table(name = "ATMS_04001140")
@Comment(value = "")
@IdClass(Sh04001140.Sh04001140Id.class)
@Alias("sh04001140")
public class Sh04001140 extends SimpleJpaModel<Sh04001140.Sh04001140Id> {

	@Id
	@Column(name = "SEQ_NO", length = 5, nullable = false)
	private String seqNo;

	@Column(name = "JISA_CODE", length = 2, nullable = false)
	private String jisaCode;

	@Id
	@Column(name = "BRANCH_CODE", length = 4, nullable = false)
	private String branchCode;

	@Id
	@Column(name = "MNG_BRANCH_CODE", length = 4)
	private String mngBranchCode;

	@Id
	@Column(name = "CORNER_CODE", length = 2, nullable = false)
	private String cornerCode;

	@Id
	@Column(name = "TERMINAL_NO", length = 4, nullable = false)
	private String terminalNo;

	@Column(name = "MODEL_CODE", length = 3)
	private String modelCode;

	@Column(name = "OVERHAUL_CORP", length = 1)
	private String overhaulCorp;

	@Column(name = "OVERHAUL_EMP_NAME", length = 10)
	private String overhaulEmpName;

	@Column(name = "INSPECTION_EMP_NAME", length = 10)
	private String inspectionEmpName;

	@Column(name = "INSPECTION_CORP", length = 1)
	private String inspectionCorp;

	@Column(name = "OVERHAUL_GUBUN", length = 1)
	private String overhaulGubun;

	@Column(name = "OVERHAUL_DATE")
	private Timestamp overhaulDate;

	@Column(name = "OVERHAUL_START_TIME")
	private Timestamp overhaulStartTime;

	@Column(name = "OVERHAUL_END_TIME")
	private Timestamp overhaulEndTime;

	@Column(name = "OVERHAUL_HANDLE_CONTENT", length = 100)
	private String overhaulHandleContent;

	@Column(name = "INSPECTION_OPINION", length = 100)
	private String inspectionOpinion;

	@Column(name = "STEXT_SEND_GUBUN", length = 1)
	private String stextSendGubun;

	@Override
	public Sh04001140Id getId() { return Sh04001140Id.of(seqNo, branchCode, mngBranchCode, cornerCode, terminalNo); }

	@Embeddable
	@Data
	@NoArgsConstructor
	@RequiredArgsConstructor(staticName = "of")
	public static class Sh04001140Id implements Serializable {

		@NonNull
		private String seqNo;

		@NonNull
		private String branchCode;

		@NonNull
		private String mngBranchCode;

		@NonNull
		private String cornerCode;

		@NonNull
		private String terminalNo;
	}

}