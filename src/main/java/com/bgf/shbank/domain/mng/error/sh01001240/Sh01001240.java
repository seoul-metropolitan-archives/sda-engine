package com.bgf.shbank.domain.mng.error.sh01001240;

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
@Table(name = "ATMS_01001240")
@Comment(value = "")
@IdClass(Sh01001240.Sh01001240Id.class)
@Alias("sh01001240")
public class Sh01001240 extends SimpleJpaModel<Sh01001240.Sh01001240Id> {

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
	@Column(name = "HANDLE_SEQ_NO", length = 6, nullable = false)
	private String handleSeqNo;

	@Id
	@Column(name = "HANDLE_DATETIME", nullable = false)
	private Timestamp handleDatetime;

	@Column(name = "HANDLE_EMP_NAME", length = 20)
	private String handleEmpName;

	@Column(name = "HANDLE_EMP_TELNO", length = 20)
	private String handleEmpTelno;

	@Column(name = "HANDLE_DESC", length = 200)
	private String handleDesc;

	@Column(name = "HANDLE_STATUS", length = 1)
	private String handleStatus;


@Override
public Sh01001240Id getId() {
return Sh01001240Id.of(branchCode, cornerCode, terminalNo, handleSeqNo, handleDatetime);
}

@Embeddable
@Data
@NoArgsConstructor
@RequiredArgsConstructor(staticName = "of")
public static class Sh01001240Id implements Serializable {

		@NonNull
		private String branchCode;

		@NonNull
		private String cornerCode;

		@NonNull
		private String terminalNo;

		@NonNull
		private String handleSeqNo;

		@NonNull
		private Timestamp handleDatetime;

}
}